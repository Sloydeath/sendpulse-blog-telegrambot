package by.github.sendpulse.sptb.command.list.quiz;

import by.github.sendpulse.sptb.command.Command;
import by.github.sendpulse.sptb.entity.Question;
import by.github.sendpulse.sptb.entity.User;
import by.github.sendpulse.sptb.keyboard.HelpCommandKeyboard;
import by.github.sendpulse.sptb.keyboard.Keyboard;
import by.github.sendpulse.sptb.keyboard.QuestionAnswersKeyboard;
import by.github.sendpulse.sptb.keyboard.StartQuizCommandKeyboard;
import by.github.sendpulse.sptb.service.interfaces.QuestionService;
import by.github.sendpulse.sptb.service.interfaces.SendBotMessageService;
import by.github.sendpulse.sptb.service.interfaces.UserService;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.*;

import static by.github.sendpulse.sptb.command.CommandName.START;
import static by.github.sendpulse.sptb.command.CommandName.START_QUIZ;

public class NextQuestionCommand implements Command {

    private static final Logger log = Logger.getLogger(NextQuestionCommand.class);

    private final UserService userService;
    private final QuestionService questionService;
    private final SendBotMessageService sendBotMessageService;
    private final Keyboard helpCommandKeyboard = new HelpCommandKeyboard();

    public NextQuestionCommand(UserService userService, QuestionService questionService, SendBotMessageService sendBotMessageService) {
        this.userService = userService;
        this.questionService = questionService;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {

    }

    @Override
    public void executeWithCallBackQuery(Update update) {
        InlineKeyboardMarkup helpCommandInlineKeyboard = helpCommandKeyboard.createKeyBoard();

        //this array consists of ["/command", "hashcode of answer", "question id"]
        String[] receivedMessage = update.getCallbackQuery().getData().trim().split("\\.");

        long questionId = Long.parseLong(receivedMessage[2].trim());
        boolean isAnswerCorrect = isAnswerCorrect(questionId, Integer.parseInt(receivedMessage[1]));
        String isAnswerCorrectMessage;

        if (userService.findById(update.getCallbackQuery().getMessage().getChat().getId()).isPresent()) {
            User currentUser = userService.findById(update.getCallbackQuery().getMessage().getChat().getId()).get();
            int currentScore = currentUser.getCurrentScore();

            if (isAnswerCorrect && questionService.findById(questionId).isPresent()) {
                currentScore += questionService.findById(questionId).get().getPoints();
                currentUser.setCurrentScore(currentScore);
                userService.update(currentUser);
                isAnswerCorrectMessage = String.format("Правильный ответ! Молодец!\n" +
                        "Твой текущий счёт - <b>%d</b>\n\n", currentScore);
            }
            else {
                isAnswerCorrectMessage = String.format("Неправильный ответ :( \n" +
                        "Ты обязательно ответишь правильно в следующий раз! \n" +
                        "Твой текущий счёт - <b>%d</b>\n\n", currentScore);
            }

            Question question;
            if (currentUser.isActive()) {
                if (currentUser.isQuizActive()) {
                    long questionCounter = questionId + 1;
                    if (questionCounter <= 10) {
                        if (questionService.findById(questionCounter).isPresent()) {
                            question = questionService.findById(questionCounter).get();

                            List<String> questionAnswers = new ArrayList<>(4);
                            questionAnswers.add(question.getOptionOne());
                            questionAnswers.add(question.getOptionTwo());
                            questionAnswers.add(question.getOptionThree());
                            questionAnswers.add(question.getCorrectAnswer());
                            Collections.shuffle(questionAnswers);
                            Map<Integer, String> questionAnswersMap = new LinkedHashMap<>(4);
                            for (int i = 0; i < 4; i++) {
                                questionAnswersMap.put(i, questionAnswers.get(i));
                            }
                            QuestionAnswersKeyboard questionAnswersKeyboard = new QuestionAnswersKeyboard(questionAnswersMap, questionCounter);
                            InlineKeyboardMarkup questionAnswersInlineKeyboard = questionAnswersKeyboard.createKeyBoard();

                            String questionNumber = String.format("<b>Вопрос №%d</b>\n", questionCounter);
                            String questionName = String.format("<b>%s</b>\n\n" +
                                    "Варианты ответов:\n", question.getText());
                            String firstPartOfVariants = String.format("1. %s\n", questionAnswersMap.get(0));
                            String secondPartOfVariants = String.format("2. %s\n", questionAnswersMap.get(1));
                            String thirdPartOfVariants = String.format("3. %s\n", questionAnswersMap.get(2));
                            String forthPartOfVariants = String.format("4. %s", questionAnswersMap.get(3));

                            sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), isAnswerCorrectMessage);
                            sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), questionNumber);
                            sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), questionName);
                            sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), firstPartOfVariants);
                            sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), secondPartOfVariants);
                            sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), thirdPartOfVariants);
                            sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), forthPartOfVariants, questionAnswersInlineKeyboard);

                        }
                        else {
                            sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), "Ошибка. Пожалуйста, напишите в тех. поддержку", helpCommandInlineKeyboard);
                            log.error("Question doesn't exists");
                        }
                    }
                    else {
                        currentUser.setQuizActive(false);
                        if (currentScore > currentUser.getHighScore()) {
                            currentUser.setHighScore(currentScore);
                        }
                        currentUser.setCurrentScore(0);
                        userService.update(currentUser);
                        String quizEndMessage = String.format("Ты завершил квиз!\n" +
                                "Твой результат - <b>%d из %d</b> вопросов!\n" +
                                "Твой счёт - <b>%d</b>\n" +
                                "Ты всегда можешь попробовать свои силы в квизе снова! Жду тебя.",
                                currentScore, questionId, currentScore);
                        sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), quizEndMessage, helpCommandInlineKeyboard);
                    }
                }
                else {
                    Keyboard keyboard = new StartQuizCommandKeyboard();
                    InlineKeyboardMarkup startQuizKeyboard =  keyboard.createKeyBoard();
                    sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), String.format("Сначала запустите квиз. %s", START_QUIZ.getCommandName()), startQuizKeyboard);
                    log.info("Quiz isn't started");
                }
            }
            else {
                sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), String.format("Сначала запустите бота! %s", START.getCommandName()), helpCommandInlineKeyboard);
                log.info("User didn't start the bot");
            }
        }
        else {
            sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), String.format("Сначала запустите бота! %s", START.getCommandName()), helpCommandInlineKeyboard);
            log.info("User didn't start the bot");
        }
    }

    private boolean isAnswerCorrect(long id, int answer) {
        if (questionService.findById(id).isPresent()) {
            Question question = questionService.findById(id).get();
            return (question.getCorrectAnswer().hashCode() == answer);
        }
        return false;
    }
}
