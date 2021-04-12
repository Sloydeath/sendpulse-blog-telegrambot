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

import java.util.Optional;

import static by.github.sendpulse.sptb.command.CommandName.START;

public class ShowUserStatisticsCommand implements Command {

    private static final Logger log = Logger.getLogger(ShowUserStatisticsCommand.class);
    public static final String USER_STATISTIC_MESSAGE = "Твой лучший результат в квизе - <b>%d</b>";

    private final SendBotMessageService sendBotMessageService;
    private final UserService userService;
    private final Keyboard helpCommandKeyboard = new HelpCommandKeyboard();

    public ShowUserStatisticsCommand(SendBotMessageService sendBotMessageService, UserService userService) {
        this.sendBotMessageService = sendBotMessageService;
        this.userService = userService;
    }

    @Override
    public void execute(Update update) {
        InlineKeyboardMarkup helpCommandInlineKeyboard = helpCommandKeyboard.createKeyBoard();

        Optional<User> user = userService.findById(update.getMessage().getChat().getId());
        if (user.isPresent()) {
            if (user.get().isActive()) {
                sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), String.format(USER_STATISTIC_MESSAGE, user.get().getHighScore()), helpCommandInlineKeyboard);
            }
            else {
                sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), String.format("Ошибка. Попробуйте нажать %s", START.getCommandName()), helpCommandInlineKeyboard);
                log.info("User didn't start the bot");
            }
        }
        else {
            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), String.format("Ошибка. Попробуйте нажать %s", START.getCommandName()), helpCommandInlineKeyboard);
            log.info("User didn't start the bot");
        }
    }

    @Override
    public void executeWithCallBackQuery(Update update) {
        InlineKeyboardMarkup helpCommandInlineKeyboard = helpCommandKeyboard.createKeyBoard();

        Optional<User> user = userService.findById(update.getCallbackQuery().getMessage().getChat().getId());
        if (user.isPresent()) {
            if (user.get().isActive()) {
                sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), String.format(USER_STATISTIC_MESSAGE, user.get().getHighScore()), helpCommandInlineKeyboard);
            }
            else {
                sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), String.format("Ошибка. Попробуйте нажать %s", START.getCommandName()), helpCommandInlineKeyboard);
                log.info("User didn't start the bot");
            }
        }
        else {
            sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), String.format("Ошибка. Попробуйте нажать %s", START.getCommandName()), helpCommandInlineKeyboard);
            log.info("User didn't start the bot");
        }
    }
}
