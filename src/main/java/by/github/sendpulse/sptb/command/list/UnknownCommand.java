package by.github.sendpulse.sptb.command.list;

import by.github.sendpulse.sptb.command.Command;
import by.github.sendpulse.sptb.service.interfaces.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static by.github.sendpulse.sptb.command.CommandName.HELP;

public class UnknownCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    public static final String UNKNOWN_MESSAGE = String.format("Упс.. Я не знаю такой команды.\n" +
            "Команда должна начинаться с '/'.\n" +
            "Список доступных команд можно посмотреть с помощью команды %s", HELP.getCommandName());

    public UnknownCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), UNKNOWN_MESSAGE);
    }
}
