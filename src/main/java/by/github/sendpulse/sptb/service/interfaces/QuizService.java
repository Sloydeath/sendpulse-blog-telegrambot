package by.github.sendpulse.sptb.service.interfaces;

import by.github.sendpulse.sptb.entity.Quiz;

import java.util.List;
import java.util.Optional;

public interface QuizService {

    void save(Quiz quiz);
    Optional<Quiz> findById(Long id);
    List<Quiz> findAll();
    void update(Quiz quiz);
    void deleteById(Quiz quiz);

}
