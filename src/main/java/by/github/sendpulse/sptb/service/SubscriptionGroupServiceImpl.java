package by.github.sendpulse.sptb.service;

import by.github.sendpulse.sptb.entity.SubscriptionGroup;
import by.github.sendpulse.sptb.repository.SubscriptionGroupRepository;
import by.github.sendpulse.sptb.service.interfaces.SubscriptionGroupService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionGroupServiceImpl implements SubscriptionGroupService {

    private static final Logger logger = Logger.getLogger(SubscriptionGroupServiceImpl.class);
    private final SubscriptionGroupRepository subscriptionGroupRepository;

    @Autowired
    public SubscriptionGroupServiceImpl(SubscriptionGroupRepository subscriptionGroupRepository) {
        this.subscriptionGroupRepository = subscriptionGroupRepository;
    }

    @Override
    public void save(SubscriptionGroup subscriptionGroup) {
        subscriptionGroupRepository.save(subscriptionGroup);
        logger.log(Level.INFO, "New subscriptionGroup was added");
    }

    @Override
    public Optional<SubscriptionGroup> findById(SubscriptionGroup subscriptionGroup) {
        return subscriptionGroupRepository.findById(subscriptionGroup.getId());
    }

    @Override
    public List<SubscriptionGroup> findAll() {
        return subscriptionGroupRepository.findAll();
    }

    @Override
    public void update(SubscriptionGroup subscriptionGroup) {
        if (subscriptionGroupRepository.findById(subscriptionGroup.getId()).isPresent()) {
            subscriptionGroupRepository.save(subscriptionGroup);
            logger.log(Level.INFO, "SubscriptionGroup with id = " + subscriptionGroup.getId() + " was updated");
        }
    }

    @Override
    public void deleteById(SubscriptionGroup subscriptionGroup) {
        subscriptionGroupRepository.delete(subscriptionGroup); 
        logger.log(Level.INFO, "SubscriptionGroup with id = " + subscriptionGroup.getId() + " was deleted");
    }
}
