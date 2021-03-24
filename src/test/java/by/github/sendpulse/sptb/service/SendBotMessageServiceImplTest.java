package by.github.sendpulse.sptb.service;

import by.github.sendpulse.sptb.bot.SendPulseTelegramBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit-level testing for SendBotMessageService")
class SendBotMessageServiceImplTest {

    private SendBotMessageService sendBotMessageService;
    private SendPulseTelegramBot sendPulseTelegramBot;

    @BeforeEach
    public void init() {
        sendPulseTelegramBot = Mockito.mock(SendPulseTelegramBot.class);
        sendBotMessageService = new SendBotMessageServiceImpl(sendPulseTelegramBot);
    }

    @Test
    void shouldCorrectlySendMessage() throws TelegramApiException {
        //given
        String chatId = "test_chat_id";
        String message = "test_message";

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.enableHtml(true);

        //when
        sendBotMessageService.sendMessage(chatId, message);

        //then
        Mockito.verify(sendPulseTelegramBot).execute(sendMessage);
    }
}