package ru.sipmine.finBot.Keyboards;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public class MenuKeyboard extends AbstractReplyKeyboard {


    public MenuKeyboard() {

        KeyboardButton setting = new KeyboardButton("Setting");
        KeyboardButton finace = new KeyboardButton("Finance");
        KeyboardButton crypto = new KeyboardButton("Crypto");
        
        KeyboardRow row = new KeyboardRow();
        row.add(setting);
        row.add(finace);
        row.add(crypto);
        
        addButtonToKeyboard(row);
        keyboardPair();
    }

    
}
