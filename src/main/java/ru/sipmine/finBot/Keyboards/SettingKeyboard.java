package ru.sipmine.finBot.Keyboards;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public class SettingKeyboard  extends AbstractReplyKeyboard{
    public SettingKeyboard() {
        KeyboardButton back = new KeyboardButton("Back");
        KeyboardButton apiAdd = new KeyboardButton("/addApi");
        KeyboardButton apiDel = new KeyboardButton("/delApi");
        
        KeyboardRow row = new KeyboardRow();
        row.add(back);
        row.add(apiAdd);
        row.add(apiDel);
        
        addButtonToKeyboard(row);
        keyboardPair();
    }   
}
