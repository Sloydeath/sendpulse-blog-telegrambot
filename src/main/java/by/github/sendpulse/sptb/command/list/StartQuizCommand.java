package by.github.sendpulse.sptb.command.list;

import by.github.sendpulse.sptb.command.Command;
import by.github.sendpulse.sptb.service.interfaces.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartQuizCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public StartQuizCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {

    }
}
