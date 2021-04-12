package by.github.sendpulse.sptb.bot;

import by.github.sendpulse.sptb.command.CommandContainer;
import by.github.sendpulse.sptb.parser.SendPulseParser;
import by.github.sendpulse.sptb.service.SendBotMessageServiceImpl;
import by.github.sendpulse.sptb.service.interfaces.QuestionService;
import by.github.sendpulse.sptb.service.interfaces.QuizService;
import by.github.sendpulse.sptb.service.interfaces.SubscriptionGroupService;
import by.github.sendpulse.sptb.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.ChatPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

import static by.github.sendpulse.sptb.command.CommandName.NO;

@AllArgsConstructor
@Component
public class SendPulseTelegramBot extends TelegramLongPollingBot {

    private static final Logger log = Logger.getLogger(SendPulseTelegramBot.class);
    public static String COMMAND_PREFIX = "/";

    private final CommandContainer commandContainer;

    @Autowired
    public SendPulseTelegramBot(UserService userService,
                                SubscriptionGroupService subscriptionGroupService,
                                QuizService quizService,
                                QuestionService questionService) throws IOException {
        this.commandContainer = new CommandContainer(
                new SendBotMessageServiceImpl(this),
                userService,
                subscriptionGroupService,
                quizService,
                questionService);
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
        log.log(Level.INFO,"New Update has received");

        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();

                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
            }
        }
        else if (update.hasCallbackQuery()) {
            String message = update.getCallbackQuery().getData();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();
                commandContainer.retrieveCommand(commandIdentifier).executeWithCallBackQuery(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName()).executeWithCallBackQuery(update);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return userName;
    }
}
