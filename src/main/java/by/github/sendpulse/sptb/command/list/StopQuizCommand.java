package by.github.sendpulse.sptb.command.list;

import by.github.sendpulse.sptb.command.Command;
import by.github.sendpulse.sptb.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StopQuizCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public StopQuizCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {

    }
}
