package by.github.sendpulse.sptb.command;

import by.github.sendpulse.sptb.command.list.UnknownCommand;
import by.github.sendpulse.sptb.service.SendBotMessageService;
import by.github.sendpulse.sptb.service.SendBotMessageServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit-test for Command Container")
class CommandContainerTest {

    private CommandContainer commandContainer;

    @BeforeEach
    public void init() {
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        commandContainer = new CommandContainer(sendBotMessageService);
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