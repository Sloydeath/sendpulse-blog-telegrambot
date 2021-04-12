package by.github.sendpulse.sptb.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static by.github.sendpulse.sptb.command.CommandName.START_QUIZ;
import static by.github.sendpulse.sptb.command.CommandName.STOP_QUIZ;

public class StartQuizCommandKeyboard implements Keyboard{
    @Override
    public InlineKeyboardMarkup createKeyBoard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        InlineKeyboardButton startQuizButton = new InlineKeyboardButton();
        startQuizButton.setText("Начать квиз!");
        startQuizButton.setCallbackData(String.format("%s", START_QUIZ.getCommandName()));

        InlineKeyboardButton stopQuizButton = new InlineKeyboardButton();
        stopQuizButton.setText("Остановить квиз");
        stopQuizButton.setCallbackData(String.format("%s", STOP_QUIZ.getCommandName()));

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(startQuizButton);
        keyboardButtonsRow.add(stopQuizButton);

        rowList.add(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
}
