package by.github.sendpulse.sptb.service;

import by.github.sendpulse.sptb.bot.SendPulseTelegramBot;
import by.github.sendpulse.sptb.service.interfaces.SendBotMessageService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class SendBotMessageServiceImpl implements SendBotMessageService {

    private static final Logger logger = Logger.getLogger(SendBotMessageServiceImpl.class);
    private final SendPulseTelegramBot sendPulseTelegramBot;

    @Autowired
    public SendBotMessageServiceImpl(SendPulseTelegramBot sendPulseTelegramBot) {
        this.sendPulseTelegramBot = sendPulseTelegramBot;
    }


    @Override
    public void sendMessage(String charId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(charId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        try {
            sendPulseTelegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            logger.log(Level.ERROR, e.getMessage());
        }
    }
}
