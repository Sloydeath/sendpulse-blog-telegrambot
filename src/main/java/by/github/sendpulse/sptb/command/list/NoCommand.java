package by.github.sendpulse.sptb.command.list;

import by.github.sendpulse.sptb.command.Command;
import by.github.sendpulse.sptb.service.interfaces.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static by.github.sendpulse.sptb.command.CommandName.HELP;

public class NoCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    public static final String NO_MESSAGE = String.format("Такой команды я не знаю.\n" +
            "Посмотри доступные команды, нажав на %s", HELP.getCommandName());

    public NoCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), NO_MESSAGE);
    }
}
