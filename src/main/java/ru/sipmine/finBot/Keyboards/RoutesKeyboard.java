package ru.sipmine.finBot.Keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class RoutesKeyboard {
    private static ReplyKeyboardMarkup replyKeyboardMarkup;
    private static String keyboardName = "Back";

    public static void setKeyboardName(String keyboardName){
        RoutesKeyboard.keyboardName = keyboardName;

    }

    public static void choiceKeyboard() {
        switch (keyboardName) {
            case "Setting":
                setKeyboard(new SettingKeyboard());
                break;
             case "Finance":
                setKeyboard(new FinanceKeyboard());
                break;
            case "Crypto":
                setKeyboard(new CryptoKeyboard());
                break;
            default:
                setKeyboard(new MenuKeyboard());
                break;
        }
    }

    private static void setKeyboard(AbstractReplyKeyboard keyboard) {
        replyKeyboardMarkup = keyboard.replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup getKeyboardMarkup() {
        return replyKeyboardMarkup;
    }

}
