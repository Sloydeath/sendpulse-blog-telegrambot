package by.github.sendpulse.sptb.keyboard;

import by.github.sendpulse.sptb.entity.SubscriptionGroup;
import by.github.sendpulse.sptb.service.interfaces.SubscriptionGroupService;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static by.github.sendpulse.sptb.command.CommandName.ADD_GROUP_SUB;

public class AddSubscriptionGroupCommandKeyboard implements Keyboard{

    private final SubscriptionGroupService subscriptionGroupService;

    public AddSubscriptionGroupCommandKeyboard(SubscriptionGroupService subscriptionGroupService) {
        this.subscriptionGroupService = subscriptionGroupService;
    }

    @Override
    public InlineKeyboardMarkup createKeyBoard() {
        List<SubscriptionGroup> subscriptionGroupList = subscriptionGroupService.findAll();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (SubscriptionGroup subscriptionGroup: subscriptionGroupList) {
            InlineKeyboardButton subscriptionGroupButton = new InlineKeyboardButton();
            subscriptionGroupButton.setText(String.format("%d. %s",subscriptionGroup.getId(), subscriptionGroup.getName()));
            subscriptionGroupButton.setCallbackData(String.format("%s %d", ADD_GROUP_SUB.getCommandName(), subscriptionGroup.getId()));
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
            keyboardButtonsRow.add(subscriptionGroupButton);
            rowList.add(keyboardButtonsRow);
        }
        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
}
