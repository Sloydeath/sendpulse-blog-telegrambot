package by.github.sendpulse.sptb.command.list;

import by.github.sendpulse.sptb.SendPulseTelegramBotApplication;
import by.github.sendpulse.sptb.bot.SendPulseTelegramBot;
import by.github.sendpulse.sptb.command.Command;
import by.github.sendpulse.sptb.service.SendBotMessageService;
import by.github.sendpulse.sptb.service.SendBotMessageServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class AbstractCommandTest {

    protected SendPulseTelegramBot sendPulseTelegramBot = Mockito.mock(SendPulseTelegramBot.class);
    protected SendBotMessageService sendBotMessageService = new SendBotMessageServiceImpl(sendPulseTelegramBot);

    abstract Command getCommand();
    abstract String getCommandMessage();
    abstract String getCommandName();

    @Test
    public void shouldProperlyExecuteCommand() throws TelegramApiException {

        //given
        Long chartId = 12345678L;
        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chartId);
        Mockito.when(message.getText()).thenReturn(getCommandName());
        update.setMessage(message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chartId.toString());
        sendMessage.setText(getCommandMessage());
        sendMessage.enableHtml(true);

        //when
        getCommand().execute(update);

        //then
        Mockito.verify(sendPulseTelegramBot).execute(sendMessage);
    }
}
