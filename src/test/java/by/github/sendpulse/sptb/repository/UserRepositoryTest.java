package by.github.sendpulse.sptb.repository;

import by.github.sendpulse.sptb.entity.User;
import org.assertj.core.api.Assertions;
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

/**
 * Integration-level testing for {@link UserRepository}.
 */

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Sql(scripts = {"/sql/clearDb.sql"})
    @Test
    public void shouldSaveUser() {
        //given
        User user = new User();
        user.setId(1243553389L);
        user.setFirstName("Danila");
        user.setActive(true);

        //when
        userRepository.save(user);

        //then
        Optional<User> saved = userRepository.findById(user.getId());
        assertTrue(saved.isPresent());
        assertEquals(userRepository.findById(user.getId()).get(), saved.get());
    }

    @Sql(scripts = {"/sql/clearDb.sql", "/sql/add_users.sql"})
    @Test
    public void shouldFindUserById() {
        //given
        User user = new User();
        user.setId(634324421L);
        user.setFirstName("Nik");
        user.setLastName("Baranik");
        user.setHighScore(2);
        user.setActive(true);

        //when
        Optional<User> userExpected = userRepository.findById(user.getId());

        //then
        assertTrue(userExpected.isPresent());
        assertEquals(userExpected.get().getId(), user.getId());
    }

    @Sql(scripts = {"/sql/clearDb.sql", "/sql/add_users.sql"})
    @Test
    public void shouldFileAllUsers() {
        //given
        int size = 7;

        //when
        List<User> users = userRepository.findAll();

        //then
        assertNotNull(users);
        assertEquals(size, users.size());
    }

    @Sql(scripts = {"/sql/clearDb.sql"})
    @Test
    public void shouldUpdateUserById() {
        //given
        User user = new User();
        user.setId(123456780L);
        user.setFirstName("Kaka");
        user.setActive(true);

        //when
        userRepository.save(user);
        user.setLastName("Lester");
        userRepository.save(user);
        int size = userRepository.findAll().size();

        //then
        assertTrue(userRepository.findById(user.getId()).isPresent());
        assertEquals("Lester", userRepository.findById(user.getId()).get().getLastName());
        assertEquals(1, size);
    }

    @Sql(scripts = {"/sql/clearDb.sql", "/sql/add_users.sql"})
    @Test
    public void shouldDeleteUserById() {
        //given
        int size = 7;
        User user = new User();
        user.setId(123456782L);

        //when
        userRepository.delete(userRepository.findById(user.getId()).get());

        //then
        assertNotEquals(size, userRepository.findAll().size());
    }
}