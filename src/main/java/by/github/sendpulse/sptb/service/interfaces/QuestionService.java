package by.github.sendpulse.sptb.service.interfaces;

import by.github.sendpulse.sptb.entity.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {

    void save(Question question);
    Optional<Question> findById(Long id);
    List<Question> findAll();
    void update(Question question);
    void deleteById(Question question);

}
