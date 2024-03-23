package ru.sipmine.finBot.Keyboards;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;


public class FinanceKeyboard extends AbstractReplyKeyboard{
    public FinanceKeyboard() {
        KeyboardButton back = new KeyboardButton("Back");
        KeyboardButton getRate = new KeyboardButton("/getRate");
        KeyboardButton getPortfolio = new KeyboardButton("/getPortfolio");
        KeyboardButton getYield = new KeyboardButton("/getYeild");
        KeyboardRow row = new KeyboardRow();

        row.add(back);
        row.add(getRate);
        row.add(getPortfolio);
        row.add(getYield);


        addButtonToKeyboard(row);
        keyboardPair();
    }
}
