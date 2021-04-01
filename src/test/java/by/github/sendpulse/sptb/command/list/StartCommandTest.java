package by.github.sendpulse.sptb.command.list;

import by.github.sendpulse.sptb.command.Command;
import by.github.sendpulse.sptb.command.CommandName;

class StartCommandTest extends AbstractCommandTest{

    @Override
    Command getCommand() {
        return new StartCommand(sendBotMessageService, userService);
    }

    @Override
    String getCommandMessage() {
        return StartCommand.START_MESSAGE;
    }

    @Override
    String getCommandName() {
        return CommandName.START.getCommandName();
    }
}