package by.github.sendpulse.sptb.service;

import by.github.sendpulse.sptb.entity.User;
import by.github.sendpulse.sptb.repository.UserRepository;
import by.github.sendpulse.sptb.service.interfaces.UserService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        if (!userRepository.findById(user.getId()).isPresent()) {
            userRepository.save(user);
            logger.log(Level.INFO, "New user was added");
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void update(User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            userRepository.save(user);
            logger.log(Level.INFO, "User with id = " + user.getId() + " was updated");
        }
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
        logger.log(Level.INFO, "User with id = " + user.getId() + " was deleted");
    }
}
