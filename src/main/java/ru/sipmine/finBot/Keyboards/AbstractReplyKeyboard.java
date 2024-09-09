package ru.sipmine.finBot.Keyboards;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public abstract class AbstractReplyKeyboard {
    protected ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    protected List<KeyboardRow> keyboard = new ArrayList<>(); 
    public void addButtonToKeyboard(KeyboardRow row) {
        keyboard.add(row);
    }
    public void keyboardPair() {
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }
}
