package com.sic.parvis.magna.second_education.repo;

import com.sic.parvis.magna.second_education.model.User;
import com.sic.parvis.magna.second_education.model.UserSubscription;
import com.sic.parvis.magna.second_education.model.UserSubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSubscriptionRepo extends JpaRepository<UserSubscription, UserSubscriptionId> {
    List<UserSubscription> findBySubscriber(User user);

    List<UserSubscription> findByChannel(User channel);

    UserSubscription findByChannelAndSubscriber(User channel, User subscriber);
}
