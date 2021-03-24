package by.github.sendpulse.sptb.bot;

import by.github.sendpulse.sptb.command.CommandContainer;
import by.github.sendpulse.sptb.service.SendBotMessageServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.logging.Level;
import java.util.logging.Logger;

import static by.github.sendpulse.sptb.command.CommandName.NO;

@AllArgsConstructor
@Component
public class SendPulseTelegramBot extends TelegramLongPollingBot {

    private static final Logger logger = Logger.getLogger(String.valueOf(SendPulseTelegramBot.class));
    public static String COMMAND_PREFIX = "/";

    private final CommandContainer commandContainer;

    public SendPulseTelegramBot() {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this));
    }

    @Getter
    @Setter
    @Value("${bot.username}")
    private String userName;

    @Getter
    @Setter
    @Value("${bot.token}")
    private String token;

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        logger.log(Level.INFO,"New Update receive");

        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();

                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return userName;
    }
}
