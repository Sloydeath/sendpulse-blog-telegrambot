package by.github.sendpulse.sptb.command.list;

import by.github.sendpulse.sptb.command.Command;
import by.github.sendpulse.sptb.command.CommandName;
import org.junit.jupiter.api.DisplayName;

import static by.github.sendpulse.sptb.command.CommandName.HELP;
import static by.github.sendpulse.sptb.command.list.HelpCommand.HELP_MESSAGE;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit test for HelpCommand")
class HelpCommandTest extends AbstractCommandTest{

    @Override
    Command getCommand() {
        return new HelpCommand(sendBotMessageService);
    }

    @Override
    String getCommandMessage() {
        return HELP_MESSAGE;
    }

    @Override
    String getCommandName() {
        return HELP.getCommandName();
    }
}