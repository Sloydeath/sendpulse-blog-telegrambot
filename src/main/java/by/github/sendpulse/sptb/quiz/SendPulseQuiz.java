package by.github.sendpulse.sptb.quiz;

import by.github.sendpulse.sptb.entity.User;
import by.github.sendpulse.sptb.service.interfaces.UserService;
import org.apache.log4j.Logger;

public class SendPulseQuiz implements Runnable{

    private static final Logger log = Logger.getLogger(SendPulseQuiz.class);

    private final UserService userService;
    private final User currentUser;

    public SendPulseQuiz(UserService userService, User currentUser) {
        this.userService = userService;
        this.currentUser = currentUser;
    }

    @Override
    public void run() {
        log.info("SendPulse quiz has started");
        launchQuiz();
    }

    private void launchQuiz() {
        try {
            Thread.sleep(1000 * 60 * 15);
        } catch (InterruptedException interruptedException) {
            log.error(interruptedException);
        } finally {
            if (currentUser.isQuizActive()) {
                currentUser.setQuizActive(false);
                userService.update(currentUser);
            }
        }
    }
}

/*

        int questionCounter = 1;
        List<Question> questions = questionService.findAll();

        for (Question question: questions) {
        List<String> questionAnswers = new ArrayList<>(4);
        questionAnswers.add(question.getOptionOne());
        questionAnswers.add(question.getOptionTwo());
        questionAnswers.add(question.getOptionThree());
        questionAnswers.add(question.getCorrectAnswer());
        Collections.shuffle(questionAnswers);
        Map<Integer, String> questionAnswersMap = new LinkedHashMap<>(4);
        for (int i = 0; i < 4; i++) {
        questionAnswersMap.put(i + 1, questionAnswers.get(i));
        }

        if (questionCounter <= 10) {
        String questionContext = String.format("<b>Вопрос №%d</b>\n" +
        "%s\n\n" +
        "Варианты ответов:\n" +
        "1. %s\n" +
        "2. %s\n" +
        "3. %s\n" +
        "4. %s\n\n" +
        "На ответ даётся 1 минута", questionCounter, question.getText(),
        questionAnswers.get(0), questionAnswers.get(1), questionAnswers.get(2), questionAnswers.get(3));
        InlineKeyboardMarkup questionInlineKeyboard;
        }
*/
