package com.sic.parvis.magna.second_education.service;

import com.sic.parvis.magna.second_education.model.User;
import com.sic.parvis.magna.second_education.model.UserSubscription;
import com.sic.parvis.magna.second_education.repo.UserDetailsRepo;
import com.sic.parvis.magna.second_education.repo.UserSubscriptionRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProfileService {
    private final UserDetailsRepo userDetailsRepo;
    private final UserSubscriptionRepo userSubscriptionRepo;

    public User changeSubscription(User channel, User subscriber) {
        List<UserSubscription> subscriptions = channel.getSubscribers().stream()
                .filter(subscription -> subscription.getSubscriber().equals(subscriber)).collect(Collectors.toList());

        if (subscriptions.isEmpty()) {
            channel.getSubscribers().add(new UserSubscription(channel, subscriber));
        } else {
            channel.getSubscribers().removeAll(subscriptions);
        }

        return userDetailsRepo.save(channel);
    }

    public List<UserSubscription> getSubscribers(User channel) {
        return userSubscriptionRepo.findByChannel(channel);
    }

    public UserSubscription changeSubscriptionStatus(User channel, User subscriber) {
        var subscription = userSubscriptionRepo.findByChannelAndSubscriber(channel, subscriber);
        subscription.setActive(!subscription.isActive());

        return userSubscriptionRepo.save(subscription);
    }
}
