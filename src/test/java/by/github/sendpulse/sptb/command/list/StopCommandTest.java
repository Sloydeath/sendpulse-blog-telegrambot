package by.github.sendpulse.sptb.command.list;

import by.github.sendpulse.sptb.command.Command;
import by.github.sendpulse.sptb.command.CommandName;

class StopCommandTest extends AbstractCommandTest{

    @Override
    Command getCommand() {
        return new StopCommand(sendBotMessageService, userService);
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