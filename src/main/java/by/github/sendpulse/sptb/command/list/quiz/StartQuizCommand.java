package by.github.sendpulse.sptb.command.list.quiz;

import by.github.sendpulse.sptb.command.Command;
import by.github.sendpulse.sptb.entity.User;
import by.github.sendpulse.sptb.keyboard.HelpCommandKeyboard;
import by.github.sendpulse.sptb.keyboard.Keyboard;
import by.github.sendpulse.sptb.quiz.FirstQuestionInitializer;
import by.github.sendpulse.sptb.quiz.SendPulseQuiz;
import by.github.sendpulse.sptb.service.interfaces.QuestionService;
import by.github.sendpulse.sptb.service.interfaces.QuizService;
import by.github.sendpulse.sptb.service.interfaces.SendBotMessageService;
import by.github.sendpulse.sptb.service.interfaces.UserService;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static by.github.sendpulse.sptb.command.CommandName.START;

public class StartQuizCommand implements Command {

    private static final Logger log = Logger.getLogger(StartQuizCommand.class);

    private final UserService userService;
    private final QuizService quizService;
    private final QuestionService questionService;
    private final SendBotMessageService sendBotMessageService;
    private final ExecutorService executors = Executors.newCachedThreadPool();
    private final Keyboard helpCommandKeyboard = new HelpCommandKeyboard();

    public StartQuizCommand(UserService userService, QuizService quizService, QuestionService questionService, SendBotMessageService sendBotMessageService) {
        this.userService = userService;
        this.quizService = quizService;
        this.questionService = questionService;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {

    }

    @Override
    public void executeWithCallBackQuery(Update update) {
        InlineKeyboardMarkup helpCommandInlineKeyboard = helpCommandKeyboard.createKeyBoard();

        if (userService.findById(update.getCallbackQuery().getMessage().getChat().getId()).isPresent()) {
            User currentUser = userService.findById(update.getCallbackQuery().getMessage().getChat().getId()).get();
            if (currentUser.isActive()) {
                if (currentUser.isQuizActive()) {
                    sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), "Квиз уже запущен. Скорее отвечайте на вопросы!)");
                    log.info("Quiz has already launched");
                }
                else {
                    if (quizService.findById(1L).isPresent()) {
                        currentUser.setQuizActive(true);
                        userService.update(currentUser);
                        executors.execute(new Thread(new SendPulseQuiz(userService, currentUser)));
                        new FirstQuestionInitializer(userService, questionService, sendBotMessageService).sendFirstQuestion(update);
                        log.info("Quiz launched");
                    }
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
