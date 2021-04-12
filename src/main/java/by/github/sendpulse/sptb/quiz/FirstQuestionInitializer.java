package by.github.sendpulse.sptb.quiz;

import by.github.sendpulse.sptb.command.list.parser.AddSubscriptionGroupCommand;
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

public class FirstQuestionInitializer {

    private static final Logger log = Logger.getLogger(FirstQuestionInitializer.class);

    private final UserService userService;
    private final QuestionService questionService;
    private final SendBotMessageService sendBotMessageService;
    private final Keyboard helpCommandKeyboard = new HelpCommandKeyboard();

    public FirstQuestionInitializer(UserService userService, QuestionService questionService, SendBotMessageService sendBotMessageService) {
        this.userService = userService;
        this.questionService = questionService;
        this.sendBotMessageService = sendBotMessageService;
    }

    public void sendFirstQuestion(Update update) {
        InlineKeyboardMarkup helpCommandInlineKeyboard = helpCommandKeyboard.createKeyBoard();

        if (userService.findById(update.getCallbackQuery().getMessage().getChat().getId()).isPresent()) {
            User currentUser = userService.findById(update.getCallbackQuery().getMessage().getChat().getId()).get();
            Question question;
            if (currentUser.isActive()) {
                if (currentUser.isQuizActive()) {
                    long questionCounter = 1;
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
                        String questionNumber = String.format("<b>Вопрос №%d</b>\n", questionCounter);
                        String questionName = String.format("<b>%s</b>\n\n" +
                                "Варианты ответов:\n", question.getText());
                        String firstPartOfVariants = String.format("1. %s\n", questionAnswersMap.get(0));
                        String secondPartOfVariants = String.format("2. %s\n", questionAnswersMap.get(1));
                        String thirdPartOfVariants = String.format("3. %s\n", questionAnswersMap.get(2));
                        String forthPartOfVariants = String.format("4. %s", questionAnswersMap.get(3));

                        Keyboard questionAnswersKeyboard = new QuestionAnswersKeyboard(questionAnswersMap, questionCounter);
                        InlineKeyboardMarkup questionAnswersInlineKeyboard = questionAnswersKeyboard.createKeyBoard();
                        sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), questionNumber);
                        sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), questionName);
                        sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), firstPartOfVariants);
                        sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), secondPartOfVariants);
                        sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), thirdPartOfVariants);
                        sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), forthPartOfVariants, questionAnswersInlineKeyboard);
                    }
                }
                else {
                    Keyboard keyboard = new StartQuizCommandKeyboard();
                    InlineKeyboardMarkup startQuizKeyboard =  keyboard.createKeyBoard();
                    sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), "Сначала запустите квиз", startQuizKeyboard);
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
}
