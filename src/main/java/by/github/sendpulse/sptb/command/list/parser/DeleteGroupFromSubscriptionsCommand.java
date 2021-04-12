package by.github.sendpulse.sptb.command.list.parser;

import by.github.sendpulse.sptb.command.Command;
import by.github.sendpulse.sptb.entity.SubscriptionGroup;
import by.github.sendpulse.sptb.entity.User;
import by.github.sendpulse.sptb.keyboard.DeleteGroupFromSubscriptionsCommandKeyBoard;
import by.github.sendpulse.sptb.keyboard.HelpCommandKeyboard;
import by.github.sendpulse.sptb.keyboard.Keyboard;
import by.github.sendpulse.sptb.service.interfaces.SendBotMessageService;
import by.github.sendpulse.sptb.service.interfaces.SubscriptionGroupService;
import by.github.sendpulse.sptb.service.interfaces.UserService;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;


import static by.github.sendpulse.sptb.command.CommandName.*;
import static by.github.sendpulse.sptb.command.list.parser.AddSubscriptionGroupCommand.NO_SUCH_SUBSCRIPTION_GROUP_EXCEPTION;

public class DeleteGroupFromSubscriptionsCommand implements Command {

    private static final Logger log = Logger.getLogger(DeleteGroupFromSubscriptionsCommand.class);

    private static final String USER_SUBSCRIPTION_GROUPS_LIST = "Вот список ваших подписок. Выберите из списка, от какой группы вы хотите отписаться.";

    private final UserService userService;
    private final SubscriptionGroupService subscriptionGroupService;
    private final SendBotMessageService sendBotMessageService;
    private final Keyboard helpCommandKeyboard = new HelpCommandKeyboard();

    public DeleteGroupFromSubscriptionsCommand(UserService userService, SubscriptionGroupService subscriptionGroupService, SendBotMessageService sendBotMessageService) {
        this.userService = userService;
        this.subscriptionGroupService = subscriptionGroupService;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        InlineKeyboardMarkup helpCommandInlineKeyboard = helpCommandKeyboard.createKeyBoard();

        String[] receivedMessage = update.getMessage().getText().trim().split(" ");

        if (userService.findById(update.getMessage().getChat().getId()).isPresent()) {
            if (userService.findById(update.getMessage().getChat().getId()).get().getSubscriptions().size() != 0) {
                User currentUser = userService.findById(update.getCallbackQuery().getMessage().getChat().getId()).get();
                Keyboard deleteGroupFromSubscriptionsCommandKeyBoard = new DeleteGroupFromSubscriptionsCommandKeyBoard(currentUser);
                InlineKeyboardMarkup deleteGroupFromSubscriptionsCommandInlineKeyBoard = deleteGroupFromSubscriptionsCommandKeyBoard.createKeyBoard();
                if (currentUser.isActive()) {
                    if (receivedMessage.length == 2) {
                        if (receivedMessage[1].matches("[0-9]+")) {
                            SubscriptionGroup subscriptionGroup;
                            if (subscriptionGroupService.findById(Long.parseLong(receivedMessage[1])).isPresent()) {
                                subscriptionGroup = subscriptionGroupService.findById(Long.parseLong(receivedMessage[1])).get();
                                currentUser = userService.findById(update.getMessage().getChat().getId()).get();
                                subscriptionGroup.removeUser(currentUser);
                                subscriptionGroupService.update(subscriptionGroup);

                                String responseMessage = String.format("Вы отписались от группы <b>%s</b>!\n" +
                                                "Теперь вы не будете получать уведомления, когда в этой группе появятся новые статьи!",
                                        subscriptionGroup.getName());
                                sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), responseMessage, helpCommandInlineKeyboard);
                                log.info("Subscription group has removed to user");
                            }
                            else {
                                sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), NO_SUCH_SUBSCRIPTION_GROUP_EXCEPTION, deleteGroupFromSubscriptionsCommandInlineKeyBoard);
                                log.info("Mistake in number of subscription group");
                            }
                        }
                        else {
                            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), USER_SUBSCRIPTION_GROUPS_LIST, deleteGroupFromSubscriptionsCommandInlineKeyBoard);
                            log.info("Mistake in format of subscription group");
                        }
                    }
                    else {
                        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), USER_SUBSCRIPTION_GROUPS_LIST, deleteGroupFromSubscriptionsCommandInlineKeyBoard);
                        log.info("Mistake in format of subscription group");
                    }
                }
                else {
                    sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), String.format("Сначала запустите бота! %s", START.getCommandName()), helpCommandInlineKeyboard);
                    log.info("User didn't start the bot");
                }
            }
            else {
                sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), "Ваш список подписок пуст! Удалять больше нечего!", helpCommandInlineKeyboard);
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

        String[] receivedMessage = update.getCallbackQuery().getData().trim().split(" ");

        if (userService.findById(update.getCallbackQuery().getMessage().getChat().getId()).isPresent()) {
            if (userService.findById(update.getCallbackQuery().getMessage().getChat().getId()).get().getSubscriptions().size() != 0) {
                User currentUser = userService.findById(update.getCallbackQuery().getMessage().getChat().getId()).get();
                DeleteGroupFromSubscriptionsCommandKeyBoard deleteGroupFromSubscriptionsCommandKeyBoard = new DeleteGroupFromSubscriptionsCommandKeyBoard(currentUser);
                InlineKeyboardMarkup deleteGroupFromSubscriptionsCommandInlineKeyBoard = deleteGroupFromSubscriptionsCommandKeyBoard.createKeyBoard();
                if (currentUser.isActive()) {
                    if (receivedMessage.length == 2) {
                        if (receivedMessage[1].matches("[0-9]+")) {
                            SubscriptionGroup subscriptionGroup;
                            if (subscriptionGroupService.findById(Long.parseLong(receivedMessage[1])).isPresent()) {
                                subscriptionGroup = subscriptionGroupService.findById(Long.parseLong(receivedMessage[1])).get();
                                subscriptionGroup.removeUser(currentUser);
                                subscriptionGroupService.update(subscriptionGroup);

                                String responseMessage = String.format("Вы отписались от группы <b>%s</b>!\n" +
                                                "Теперь вы не будете получать уведомления, когда в этой группе появятся новые статьи!",
                                        subscriptionGroup.getName());
                                sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), responseMessage, helpCommandInlineKeyboard);
                                log.info("Subscription group has removed to user");
                            } else {
                                sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), NO_SUCH_SUBSCRIPTION_GROUP_EXCEPTION, deleteGroupFromSubscriptionsCommandInlineKeyBoard);
                                log.info("Mistake in number of subscription group");
                            }
                        } else {
                            sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), USER_SUBSCRIPTION_GROUPS_LIST, deleteGroupFromSubscriptionsCommandInlineKeyBoard);
                            log.info("Mistake in format of subscription group");
                        }
                    } else {
                        sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), USER_SUBSCRIPTION_GROUPS_LIST, deleteGroupFromSubscriptionsCommandInlineKeyBoard);
                        log.info("Mistake in format of subscription group");
                    }
                } else {
                    sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), String.format("Сначала запустите бота! %s", START.getCommandName()), helpCommandInlineKeyboard);
                    log.info("User didn't start the bot");
                }
            }
            else {
                sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), "Ваш список подписок пуст! Удалять больше нечего!", helpCommandInlineKeyboard);
                log.info("User didn't start the bot");
            }
        }
        else {
            sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), String.format("Сначала запустите бота! %s", START.getCommandName()), helpCommandInlineKeyboard);
            log.info("User didn't start the bot");
        }
    }
}
