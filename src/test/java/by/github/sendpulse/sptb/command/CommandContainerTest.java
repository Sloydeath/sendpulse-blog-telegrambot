package by.github.sendpulse.sptb.command;

import by.github.sendpulse.sptb.command.list.UnknownCommand;
import by.github.sendpulse.sptb.service.interfaces.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

@DisplayName("Unit-test for Command Container")
class CommandContainerTest {

    private CommandContainer commandContainer;

    @BeforeEach
    public void init() {
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        UserService userService = Mockito.mock(UserService.class);
        QuestionService questionService = Mockito.mock(QuestionService.class);
        QuizService quizService = Mockito.mock(QuizService.class);
        SubscriptionGroupService subscriptionGroupService = Mockito.mock(SubscriptionGroupService.class);

        commandContainer = new CommandContainer(
                sendBotMessageService,
                userService,
                subscriptionGroupService,
                quizService,
                questionService);
    }

    @Test
    public void shouldGetAllCommands() {
        Arrays.stream(CommandName.values())
                .forEach(commandName -> {
                    Command command = commandContainer.retrieveCommand(commandName.getCommandName());
                    Assertions.assertNotEquals(UnknownCommand.class, command.getClass());
                });

    }

    @Test
    public void shouldReturnUnknownCommand() {
        //given
        String message = "/someUnknownCommand";

        //when
        Command command = commandContainer.retrieveCommand(message);

        //then
        Assertions.assertEquals(UnknownCommand.class, command.getClass());
    }
}