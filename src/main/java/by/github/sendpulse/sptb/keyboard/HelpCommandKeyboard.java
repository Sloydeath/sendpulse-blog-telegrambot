package by.github.sendpulse.sptb.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static by.github.sendpulse.sptb.command.CommandName.*;
import static by.github.sendpulse.sptb.command.CommandName.MY_SUBS;

public class HelpCommandKeyboard implements Keyboard {
    @Override
    public InlineKeyboardMarkup createKeyBoard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow4 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow5 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow6 = new ArrayList<>();


        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);
        rowList.add(keyboardButtonsRow4);
        rowList.add(keyboardButtonsRow5);
        rowList.add(keyboardButtonsRow6);


        InlineKeyboardButton startButton = new InlineKeyboardButton();
        startButton.setText("Старт");
        startButton.setCallbackData(START.getCommandName());

        InlineKeyboardButton stopButton = new InlineKeyboardButton();
        stopButton.setText("Стоп");
        stopButton.setCallbackData(STOP.getCommandName());

        InlineKeyboardButton helpButton = new InlineKeyboardButton();
        helpButton.setText("Помощь");
        helpButton.setCallbackData(HELP.getCommandName());

        InlineKeyboardButton showStatsButton = new InlineKeyboardButton();
        showStatsButton.setText("Моя статистика");
        showStatsButton.setCallbackData(SHOW_USER_STATISTICS.getCommandName());

        InlineKeyboardButton groupsListButton = new InlineKeyboardButton();
        groupsListButton.setText("Показать список групп");
        groupsListButton.setCallbackData(LIST_GROUP_SUB.getCommandName());

        InlineKeyboardButton addGroupButton = new InlineKeyboardButton();
        addGroupButton.setText("Добавить подписки");
        addGroupButton.setCallbackData(ADD_GROUP_SUB.getCommandName());

        InlineKeyboardButton deleteGroupButton = new InlineKeyboardButton();
        deleteGroupButton.setText("Удалить подписку");
        deleteGroupButton.setCallbackData(DELETE_GROUP_FROM_SUBS.getCommandName());

        InlineKeyboardButton showUsersGroupsListButton = new InlineKeyboardButton();
        showUsersGroupsListButton.setText("Мои подписки");
        showUsersGroupsListButton.setCallbackData(MY_SUBS.getCommandName());

        InlineKeyboardButton quizButton = new InlineKeyboardButton();
        quizButton.setText("Квиз");
        quizButton.setCallbackData(QUIZ.getCommandName());

        keyboardButtonsRow1.addAll(Arrays.asList(showStatsButton, showUsersGroupsListButton));
        keyboardButtonsRow2.add(groupsListButton);
        keyboardButtonsRow3.addAll(Arrays.asList(addGroupButton, deleteGroupButton));
        keyboardButtonsRow4.add(quizButton);
        keyboardButtonsRow6.addAll(Arrays.asList(startButton, stopButton));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
}
