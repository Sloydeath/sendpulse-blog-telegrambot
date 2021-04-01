package by.github.sendpulse.sptb.command;

import by.github.sendpulse.sptb.command.list.*;
import by.github.sendpulse.sptb.service.interfaces.*;
import com.google.common.collect.ImmutableMap;

import static by.github.sendpulse.sptb.command.CommandName.*;

public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendBotMessageService sendBotMessageService,
                            UserService userService,
                            SubscriptionGroupService subscriptionGroupService,
                            QuizService quizService,
                            QuestionService questionService) {

        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService, userService))
                .put(STOP.getCommandName(), new StopCommand(sendBotMessageService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(START_QUIZ.getCommandName(), new StartQuizCommand(sendBotMessageService))
                .put(STOP_QUIZ.getCommandName(), new StopQuizCommand(sendBotMessageService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .put(SHOW_USER_STATISTICS.getCommandName(), new ShowUserStatisticsCommand(sendBotMessageService, userService))
                .build();
        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}
