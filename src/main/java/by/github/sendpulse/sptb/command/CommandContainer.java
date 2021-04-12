package by.github.sendpulse.sptb.command;

import by.github.sendpulse.sptb.command.list.*;
import by.github.sendpulse.sptb.command.list.parser.AddSubscriptionGroupCommand;
import by.github.sendpulse.sptb.command.list.parser.DeleteGroupFromSubscriptionsCommand;
import by.github.sendpulse.sptb.command.list.parser.ListSubscriptionGroupCommand;
import by.github.sendpulse.sptb.command.list.parser.ShowUserSubscriptionsCommand;
import by.github.sendpulse.sptb.command.list.quiz.*;
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
                .put(STOP.getCommandName(), new StopCommand(sendBotMessageService, userService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))

                .put(ADD_GROUP_SUB.getCommandName(), new AddSubscriptionGroupCommand(sendBotMessageService, subscriptionGroupService, userService))
                .put(LIST_GROUP_SUB.getCommandName(), new ListSubscriptionGroupCommand(subscriptionGroupService, sendBotMessageService, userService))
                .put(DELETE_GROUP_FROM_SUBS.getCommandName(), new DeleteGroupFromSubscriptionsCommand(userService, subscriptionGroupService, sendBotMessageService))
                .put(MY_SUBS.getCommandName(), new ShowUserSubscriptionsCommand(userService, sendBotMessageService))

                .put(QUIZ.getCommandName(), new QuizCommand(userService, quizService, sendBotMessageService))
                .put(START_QUIZ.getCommandName(), new StartQuizCommand(userService, quizService, questionService, sendBotMessageService))
                .put(STOP_QUIZ.getCommandName(), new StopQuizCommand(userService, sendBotMessageService))
                .put(NEXT_QUESTION.getCommandName(), new NextQuestionCommand(userService, questionService, sendBotMessageService))
                .put(SHOW_USER_STATISTICS.getCommandName(), new ShowUserStatisticsCommand(sendBotMessageService, userService))

                .build();
        unknownCommand = new UnknownCommand(sendBotMessageService);

    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}
