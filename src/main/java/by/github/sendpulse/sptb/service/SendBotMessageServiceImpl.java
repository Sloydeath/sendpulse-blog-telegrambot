package by.github.sendpulse.sptb.service;

import by.github.sendpulse.sptb.bot.SendPulseTelegramBot;
import by.github.sendpulse.sptb.service.interfaces.SendBotMessageService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class SendBotMessageServiceImpl implements SendBotMessageService {

    private static final Logger log = Logger.getLogger(SendBotMessageServiceImpl.class);
    private final SendPulseTelegramBot sendPulseTelegramBot;

    @Autowired
    public SendBotMessageServiceImpl(SendPulseTelegramBot sendPulseTelegramBot) {
        this.sendPulseTelegramBot = sendPulseTelegramBot;
    }

    @Override
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);
        try {
            sendPulseTelegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.log(Level.ERROR, e.getMessage());
        }
    }

    @Override
    public void sendMessage(String chatId, String message, InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        try {
            sendPulseTelegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.log(Level.ERROR, e.getMessage());
        }
    }
}
