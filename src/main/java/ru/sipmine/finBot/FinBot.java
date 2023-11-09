package ru.sipmine.finBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class FinBot extends TelegramLongPollingBot{
    private String botName;
    private String botToken;

    public FinBot(String botName, String botToken) {
        this.botName = botName;
        this.botToken = botToken;

    }
    
    @Override
    public String getBotUsername() {
        // TODO
        return botName;
    }

    @Override
    public String getBotToken() {
        // TODO
        return botToken;
    }
    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.getMessage().getText());
    }


}
