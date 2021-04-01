package by.github.sendpulse.sptb.command.list;

import by.github.sendpulse.sptb.command.Command;
import by.github.sendpulse.sptb.service.interfaces.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.logging.Level;
import java.util.logging.Logger;

import static by.github.sendpulse.sptb.command.CommandName.*;

public class HelpCommand implements Command {

    private static final Logger logger = Logger.getLogger(String.valueOf(HelpCommand.class));

    private final SendBotMessageService sendBotMessageService;
    public static final String HELP_MESSAGE = String.format("<b>Дотупные команды</b>\n\n"

                    + "<b>Начать\\закончить работу с ботом</b>\n"
                    + "%s - начать работу со мной\n"
                    + "%s - приостановить работу со мной\n\n"
                    + "%s - получить помощь в работе со мной\n"
                    + "%s - посмотреть свой наивысший балл в квизе",
            START.getCommandName(), STOP.getCommandName(), HELP.getCommandName(), SHOW_USER_STATISTICS.getCommandName());

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }


    @Override
    public void execute(Update update) {
        logger.log(Level.INFO, "execution of HELP_MESSAGE");
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE);
    }
}
