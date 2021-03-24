package by.github.sendpulse.sptb.command.list;

import by.github.sendpulse.sptb.command.Command;
import by.github.sendpulse.sptb.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ShowUserStatisticsCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public ShowUserStatisticsCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {

    }
}
