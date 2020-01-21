package com.sic.parvis.magna.second_education.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.sic.parvis.magna.second_education.dto.EventType;
import com.sic.parvis.magna.second_education.dto.MetaDto;
import com.sic.parvis.magna.second_education.dto.ObjectType;
import com.sic.parvis.magna.second_education.model.University;
import com.sic.parvis.magna.second_education.model.Views;
import com.sic.parvis.magna.second_education.repo.UniversityRepo;
import com.sic.parvis.magna.second_education.util.WsSender;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/university")
public class UniversityController {

    private static final String URL_PATTERN = "https?:\\/\\/?[\\w\\d\\._\\-%\\/\\?=&#]+";
    private static final String IMAGE_PATTERN = "\\.(jpeg|jpg|gif|png)$";
    private static Pattern URL_REGEX = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
    private static Pattern IMAGE_REGEX = Pattern.compile(IMAGE_PATTERN, Pattern.CASE_INSENSITIVE);

    private final UniversityRepo universityRepo;
    private final BiConsumer<EventType, University> wsSender;

    @Autowired
    public UniversityController(UniversityRepo universityRepo, WsSender wsSender) {
        this.universityRepo = universityRepo;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, Views.IdName.class);
    }


    @GetMapping
    @JsonView(Views.IdName.class)
    public List<University> listUniversities() {
        return universityRepo.findAll();
    }

    @GetMapping("/{id}")
    public University getUniversity(@PathVariable("id") University university) {
        return university;
    }

    @PostMapping
    public University createUniversity(@RequestBody University university) {
        university.setAdded(LocalDateTime.now());
        fillMeta(university);
        University createdUniversity = universityRepo.save(university);
        wsSender.accept(EventType.CREATE, createdUniversity);

        return createdUniversity;
    }

    @PutMapping
    public University updateUniversity(@RequestParam("id") University entityFromDb, @RequestBody University updatedEntity) {
        BeanUtils.copyProperties(updatedEntity, entityFromDb, "id");
        fillMeta(entityFromDb);
        University updatedUniversity = universityRepo.save(entityFromDb);
        wsSender.accept(EventType.UPDATE, updatedEntity);

        return updatedUniversity;
    }

    @DeleteMapping
    public void deleteUniversity(@RequestParam("id") University university) {
        universityRepo.delete(university);
        wsSender.accept(EventType.REMOVE, university);
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
}
