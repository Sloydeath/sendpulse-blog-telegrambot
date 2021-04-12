package by.github.sendpulse.sptb.keyboard;

import by.github.sendpulse.sptb.entity.SubscriptionGroup;
import by.github.sendpulse.sptb.entity.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static by.github.sendpulse.sptb.command.CommandName.DELETE_GROUP_FROM_SUBS;

public class DeleteGroupFromSubscriptionsCommandKeyBoard implements Keyboard {

    private final User user;

    public DeleteGroupFromSubscriptionsCommandKeyBoard(User user) {
        this.user = user;
    }


    @Override
    public InlineKeyboardMarkup createKeyBoard() {
        Set<SubscriptionGroup> subscriptionGroupList = user.getSubscriptions();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (SubscriptionGroup subscriptionGroup: subscriptionGroupList) {
            InlineKeyboardButton subscriptionGroupButton = new InlineKeyboardButton();
            subscriptionGroupButton.setText(String.format("%s", subscriptionGroup.getName()));
            subscriptionGroupButton.setCallbackData(String.format("%s %d", DELETE_GROUP_FROM_SUBS.getCommandName(), subscriptionGroup.getId()));
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
            keyboardButtonsRow.add(subscriptionGroupButton);
            rowList.add(keyboardButtonsRow);
        }
        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
}
