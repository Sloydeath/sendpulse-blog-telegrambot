package by.github.sendpulse.sptb.service;

import by.github.sendpulse.sptb.entity.Question;
import by.github.sendpulse.sptb.repository.QuestionRepository;
import by.github.sendpulse.sptb.service.interfaces.QuestionService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    private static final Logger logger = Logger.getLogger(QuestionServiceImpl.class);
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public void save(Question question) {
        questionRepository.save(question);
        logger.log(Level.INFO, "New question was added");
    }

    @Override
    public Optional<Question> findById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public void update(Question question) {
        if (questionRepository.findById(question.getId()).isPresent()) {
            questionRepository.save(question);
            logger.log(Level.INFO, "Question with id = " + question.getId() + " was updated");
        }
    }

    @Override
    public void deleteById(Question question) {
        questionRepository.delete(question);
        logger.log(Level.INFO, "Question with id = " + question.getId() + " was deleted");
    }
}
