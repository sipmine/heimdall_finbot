package ru.sipmine.finBot.Keyboards;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public class CryptoKeyboard extends AbstractReplyKeyboard{
    public CryptoKeyboard() {
        KeyboardButton back = new KeyboardButton("Back");
        KeyboardButton crypoPortfolio = new KeyboardButton("/getCryptoPortfolio");


        KeyboardRow row = new KeyboardRow();

        row.add(back);
        row.add(crypoPortfolio);
        addButtonToKeyboard(row);
        keyboardPair();
    }
}
