package by.github.sendpulse.sptb.command.list;

import by.github.sendpulse.sptb.command.Command;
import by.github.sendpulse.sptb.keyboard.HelpCommandKeyboard;
import by.github.sendpulse.sptb.service.interfaces.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static by.github.sendpulse.sptb.command.CommandName.HELP;

public class UnknownCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final HelpCommandKeyboard helpCommandKeyboard = new HelpCommandKeyboard();

    public static final String UNKNOWN_MESSAGE = String.format("Упс.. Я не знаю такой команды.\n" +
            "Команда должна начинаться с '/'.\n" +
            "Список доступных команд можно посмотреть с помощью команды %s", HELP.getCommandName());

    public UnknownCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        InlineKeyboardMarkup helpCommandInlineKeyboard = helpCommandKeyboard.createKeyBoard();

        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), UNKNOWN_MESSAGE, helpCommandInlineKeyboard);
    }

    @Override
    public void executeWithCallBackQuery(Update update) {
        InlineKeyboardMarkup helpCommandInlineKeyboard = helpCommandKeyboard.createKeyBoard();

        sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), UNKNOWN_MESSAGE, helpCommandInlineKeyboard);
    }
}
