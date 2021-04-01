package by.github.sendpulse.sptb.repository;

import by.github.sendpulse.sptb.entity.SubscriptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionGroupRepository extends JpaRepository<SubscriptionGroup, Long> {
}
