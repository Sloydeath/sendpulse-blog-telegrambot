package by.github.sendpulse.sptb.command.list;

import by.github.sendpulse.sptb.command.Command;
import by.github.sendpulse.sptb.command.CommandName;

import static org.junit.jupiter.api.Assertions.*;

class UnknownCommandTest extends AbstractCommandTest{

    @Override
    Command getCommand() {
        return new UnknownCommand(sendBotMessageService);
    }

    @Override
    String getCommandMessage() {
        return UnknownCommand.UNKNOWN_MESSAGE;
    }

    @Override
    String getCommandName() {
        return "/someUnknownCommand";
    }
}