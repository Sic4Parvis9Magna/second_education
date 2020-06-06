package com.sic.parvis.magna.second_education.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.sic.parvis.magna.second_education.model.User;
import com.sic.parvis.magna.second_education.model.UserSubscription;
import com.sic.parvis.magna.second_education.model.Views;
import com.sic.parvis.magna.second_education.service.ProfileService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("profile")
@AllArgsConstructor
@Log4j2
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping("{id}")
    @JsonView(Views.FullProfile.class)
    public User getUser(@PathVariable("id") User user) {
        log.warn("Requested user found!");
        return user;
    }

    @PostMapping("change-subscription/{channelId}")
    @JsonView(Views.FullProfile.class)
    public User changeSubscription(
            @AuthenticationPrincipal User subscriber,
            @PathVariable("channelId") User channel
    ) {
        if (subscriber.equals(channel)) {
            return channel;
        } else {
            return profileService.changeSubscription(channel, subscriber);
        }
    }

    @GetMapping("get-subscribers/{channelId}")
    @JsonView(Views.IdName.class)
    public List<UserSubscription> subscribers(@PathVariable("channelId") User channel) {
        return profileService.getSubscribers(channel);
    }

    @PostMapping("change-status/{subscriberId}")
    @JsonView(Views.IdName.class)
    public UserSubscription changeSubscriptionStatus(@AuthenticationPrincipal User channel,
                                                     @PathVariable("subscriberId") User subscriber) {
        return profileService.changeSubscriptionStatus(channel, subscriber);
    }
}
