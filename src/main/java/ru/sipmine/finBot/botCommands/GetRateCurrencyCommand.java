package ru.sipmine.finBot.botCommands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class GetRateCurrencyCommand extends AbstractMultiCommand {

    public GetRateCurrencyCommand(SessionFactory sessionFactory) {
        super("getRate", "Возвращает курс введёной валюты");
    }

    @Override
    public SendMessage handle(Update update) {
        var msg = update.getMessage();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Введите или выберите из предложенных валютных пар\n Пример ввода: RUB USD");
        sendMessage.setChatId(msg.getChatId());
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<InlineKeyboardButton>();
        // USDRUB
        InlineKeyboardButton USDRUB = new InlineKeyboardButton("USD RUB");
        USDRUB.setCallbackData("grcUSD RUB");
        // EURRUB
        InlineKeyboardButton EURRUB = new InlineKeyboardButton("EUR RUB");
        EURRUB.setCallbackData("grcEUR RUB");
        // USDEUR
        InlineKeyboardButton USDEUR = new InlineKeyboardButton("USD EUR");
        USDEUR.setCallbackData("grcUSD EUR");
        buttons.add(USDRUB);
        buttons.add(EURRUB);
        buttons.add(USDEUR);
        keyboard.add(buttons);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    @Override
    public SendMessage callback(Update update, String arg) {
        String date;
        SendMessage sendMessage = new SendMessage();
        Long chatId = null;
        if (!update.hasCallbackQuery()) {
            date = update.getMessage().getText().toString();
            chatId = update.getMessage().getChatId();
        } else {
            date = arg;
            chatId = update.getCallbackQuery().getMessage().getChatId();
        } 

        sendMessage.setChatId(chatId);
        String[] dates = date.split(" ");
        String url = "https://www.x-rates.com/calculator/?from="+dates[0] + "&to=" + dates[1] + "&amount=1";
        
        try {
            Document doc = Jsoup.connect(url).get();
            String rate =  doc.getElementsByClass("ccOutputRslt").text().toString();
            
            sendMessage.setText(rate);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return sendMessage;
    }

}
