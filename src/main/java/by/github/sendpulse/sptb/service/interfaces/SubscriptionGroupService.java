package by.github.sendpulse.sptb.service.interfaces;

import by.github.sendpulse.sptb.entity.SubscriptionGroup;

import java.util.List;
import java.util.Optional;

public interface SubscriptionGroupService {

    void save(SubscriptionGroup subscriptionGroup);
    Optional<SubscriptionGroup> findById(SubscriptionGroup subscriptionGroup);
    List<SubscriptionGroup> findAll();
    void update(SubscriptionGroup subscriptionGroup);
    void deleteById(SubscriptionGroup subscriptionGroup);

}
