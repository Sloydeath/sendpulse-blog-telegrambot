package by.github.sendpulse.sptb.service;

import by.github.sendpulse.sptb.entity.Quiz;
import by.github.sendpulse.sptb.repository.QuizRepository;
import by.github.sendpulse.sptb.service.interfaces.QuizService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizServiceImpl implements QuizService {

    private static final Logger logger = Logger.getLogger(QuestionServiceImpl.class);
    private final QuizRepository quizRepository;

    @Autowired
    public QuizServiceImpl(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Override
    public void save(Quiz quiz) {
        quizRepository.save(quiz);
        logger.log(Level.INFO, "New quiz was added");
    }

    @Override
    public Optional<Quiz> findById(Long id) {
        return quizRepository.findById(id);
    }

    @Override
    public List<Quiz> findAll() {
        return quizRepository.findAll();
    }

    @Override
    public void update(Quiz quiz) {
        if (quizRepository.findById(quiz.getId()).isPresent()) {
            quizRepository.save(quiz);
            logger.log(Level.INFO, "Quiz with id = " + quiz.getId() + " was updated");
        }
    }

    @Override
    public void deleteById(Quiz quiz) {
        quizRepository.delete(quiz);
        logger.log(Level.INFO, "Quiz with id = " + quiz.getId() + " was deleted");
    }
}
