package by.github.sendpulse.sptb.repository;

import by.github.sendpulse.sptb.entity.Question;
import by.github.sendpulse.sptb.entity.Quiz;
import by.github.sendpulse.sptb.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Sql(scripts = {"/sql/clearDb.sql", "/sql/add_quiz.sql"})
    @Test
    public void shouldSaveQuestion() {
        //given
        Question question = new Question();
        question.setText("Как меня зовут?");
        question.setCorrectAnswer("Андрей");
        question.setOptionOne("Артём");
        question.setOptionTwo("Олег");
        question.setOptionThree("Никита");
        question.setPoints(10);

        Quiz quiz = new Quiz();
        quiz.setId(1L);
        question.setQuiz(quiz);

        //when
        questionRepository.save(question);

        //then
        Optional<Question> saved = questionRepository.findById(question.getId());
        assertTrue(saved.isPresent());
        assertEquals(questionRepository.findById(question.getId()).get(), saved.get());
    }

    @Sql(scripts = {"/sql/clearDb.sql", "/sql/add_quiz.sql", "/sql/add_questions.sql"})
    @Test
    public void shouldFindQuestionById() {
        //given
        Question question = new Question();
        question.setId(1L);
        question.setText("Как меня зовут?");
        question.setCorrectAnswer("Андрей");
        question.setOptionOne("Артём");
        question.setOptionTwo("Олег");
        question.setOptionThree("Никита");
        question.setPoints(10);

        Quiz quiz = new Quiz();
        quiz.setId(1L);
        question.setQuiz(quiz);

        //when
        Optional<Question> questionExpected = questionRepository.findById(question.getId());

        //then
        assertTrue(questionExpected.isPresent());
        assertEquals(questionExpected.get().getId(), question.getId());
    }

    @Sql(scripts = {"/sql/clearDb.sql", "/sql/add_quiz.sql", "/sql/add_questions.sql"})
    @Test
    public void shouldFindAllQuestions() {
        //given
        int size = 3;

        //when
        List<Question> questions = questionRepository.findAll();

        //then
        assertNotNull(questions);
        assertEquals(size, questions.size());
    }

    @Sql(scripts = {"/sql/clearDb.sql", "/sql/add_quiz.sql", "/sql/add_questions.sql"})
    @Test
    public void shouldUpdateQuestionById() {
        //given

        Question question = questionRepository.findById(1L).get();

        //when
        question.setText("Как меня зовут? Update");
        questionRepository.save(question);
        int size = questionRepository.findAll().size();
        Optional<Question> questionExpected = questionRepository.findById(question.getId());

        //then
        assertTrue(questionExpected.isPresent());
        assertEquals("Как меня зовут? Update", questionRepository.findById(1L).get().getText());
        assertEquals(3, size);
    }

    @Sql(scripts = {"/sql/clearDb.sql", "/sql/add_quiz.sql", "/sql/add_questions.sql"})
    @Test
    public void shouldDeleteUserById() {
        //given
        int size = 3;
        Question question = new Question();
        question.setId(1L);

        Quiz quiz = new Quiz();
        quiz.setId(1L);
        question.setQuiz(quiz);

        //when
        questionRepository.delete(questionRepository.findById(question.getId()).get());

        //then
        assertNotEquals(size, questionRepository.findAll().size());
    }
}