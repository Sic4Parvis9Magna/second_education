package com.sic.parvis.magna.second_education.service;

import com.sic.parvis.magna.second_education.dto.EventType;
import com.sic.parvis.magna.second_education.dto.MetaDto;
import com.sic.parvis.magna.second_education.dto.ObjectType;
import com.sic.parvis.magna.second_education.dto.UniversityPageDto;
import com.sic.parvis.magna.second_education.model.University;
import com.sic.parvis.magna.second_education.model.User;
import com.sic.parvis.magna.second_education.model.UserSubscription;
import com.sic.parvis.magna.second_education.model.Views;
import com.sic.parvis.magna.second_education.repo.UniversityRepo;
import com.sic.parvis.magna.second_education.repo.UserSubscriptionRepo;
import com.sic.parvis.magna.second_education.util.WsSender;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UniversityService {
    private static final String URL_PATTERN = "https?:\\/\\/?[\\w\\d\\._\\-%\\/\\?=&#]+";
    private static final String IMAGE_PATTERN = "\\.(jpeg|jpg|gif|png)$";
    private static final Pattern URL_REGEX = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
    private static final Pattern IMAGE_REGEX = Pattern.compile(IMAGE_PATTERN, Pattern.CASE_INSENSITIVE);


    private final UniversityRepo universityRepo;
    private final UserSubscriptionRepo userSubscriptionRepo;
    private final BiConsumer<EventType, University> wsSenderIdName;
    private final BiConsumer<EventType, University> wsSenderWithData;


    @Autowired
    public UniversityService(UniversityRepo universityRepo,
                             UserSubscriptionRepo userSubscriptionRepo,
                             WsSender wsSender) {
        this.universityRepo = universityRepo;
        this.userSubscriptionRepo = userSubscriptionRepo;
        this.wsSenderIdName = wsSender.getSender(ObjectType.MESSAGE, Views.IdName.class);
        this.wsSenderWithData = wsSender.getSender(ObjectType.MESSAGE, Views.WithData.class);
    }

    private void fillMeta(University university) {
        var name = university.getName();
        var matcherUrl = URL_REGEX.matcher(name);

        if (matcherUrl.find()) {
            var url = name.substring(matcherUrl.start(),matcherUrl.end());

            university.setLink(url);

            var imageMatcher = IMAGE_REGEX.matcher(url);

            if (imageMatcher.find()) {
                university.setLinkCover(url);
            } else  if (!url.contains("youtu")) {

                MetaDto metaDto = getMeta(url);

                university.setLinkCover(metaDto.getCover());
                university.setLinkTitle(metaDto.getTitle());
                university.setLinkDescription(metaDto.getDescription());
            }
        }
    }

    @SneakyThrows
    private MetaDto getMeta(String url) {
        Document doc = Jsoup.connect(url).get();
        Elements title = doc.select("meta[name$=titile], meta[property$=titile]");
        Elements description = doc.select("meta[name$=description], meta[property$=description]");
        Elements cover = doc.select("meta[name$=image], meta[property$=image]");

        return MetaDto.from(
                getContent(title.first()),
                getContent(description.first()),
                getContent(cover.first())
        );
    }

    private String getContent(Element element) {
        return Objects.isNull(element)? "" : element.attr("content");
    }

    public void delete(University university) {
        universityRepo.delete(university);
        //TODO check if we need it
        wsSenderIdName.accept(EventType.REMOVE, new University(university.getId(), university.getName()));
    }

    public University updateName(University entityFromDb, University updatedEntity) {
        fillMeta(entityFromDb);
        entityFromDb.setName(updatedEntity.getName());
        University updatedUniversity = universityRepo.save(entityFromDb);
        wsSenderWithData.accept(EventType.UPDATE, updatedUniversity);

        return updatedUniversity;
    }

    public University create(University university, User user) {
        university.setAdded(LocalDateTime.now());
        fillMeta(university);
        university.setAuthor(user);
        University createdUniversity = universityRepo.save(university);
        wsSenderIdName.accept(EventType.CREATE, createdUniversity);

        return createdUniversity;
    }

    public UniversityPageDto findForUser(Pageable pageable, User user) {
        List<User> channels = userSubscriptionRepo.findBySubscriber(user)
                .stream()
                .filter(UserSubscription::isActive)
                .map(UserSubscription::getChannel)
                .collect(Collectors.toList());

        channels.add(user);

        Page<University> page = universityRepo.findByAuthorIn(channels, pageable);

        return new UniversityPageDto(page.getContent(),
                pageable.getPageNumber(),
                page.getTotalPages()
        );
    }
}
