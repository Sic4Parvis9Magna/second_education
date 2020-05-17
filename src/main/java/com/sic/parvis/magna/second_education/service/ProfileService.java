package com.sic.parvis.magna.second_education.service;

import com.sic.parvis.magna.second_education.model.User;
import com.sic.parvis.magna.second_education.repo.UserDetailsRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class ProfileService {
    private final UserDetailsRepo userDetailsRepo;

    public User changeSubscription(User channel, User subscriber) {
        Set<User> subscribers = channel.getSubscribers();

        if (subscribers.contains(subscriber)) {
            subscribers.remove(subscriber);
        } else {
            subscribers.add(subscriber);
        }

        return userDetailsRepo.save(channel);
    }
}
