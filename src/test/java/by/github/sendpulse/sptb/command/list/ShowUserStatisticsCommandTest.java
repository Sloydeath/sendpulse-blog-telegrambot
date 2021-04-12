package by.github.sendpulse.sptb.command.list;

import by.github.sendpulse.sptb.command.Command;
import by.github.sendpulse.sptb.command.CommandName;
import by.github.sendpulse.sptb.command.list.quiz.ShowUserStatisticsCommand;
import org.junit.jupiter.api.DisplayName;

import static by.github.sendpulse.sptb.command.list.quiz.ShowUserStatisticsCommand.USER_STATISTIC_MESSAGE;

@DisplayName("Unit test for ShowUserStatisticsCommand")
class ShowUserStatisticsCommandTest extends AbstractCommandTest{



    @Override
    Command getCommand() {
        return new ShowUserStatisticsCommand(sendBotMessageService, userService);
    }

    @Override
    String getCommandMessage() {
        return USER_STATISTIC_MESSAGE;
    }

    @Override
    String getCommandName() {
        return CommandName.SHOW_USER_STATISTICS.getCommandName();
    }
}