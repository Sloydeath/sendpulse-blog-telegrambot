package by.github.sendpulse.sptb.command.list.quiz;

import by.github.sendpulse.sptb.command.Command;
import by.github.sendpulse.sptb.entity.User;
import by.github.sendpulse.sptb.keyboard.HelpCommandKeyboard;
import by.github.sendpulse.sptb.keyboard.Keyboard;
import by.github.sendpulse.sptb.service.interfaces.SendBotMessageService;
import by.github.sendpulse.sptb.service.interfaces.UserService;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static by.github.sendpulse.sptb.command.CommandName.START;

public class StopQuizCommand implements Command {

    private static final Logger log = Logger.getLogger(StopQuizCommand.class);

    private final UserService userService;
    private final SendBotMessageService sendBotMessageService;
    private final Keyboard helpCommandKeyboard = new HelpCommandKeyboard();

    public StopQuizCommand(UserService userService, SendBotMessageService sendBotMessageService) {
        this.userService = userService;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {

    }

    @Override
    public void executeWithCallBackQuery(Update update) {
        User user;
        InlineKeyboardMarkup helpCommandInlineKeyboard = helpCommandKeyboard.createKeyBoard();

        if (userService.findById(update.getCallbackQuery().getMessage().getChat().getId()).isPresent()) {
            user = userService.findById(update.getCallbackQuery().getMessage().getChat().getId()).get();
            if (user.isActive()) {
                if (user.isQuizActive()) {
                    user.setQuizActive(false);
                    userService.update(user);
                    sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), "Вы остановили квиз!", helpCommandInlineKeyboard);
                }
                else {
                    sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), "Квиз уже был остановлен.", helpCommandInlineKeyboard);
                    log.info("Quiz has already stopped");
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
