package by.github.sendpulse.sptb.command.list.parser;

import by.github.sendpulse.sptb.command.Command;
import by.github.sendpulse.sptb.entity.SubscriptionGroup;
import by.github.sendpulse.sptb.entity.User;
import by.github.sendpulse.sptb.keyboard.HelpCommandKeyboard;
import by.github.sendpulse.sptb.keyboard.Keyboard;
import by.github.sendpulse.sptb.service.interfaces.SendBotMessageService;
import by.github.sendpulse.sptb.service.interfaces.UserService;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Set;

import static by.github.sendpulse.sptb.command.CommandName.HELP;
import static by.github.sendpulse.sptb.command.CommandName.START;

public class ShowUserSubscriptionsCommand implements Command {

    private static final Logger log = Logger.getLogger(ShowUserSubscriptionsCommand.class);

    public static final String EMPTY_SUBS_LIST_MESSAGE = String.format("У Вас пока что нет подписок!\n" +
            "Если хотите добавить новую подписку, то найдите команду для добавления здесь: <b>%s</b>", HELP.getCommandName());

    private final UserService userService;
    private final SendBotMessageService sendBotMessageService;
    private final Keyboard helpCommandKeyboard = new HelpCommandKeyboard();

    public ShowUserSubscriptionsCommand(UserService userService, SendBotMessageService sendBotMessageService) {
        this.userService = userService;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        InlineKeyboardMarkup helpCommandInlineKeyboard = helpCommandKeyboard.createKeyBoard();

        User currentUser;
        if (userService.findById(update.getMessage().getChat().getId()).isPresent()) {
            if (userService.findById(update.getMessage().getChat().getId()).get().isActive()) {
                currentUser = userService.findById(update.getMessage().getChat().getId()).get();
                Set<SubscriptionGroup> userSubs = currentUser.getSubscriptions();
                if (userSubs.size() > 0) {
                    StringBuilder userSubsListMessage = new StringBuilder("Список Ваших подписок:\n");
                    for (SubscriptionGroup subscriptionGroup : userSubs) {
                        userSubsListMessage.append(String.format(" - %s\n", subscriptionGroup.getName()));
                    }
                    sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), userSubsListMessage.toString(), helpCommandInlineKeyboard);
                } else {
                    sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), EMPTY_SUBS_LIST_MESSAGE, helpCommandInlineKeyboard);
                }
            }
            else {
                sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), String.format("Сначала запустите бота! %s", START.getCommandName()), helpCommandInlineKeyboard);
                log.info("User didn't start the bot");
            }
        }
        else {
            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), String.format("Сначала запустите бота! %s", START.getCommandName()), helpCommandInlineKeyboard);
            log.info("User didn't start the bot");
        }
    }

    @Override
    public void executeWithCallBackQuery(Update update) {
        InlineKeyboardMarkup helpCommandInlineKeyboard = helpCommandKeyboard.createKeyBoard();

        User currentUser;
        if (userService.findById(update.getCallbackQuery().getMessage().getChat().getId()).isPresent()) {
            if (userService.findById(update.getCallbackQuery().getMessage().getChat().getId()).get().isActive()) {
                currentUser = userService.findById(update.getCallbackQuery().getMessage().getChat().getId()).get();
                Set<SubscriptionGroup> userSubs = currentUser.getSubscriptions();
                if (userSubs.size() > 0) {
                    StringBuilder userSubsListMessage = new StringBuilder("Список Ваших подписок:\n");
                    for (SubscriptionGroup subscriptionGroup : userSubs) {
                        userSubsListMessage.append(String.format(" - %s\n", subscriptionGroup.getName()));
                    }
                    sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), userSubsListMessage.toString(), helpCommandInlineKeyboard);
                } else {
                    sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), EMPTY_SUBS_LIST_MESSAGE, helpCommandInlineKeyboard);
                }
            }
            else {
                sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), String.format("Сначала запустите бота! %s", START.getCommandName()), helpCommandInlineKeyboard);
                log.info("User didn't start the bot");
            }
        }
        else {
            sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), String.format("Сначала запустите бота! %s", START.getCommandName()), helpCommandInlineKeyboard);
            log.info("User didn't start the bot");
        }
    }
}
