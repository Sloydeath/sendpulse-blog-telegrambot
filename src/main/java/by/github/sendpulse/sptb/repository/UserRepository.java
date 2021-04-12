package by.github.sendpulse.sptb.repository;

import by.github.sendpulse.sptb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM USER u WHERE u.active = true", nativeQuery = true)
    List<User> findAllIsActive();
}
