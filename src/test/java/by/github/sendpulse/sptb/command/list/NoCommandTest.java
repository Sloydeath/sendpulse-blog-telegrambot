package by.github.sendpulse.sptb.command.list;

import by.github.sendpulse.sptb.command.Command;

import static by.github.sendpulse.sptb.command.CommandName.NO;
import static org.junit.jupiter.api.Assertions.*;

class NoCommandTest extends AbstractCommandTest{

    @Override
    Command getCommand() {
        return new NoCommand(sendBotMessageService);
    }

    @Override
    String getCommandMessage() {
        return NoCommand.NO_MESSAGE;
    }

    @Override
    String getCommandName() {
        return NO.getCommandName();
    }
}