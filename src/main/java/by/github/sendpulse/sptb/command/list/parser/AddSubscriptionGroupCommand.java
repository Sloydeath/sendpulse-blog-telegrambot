package by.github.sendpulse.sptb.command.list.parser;

import by.github.sendpulse.sptb.command.Command;
import by.github.sendpulse.sptb.entity.SubscriptionGroup;
import by.github.sendpulse.sptb.entity.User;
import by.github.sendpulse.sptb.keyboard.AddSubscriptionGroupCommandKeyboard;
import by.github.sendpulse.sptb.keyboard.HelpCommandKeyboard;
import by.github.sendpulse.sptb.keyboard.Keyboard;
import by.github.sendpulse.sptb.service.interfaces.SendBotMessageService;
import by.github.sendpulse.sptb.service.interfaces.SubscriptionGroupService;
import by.github.sendpulse.sptb.service.interfaces.UserService;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static by.github.sendpulse.sptb.command.CommandName.*;

public class AddSubscriptionGroupCommand implements Command {

    private static final Logger log = Logger.getLogger(AddSubscriptionGroupCommand.class);
    private static final String SUBSCRIPTION_GROUPS_LIST = "Нажмите на группу, которую хотите добавить в подписки:";

    public static final String NO_SUCH_SUBSCRIPTION_GROUP_EXCEPTION = "Вы ввели неверный номер группы. " +
            "Попробуйте снова!";

    private final SendBotMessageService sendBotMessageService;
    private final SubscriptionGroupService subscriptionGroupService;
    private final UserService userService;
    private final Keyboard helpCommandKeyboard = new HelpCommandKeyboard();
    private final Keyboard addSubscriptionGroupCommandKeyboard;

    public AddSubscriptionGroupCommand(SendBotMessageService sendBotMessageService, SubscriptionGroupService subscriptionGroupService, UserService userService) {
        this.sendBotMessageService = sendBotMessageService;
        this.subscriptionGroupService = subscriptionGroupService;
        this.userService = userService;

        addSubscriptionGroupCommandKeyboard = new AddSubscriptionGroupCommandKeyboard(subscriptionGroupService);
    }

    @Override
    public void execute(Update update) {
        InlineKeyboardMarkup helpCommandInlineKeyboard = helpCommandKeyboard.createKeyBoard();
        InlineKeyboardMarkup addSubscriptionGroupCommandInlineKeyboard = addSubscriptionGroupCommandKeyboard.createKeyBoard();

        String[] receivedMessage = update.getMessage().getText().trim().split(" ");

        if (userService.findById(update.getMessage().getChat().getId()).isPresent()) {
            if (userService.findById(update.getMessage().getChat().getId()).get().isActive()) {
                if (receivedMessage.length == 2) {
                    if (receivedMessage[1].matches("[0-9]+")) {
                        SubscriptionGroup subscriptionGroup;
                        User currentUser;
                        if (subscriptionGroupService.findById(Long.parseLong(receivedMessage[1])).isPresent()) {
                            subscriptionGroup = subscriptionGroupService.findById(Long.parseLong(receivedMessage[1])).get();
                            currentUser = userService.findById(update.getMessage().getChat().getId()).get();
                            subscriptionGroup.addUser(currentUser);
                            subscriptionGroupService.update(subscriptionGroup);

                            String responseMessage = String.format("Вы подписались на группу <b>%s</b>!\n" +
                                            "Теперь вы сможете получать уведомления, когда в этой группе появятся новые статьи!",
                                    subscriptionGroup.getName());
                            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), responseMessage, helpCommandInlineKeyboard);
                            log.info("Subscription group has added to user");
                        }
                        else {
                            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), NO_SUCH_SUBSCRIPTION_GROUP_EXCEPTION, addSubscriptionGroupCommandInlineKeyboard);
                            log.info("Mistake in number of subscription group");
                        }
                    }
                    else {
                        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), SUBSCRIPTION_GROUPS_LIST, addSubscriptionGroupCommandInlineKeyboard);
                        log.info("Mistake in format of subscription group");
                    }
                }
                else {
                    sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), SUBSCRIPTION_GROUPS_LIST, addSubscriptionGroupCommandInlineKeyboard);
                    log.info("Mistake in format of subscription group");
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
        InlineKeyboardMarkup addSubscriptionGroupCommandInlineKeyboard = addSubscriptionGroupCommandKeyboard.createKeyBoard();

        String[] receivedMessage = update.getCallbackQuery().getData().trim().split(" ");

        if (userService.findById(update.getCallbackQuery().getMessage().getChat().getId()).isPresent()) {
            if (userService.findById(update.getCallbackQuery().getMessage().getChat().getId()).get().isActive()) {
                if (receivedMessage.length == 2) {
                    if (receivedMessage[1].matches("[0-9]+")) {
                        SubscriptionGroup subscriptionGroup;
                        User currentUser;
                        if (subscriptionGroupService.findById(Long.parseLong(receivedMessage[1])).isPresent()) {
                            subscriptionGroup = subscriptionGroupService.findById(Long.parseLong(receivedMessage[1])).get();
                            currentUser = userService.findById(update.getCallbackQuery().getMessage().getChat().getId()).get();
                            subscriptionGroup.addUser(currentUser);
                            subscriptionGroupService.update(subscriptionGroup);

                            String responseMessage = String.format("Вы подписались на группу <b>%s</b>!\n" +
                                            "Теперь вы сможете получать уведомления, когда в этой группе появятся новые статьи!",
                                    subscriptionGroup.getName());
                            sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), responseMessage, helpCommandInlineKeyboard);
                            log.info("Subscription group has added to user");
                        }
                        else {
                            sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), NO_SUCH_SUBSCRIPTION_GROUP_EXCEPTION, addSubscriptionGroupCommandInlineKeyboard);
                            log.info("Mistake in number of subscription group");
                        }
                    }
                    else {
                        sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), SUBSCRIPTION_GROUPS_LIST, addSubscriptionGroupCommandInlineKeyboard);
                        log.info("Mistake in format of subscription group");
                    }
                }
                else {
                    sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), SUBSCRIPTION_GROUPS_LIST, addSubscriptionGroupCommandInlineKeyboard);
                    log.info("Mistake in format of subscription group");
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
