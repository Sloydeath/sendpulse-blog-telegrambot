package by.github.sendpulse.sptb.command.list;

import by.github.sendpulse.sptb.command.Command;
import by.github.sendpulse.sptb.command.CommandName;

import static org.junit.jupiter.api.Assertions.*;

class StopCommandTest extends AbstractCommandTest{

    @Override
    Command getCommand() {
        return new StopCommand(sendBotMessageService);
    }

    @Override
    String getCommandMessage() {
        return StopCommand.STOP_MESSAGE;
    }

    @Override
    String getCommandName() {
        return CommandName.STOP.getCommandName();
    }
}