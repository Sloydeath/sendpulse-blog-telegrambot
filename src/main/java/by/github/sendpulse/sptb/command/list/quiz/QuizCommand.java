package by.github.sendpulse.sptb.command.list.quiz;

import by.github.sendpulse.sptb.command.Command;
import by.github.sendpulse.sptb.entity.Quiz;
import by.github.sendpulse.sptb.entity.User;
import by.github.sendpulse.sptb.keyboard.HelpCommandKeyboard;
import by.github.sendpulse.sptb.keyboard.Keyboard;
import by.github.sendpulse.sptb.keyboard.StartQuizCommandKeyboard;
import by.github.sendpulse.sptb.service.interfaces.QuizService;
import by.github.sendpulse.sptb.service.interfaces.SendBotMessageService;
import by.github.sendpulse.sptb.service.interfaces.UserService;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static by.github.sendpulse.sptb.command.CommandName.START;

public class QuizCommand implements Command {

    private static final Logger log = Logger.getLogger(QuizCommand.class);

    private final UserService userService;
    private final QuizService quizService;
    private final SendBotMessageService sendBotMessageService;
    private final Keyboard helpCommandKeyboard = new HelpCommandKeyboard();

    public QuizCommand(UserService userService, QuizService quizService, SendBotMessageService sendBotMessageService) {
        this.userService = userService;
        this.quizService = quizService;
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
                if (quizService.findById(1L).isPresent()) {
                    Keyboard startQuizCommandKeyboard = new StartQuizCommandKeyboard();
                    InlineKeyboardMarkup startQuizCommandInlineKeyboard = startQuizCommandKeyboard.createKeyBoard();
                    Quiz quiz = quizService.findById(1L).get();
                    String quizMessage = String.format("Информация о <b>%s</b>!\n\n" +
                                    "%s\n\n" +
                                    "Можете приступать к ответам на вопросы.\n\n" +
                                    "Через 15 минут квиз автоматически завершится, если вы передумаете его проходить.\n\n" +
                                    "Начинаем квиз?",
                            quiz.getTitle(), quiz.getDescription());
                    sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), quizMessage, startQuizCommandInlineKeyboard);
                    log.info("Quiz info has gotten");
                }
                else {
                    sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), "Ошибка. Пожалуйста, напишите в тех. поддержку", helpCommandInlineKeyboard);
                    log.error("Question doesn't exists");
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