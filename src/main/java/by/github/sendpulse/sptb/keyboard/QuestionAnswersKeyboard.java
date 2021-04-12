package by.github.sendpulse.sptb.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static by.github.sendpulse.sptb.command.CommandName.NEXT_QUESTION;

public class QuestionAnswersKeyboard implements Keyboard {

    private final Map<Integer,String> questionAnswers;
    private final long id;

    public QuestionAnswersKeyboard(Map<Integer, String> questionAnswers, long id) {
        this.questionAnswers = questionAnswers;
        this.id = id;
    }

    @Override
    public InlineKeyboardMarkup createKeyBoard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            if (i < 2) {
                InlineKeyboardButton answerButton = new InlineKeyboardButton();
                answerButton.setText(String.format("%d", i + 1));
                answerButton.setCallbackData(String.format("%s .%s.%d", NEXT_QUESTION.getCommandName(), questionAnswers.get(i).hashCode(), id));
                keyboardButtonsRow1.add(answerButton);
            }
            else {
                InlineKeyboardButton answerButton = new InlineKeyboardButton();
                answerButton.setText(String.format("%d", i + 1));
                answerButton.setCallbackData(String.format("%s .%s.%d", NEXT_QUESTION.getCommandName(), questionAnswers.get(i).hashCode(), id));
                keyboardButtonsRow2.add(answerButton);
            }
        }
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}
