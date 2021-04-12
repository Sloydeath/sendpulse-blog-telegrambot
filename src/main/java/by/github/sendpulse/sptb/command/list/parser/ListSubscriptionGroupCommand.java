package by.github.sendpulse.sptb.command.list.parser;

import by.github.sendpulse.sptb.command.Command;
import by.github.sendpulse.sptb.entity.SubscriptionGroup;
import by.github.sendpulse.sptb.keyboard.HelpCommandKeyboard;
import by.github.sendpulse.sptb.keyboard.Keyboard;
import by.github.sendpulse.sptb.service.interfaces.SendBotMessageService;
import by.github.sendpulse.sptb.service.interfaces.SubscriptionGroupService;
import by.github.sendpulse.sptb.service.interfaces.UserService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

import static by.github.sendpulse.sptb.command.CommandName.START;

public class ListSubscriptionGroupCommand implements Command {

    private static final Logger log = Logger.getLogger(ListSubscriptionGroupCommand.class);
    private final SubscriptionGroupService subscriptionGroupService;
    private final SendBotMessageService sendBotMessageService;
    private final UserService userService;
    private final Keyboard helpCommandKeyboard = new HelpCommandKeyboard();

    public ListSubscriptionGroupCommand(SubscriptionGroupService subscriptionGroupService, SendBotMessageService sendBotMessageService, UserService userService) {
        this.subscriptionGroupService = subscriptionGroupService;
        this.sendBotMessageService = sendBotMessageService;
        this.userService = userService;
    }

    @Override
    public void execute(Update update) {
        InlineKeyboardMarkup helpCommandInlineKeyboard = helpCommandKeyboard.createKeyBoard();

        if (userService.findById(update.getMessage().getChat().getId()).isPresent()) {
            if (userService.findById(update.getMessage().getChat().getId()).get().isActive()) {
                if (subscriptionGroupService.findAll() != null) {
                    StringBuilder showSubsListMessage = new StringBuilder();
                    List<SubscriptionGroup> subscriptionGroupList = subscriptionGroupService.findAll();
                    for (SubscriptionGroup subscriptionGroup : subscriptionGroupList) {
                        showSubsListMessage.append(String.format("%d. %s\n", subscriptionGroup.getId(), subscriptionGroup.getName()));
                    }
                    sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), showSubsListMessage.toString(), helpCommandInlineKeyboard);
                    log.info("Subscription groups' list has sent");
                } else {
                    sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), "Возникла ошибка! " +
                            "Пожалуйста, напиши в тех. поддержку!", helpCommandInlineKeyboard);
                    log.log(Level.ERROR, "Subscription groups' list is empty!");
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

        if (userService.findById(update.getCallbackQuery().getMessage().getChat().getId()).isPresent()) {
            if (userService.findById(update.getCallbackQuery().getMessage().getChat().getId()).get().isActive()) {
                if (subscriptionGroupService.findAll() != null) {
                    StringBuilder showSubsListMessage = new StringBuilder();
                    List<SubscriptionGroup> subscriptionGroupList = subscriptionGroupService.findAll();
                    for (SubscriptionGroup subscriptionGroup : subscriptionGroupList) {
                        showSubsListMessage.append(String.format("%d. %s\n", subscriptionGroup.getId(), subscriptionGroup.getName()));
                    }
                    sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), showSubsListMessage.toString(), helpCommandInlineKeyboard);
                    log.info("Subscription groups' list has sent");
                } else {
                    sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), "Возникла ошибка! " +
                            "Пожалуйста, напиши в тех. поддержку!", helpCommandInlineKeyboard);
                    log.log(Level.ERROR, "Subscription groups' list is empty!");
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
