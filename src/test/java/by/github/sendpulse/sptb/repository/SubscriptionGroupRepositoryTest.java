package by.github.sendpulse.sptb.repository;

import by.github.sendpulse.sptb.entity.Quiz;
import by.github.sendpulse.sptb.entity.SubscriptionGroup;
import by.github.sendpulse.sptb.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class SubscriptionGroupRepositoryTest {


    @Autowired
    private SubscriptionGroupRepository subscriptionGroupRepository;


    @Sql(scripts = {"/sql/clearDb.sql"})
    @Test
    public void shouldSaveSubGroup() {
        //given
        SubscriptionGroup subscriptionGroup = new SubscriptionGroup();
        subscriptionGroup.setName("Some group");
        subscriptionGroup.setLastArticleId(1);

        //when
        subscriptionGroupRepository.save(subscriptionGroup);

        //then
        Optional<SubscriptionGroup> saved = subscriptionGroupRepository.findById(subscriptionGroup.getId());
        assertTrue(saved.isPresent());
        assertEquals(subscriptionGroupRepository.findById(subscriptionGroup.getId()).get(), saved.get());
    }

    @Sql(scripts = {"/sql/clearDb.sql", "/sql/add_users.sql", "/sql/add_subscription_groups.sql"})
    @Test
    public void shouldFindSubGroupById() {
        //given
        SubscriptionGroup subscriptionGroup = new SubscriptionGroup();
        subscriptionGroup.setId(1L);

        //when
        Optional<SubscriptionGroup> subGroupExpected = subscriptionGroupRepository.findById(subscriptionGroup.getId());

        //then
        assertTrue(subGroupExpected.isPresent());
        assertEquals(subGroupExpected.get().getId(), subscriptionGroup.getId());
    }

    @Sql(scripts = {"/sql/clearDb.sql", "/sql/add_users.sql", "/sql/add_subscription_groups.sql"})
    @Test
    public void shouldFindAllSubGroups() {
        //given
        int size = 4;

        //when
        List<SubscriptionGroup> subscriptionGroups = subscriptionGroupRepository.findAll();

        //then
        assertNotNull(subscriptionGroups);
        assertEquals(size, subscriptionGroups.size());
    }

    @Sql(scripts = {"/sql/clearDb.sql", "/sql/add_users.sql", "/sql/add_subscription_groups.sql"})
    @Test
    public void shouldUpdateSubGroupById() {

        //given
        SubscriptionGroup subscriptionGroup = subscriptionGroupRepository.findById(1L).get();

        //when
        subscriptionGroup.setName("New group");
        subscriptionGroupRepository.save(subscriptionGroup);
        int size = subscriptionGroupRepository.findAll().size();
        Optional<SubscriptionGroup> subGroupExpected = subscriptionGroupRepository.findById(subscriptionGroup.getId());

        //then
        assertTrue(subGroupExpected.isPresent());
        assertEquals("New group", subscriptionGroupRepository.findById(1L).get().getName());
        assertEquals(4, size);
    }

    @Sql(scripts = {"/sql/clearDb.sql", "/sql/add_users.sql", "/sql/add_subscription_groups.sql"})
    @Test
    public void shouldDeleteSubGroupById() {
        //given
        int size = 4;

        SubscriptionGroup subscriptionGroup = new SubscriptionGroup();
        subscriptionGroup.setId(1L);


        //when
        subscriptionGroupRepository.delete(subscriptionGroupRepository.findById(subscriptionGroup.getId()).get());

        //then
        assertNotEquals(size, subscriptionGroupRepository.findAll().size());
    }

}