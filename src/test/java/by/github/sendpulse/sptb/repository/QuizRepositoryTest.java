package by.github.sendpulse.sptb.repository;

import by.github.sendpulse.sptb.entity.Question;
import by.github.sendpulse.sptb.entity.Quiz;
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
class QuizRepositoryTest {


    @Autowired
    private QuizRepository quizRepository;

    @Sql(scripts = {"/sql/clearDb.sql", "/sql/add_quiz.sql"})
    @Test
    public void shouldSaveQuiz() {
        //given
        Quiz quiz = new Quiz();
        quiz.setId(2L);
        quiz.setDescription("Some information about quiz");
        quiz.setTitle("test quiz");

        //when
        quizRepository.save(quiz);

        //then
        Optional<Quiz> saved = quizRepository.findById(quiz.getId());
        assertTrue(saved.isPresent());
        assertEquals(quizRepository.findById(quiz.getId()).get(), saved.get());
    }

    @Sql(scripts = {"/sql/clearDb.sql", "/sql/add_quiz.sql"})
    @Test
    public void shouldFindQuizById() {
        //given
        Quiz quiz = new Quiz();
        quiz.setId(1L);
        quiz.setDescription("Квиз для интеграционных тестов");
        quiz.setTitle("Тестовый квиз");

        //when
        Optional<Quiz> quizExpected = quizRepository.findById(quiz.getId());

        //then
        assertTrue(quizExpected.isPresent());
        assertEquals(quizExpected.get().getId(), quiz.getId());
    }

    @Sql(scripts = {"/sql/clearDb.sql", "/sql/add_quiz.sql"})
    @Test
    public void shouldFindAllQuestions() {
        //given
        int size = 1;

        //when
        List<Quiz> quizzes = quizRepository.findAll();

        //then
        assertNotNull(quizzes);
        assertEquals(size, quizzes.size());
    }

    @Sql(scripts = {"/sql/clearDb.sql", "/sql/add_quiz.sql"})
    @Test
    public void shouldUpdateQuizById() {
        //given

        Quiz quiz = quizRepository.findById(1L).get();

        //when
        quiz.setDescription("description was updated");
        quizRepository.save(quiz);
        int size = quizRepository.findAll().size();
        Optional<Quiz> quizExpected = quizRepository.findById(quiz.getId());

        //then
        assertTrue(quizExpected.isPresent());
        assertEquals("description was updated", quizRepository.findById(1L).get().getDescription());
        assertEquals(1, size);
    }

    @Sql(scripts = {"/sql/clearDb.sql", "/sql/add_quiz.sql"})
    @Test
    public void shouldDeleteQuizById() {
        //given
        int size = 1;

        Quiz quiz = new Quiz();
        quiz.setId(1L);


        //when
        quizRepository.delete(quizRepository.findById(quiz.getId()).get());

        //then
        assertNotEquals(size, quizRepository.findAll().size());
    }
}