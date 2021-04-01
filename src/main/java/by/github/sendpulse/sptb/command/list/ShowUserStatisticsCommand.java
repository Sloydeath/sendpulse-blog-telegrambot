package by.github.sendpulse.sptb.command.list;

import by.github.sendpulse.sptb.command.Command;
import by.github.sendpulse.sptb.entity.User;
import by.github.sendpulse.sptb.service.interfaces.SendBotMessageService;
import by.github.sendpulse.sptb.service.interfaces.UserService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public class ShowUserStatisticsCommand implements Command {

    public static final String USER_STATISTIC_MESSAGE = "Твой лучший результат в квизе - <b>%d</b>";

    private final SendBotMessageService sendBotMessageService;
    private final UserService userService;

    public ShowUserStatisticsCommand(SendBotMessageService sendBotMessageService, UserService userService) {
        this.sendBotMessageService = sendBotMessageService;
        this.userService = userService;
    }

    @Override
    public void execute(Update update) {
        Optional<User> user = userService.findById(update.getMessage().getChat().getId());
        if (user.isPresent()) {
            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), String.format(USER_STATISTIC_MESSAGE, user.get().getHighScore()));
        }
        else {
            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), "Ошибка. Попробуйте нажать /start");
        }
    }
}
