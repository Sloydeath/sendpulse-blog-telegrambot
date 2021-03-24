package by.github.sendpulse.sptb.command.list;

import by.github.sendpulse.sptb.command.Command;
import by.github.sendpulse.sptb.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public final static String START_MESSAGE = "Привет! Я SendPulse Telegram Bot. Я помогу тебе погрузиться в мир " +
            "email-маркетинга и не только!\n\n " +
            "С моей помощью ты сможешь получать уведомления об обновлениях " +
            "статей в блоге SendPulse из разделов, на которые ты подпишешься.\n\n" +
            "Ещё вместе со мной ты сможешь поиграть в интеллектуальную игру - <b>SendPulse Quiz</b>\n" +
            "Ты сможешь проверить свои знания и посоревноваться с друзьями.";

    public StartCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), START_MESSAGE);
    }
}
