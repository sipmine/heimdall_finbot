package ru.sipmine.finBot.Keyboards;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public abstract class AbstractInlineKeyboard {
    protected InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
    protected List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

    public void addButtonToKeyboard(List<InlineKeyboardButton> buttons) {
        keyboard.add(buttons);
    }
    
    public void keyboardPair() {
        inlineKeyboardMarkup.setKeyboard(keyboard);
    }

}
