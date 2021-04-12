package by.github.sendpulse.sptb.command.list;

import by.github.sendpulse.sptb.command.Command;
import by.github.sendpulse.sptb.entity.User;
import by.github.sendpulse.sptb.keyboard.HelpCommandKeyboard;
import by.github.sendpulse.sptb.service.interfaces.SendBotMessageService;
import by.github.sendpulse.sptb.service.interfaces.SubscriptionGroupService;
import by.github.sendpulse.sptb.service.interfaces.UserService;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.HashSet;

import static by.github.sendpulse.sptb.command.CommandName.START;

public class StopCommand implements Command {

    private static final Logger log = Logger.getLogger(StopCommand.class);

    private final SendBotMessageService sendBotMessageService;
    private final UserService userService;

    public static final String STOP_MESSAGE = "Вы остановили SendPulse-бота и больше не будете получать рассылку.\n" +
            "Надеюсь, что скоро мы увидимся снова!";
    public final static String EXCEPTION_MESSAGE = "Вы уже отписаны от бота, но всегда можете вернуться!";

    public StopCommand(SendBotMessageService sendBotMessageService, UserService userService) {
        this.sendBotMessageService = sendBotMessageService;
        this.userService = userService;
    }

    @Override
    public void execute(Update update) {
        User user;
        if (userService.findById(update.getMessage().getChat().getId()).isPresent()) {
            if (!userService.findById(update.getMessage().getChat().getId()).get().isActive()) {
                sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), EXCEPTION_MESSAGE);
            }
            else {
                user = userService.findById(update.getMessage().getChat().getId()).get();
                user.setActive(false);

                userService.update(user);

                sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), STOP_MESSAGE);
            }
        }
        else {
            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), String.format("Сначала запустите бота! %s", START.getCommandName()));
            log.info("User didn't start the bot");
        }
    }

    @Override
    public void executeWithCallBackQuery(Update update) {
        User user;
        if (userService.findById(update.getCallbackQuery().getMessage().getChat().getId()).isPresent()) {
            if (!userService.findById(update.getCallbackQuery().getMessage().getChat().getId()).get().isActive()) {
                sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), EXCEPTION_MESSAGE);
            }
            else {
                user = userService.findById(update.getCallbackQuery().getMessage().getChat().getId()).get();
                user.setActive(false);
                user.setQuizActive(false);

                userService.update(user);

                sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), STOP_MESSAGE);
            }
        }
        else {
            sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), String.format("Сначала запустите бота! %s", START.getCommandName()));
            log.info("User didn't start the bot");
        }
    }
}
