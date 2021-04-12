package by.github.sendpulse.sptb.command.list;

import by.github.sendpulse.sptb.command.Command;
import by.github.sendpulse.sptb.keyboard.HelpCommandKeyboard;
import by.github.sendpulse.sptb.service.interfaces.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.logging.Level;
import java.util.logging.Logger;

import static by.github.sendpulse.sptb.command.CommandName.*;

public class HelpCommand implements Command {

    private static final Logger logger = Logger.getLogger(String.valueOf(HelpCommand.class));

    private final SendBotMessageService sendBotMessageService;
    private final HelpCommandKeyboard helpCommandKeyboard = new HelpCommandKeyboard();

    public static final String HELP_MESSAGE = String.format("<b>Дотупные команды</b>\n\n"

                    + "<b>Начать\\закончить работу с ботом</b>\n"
                    + "%s - начать работу со мной\n"
                    + "%s - приостановить работу со мной\n\n"
                    + "%s - получить помощь в работе со мной\n\n"
                    + "%s - посмотреть свой наивысший балл в квизе\n\n"
                    + "%s - посмотреть список доступных групп для подписки\n\n"
                    + "%s - добавить группу в подписки\n(Пример: %s 5)\n\n"
                    + "%s - удалить группу из подписок\n\n"
                    + "%s - посмотреть список моих подписок\n",

            START.getCommandName(), STOP.getCommandName(), HELP.getCommandName(), SHOW_USER_STATISTICS.getCommandName(),
            LIST_GROUP_SUB.getCommandName(), ADD_GROUP_SUB.getCommandName(), ADD_GROUP_SUB.getCommandName(),
            DELETE_GROUP_FROM_SUBS.getCommandName(), MY_SUBS.getCommandName());

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        logger.log(Level.INFO, "execution of HELP_MESSAGE");
        InlineKeyboardMarkup helpCommandInlineKeyboard = helpCommandKeyboard.createKeyBoard();
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE, helpCommandInlineKeyboard);
    }

    @Override
    public void executeWithCallBackQuery(Update update) {
        logger.log(Level.INFO, "execution of HELP_MESSAGE");
        InlineKeyboardMarkup helpCommandInlineKeyboard = helpCommandKeyboard.createKeyBoard();
        sendBotMessageService.sendMessage(
                update.getCallbackQuery().getMessage().getChatId().toString(),
                HELP_MESSAGE,
                helpCommandInlineKeyboard);
    }
}
