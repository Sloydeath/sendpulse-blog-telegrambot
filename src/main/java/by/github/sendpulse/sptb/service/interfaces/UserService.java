package by.github.sendpulse.sptb.service.interfaces;


import by.github.sendpulse.sptb.entity.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void save(User user);
    Optional<User> findById(Long id);
    List<User> findAllIsActive();
    List<User> findAll();
    void update(User user);
    void delete(User user);

}
