package by.github.sendpulse.sptb.command.list;

import by.github.sendpulse.sptb.command.Command;
import by.github.sendpulse.sptb.entity.User;
import by.github.sendpulse.sptb.service.UserServiceImpl;
import by.github.sendpulse.sptb.service.interfaces.SendBotMessageService;
import by.github.sendpulse.sptb.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final UserService userService;

    public final static String START_MESSAGE = "Привет! Я SendPulse Telegram Bot. Я помогу тебе погрузиться в мир " +
            "email-маркетинга и не только!\n\n" +
            "С моей помощью ты сможешь получать уведомления об обновлениях " +
            "статей в блоге SendPulse из разделов, на которые ты подпишешься.\n\n" +
            "Ещё вместе со мной ты сможешь поиграть в интеллектуальную игру - <b>SendPulse Quiz</b>\n" +
            "Ты сможешь проверить свои знания и посоревноваться с друзьями.";

    public StartCommand(SendBotMessageService sendBotMessageService, UserService userService) {
        this.sendBotMessageService = sendBotMessageService;
        this.userService = userService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), START_MESSAGE);

        User newUser = new User();
        newUser.setId(update.getMessage().getChat().getId());
        newUser.setFirstName(update.getMessage().getChat().getFirstName());
        newUser.setLastName(update.getMessage().getChat().getLastName());
        newUser.setActive(true);
        userService.save(newUser);
    }
}
