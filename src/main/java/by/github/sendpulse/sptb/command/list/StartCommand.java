package by.github.sendpulse.sptb.command.list;

import by.github.sendpulse.sptb.command.Command;
import by.github.sendpulse.sptb.entity.User;
import by.github.sendpulse.sptb.keyboard.HelpCommandKeyboard;
import by.github.sendpulse.sptb.service.interfaces.SendBotMessageService;
import by.github.sendpulse.sptb.service.interfaces.UserService;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static by.github.sendpulse.sptb.command.CommandName.HELP;

public class StartCommand implements Command {

    private static final Logger log = Logger.getLogger(StartCommand.class);

    private final SendBotMessageService sendBotMessageService;
    private final UserService userService;
    private final HelpCommandKeyboard helpCommandKeyboard = new HelpCommandKeyboard();

    public final static String START_MESSAGE = String.format("Привет! Я SendPulse Telegram Bot. Я помогу тебе погрузиться в мир " +
            "email-маркетинга и не только!\n\n" +
            "С моей помощью ты сможешь получать уведомления об обновлениях " +
            "статей в блоге SendPulse из разделов, на которые ты подпишешься.\n\n" +
            "Ещё вместе со мной ты сможешь поиграть в интеллектуальную игру - <b>SendPulse Quiz</b>\n" +
            "Ты сможешь проверить свои знания и посоревноваться с друзьями.\n\n" +
            "Для просмотра доступных команд нажмите %s", HELP.getCommandName());

    public final static String EXCEPTION_MESSAGE = "Вы уже запустили бота!";

    public StartCommand(SendBotMessageService sendBotMessageService, UserService userService) {
        this.sendBotMessageService = sendBotMessageService;
        this.userService = userService;
    }

    @Override
    public void execute(Update update) {
        InlineKeyboardMarkup helpCommandInlineKeyboard = helpCommandKeyboard.createKeyBoard();

        User user;

        if (userService.findById(update.getMessage().getChat().getId()).isPresent()) {
            user = userService.findById(update.getMessage().getChat().getId()).get();
            if (user.isActive()) {
                sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), EXCEPTION_MESSAGE, helpCommandInlineKeyboard);
            }
            else {
                String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime());
                user.setDateOfRegistration(timeStamp);
                user.setActive(true);
                userService.update(user);
                sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), START_MESSAGE, helpCommandInlineKeyboard);
                log.info("User has started the bot");
            }
        }
        else {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime());

            user = new User();
            user.setDateOfRegistration(timeStamp);
            user.setId(update.getMessage().getChat().getId());
            user.setFirstName(update.getMessage().getChat().getFirstName());
            user.setLastName(update.getMessage().getChat().getLastName());
            user.setActive(true);
            userService.save(user);

            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), START_MESSAGE, helpCommandInlineKeyboard);
            log.info("User has started the bot");
        }
    }

    @Override
    public void executeWithCallBackQuery(Update update) {
        InlineKeyboardMarkup helpCommandInlineKeyboard = helpCommandKeyboard.createKeyBoard();

        User user;

        if (userService.findById(update.getCallbackQuery().getMessage().getChat().getId()).isPresent()) {
            user = userService.findById(update.getCallbackQuery().getMessage().getChat().getId()).get();
            if (user.isActive()) {
                sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), EXCEPTION_MESSAGE, helpCommandInlineKeyboard);
            }
            else {
                String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime());
                user.setDateOfRegistration(timeStamp);
                user.setActive(true);
                userService.update(user);
                sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), START_MESSAGE, helpCommandInlineKeyboard);
                log.info("User has started the bot");
            }
        }
        else {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime());

            user = new User();
            user.setDateOfRegistration(timeStamp);
            user.setId(update.getCallbackQuery().getMessage().getChat().getId());
            user.setFirstName(update.getCallbackQuery().getMessage().getChat().getFirstName());
            user.setLastName(update.getCallbackQuery().getMessage().getChat().getLastName());
            user.setActive(true);
            userService.save(user);

            sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), START_MESSAGE, helpCommandInlineKeyboard);
            log.info("User has started the bot");
        }
    }
}
