package ru.sipmine.finBot;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.google.protobuf.Extension.MessageType;

import ru.sipmine.finBot.BotCommands.StartCommand;


public class FinBot extends TelegramLongPollingCommandBot{
    private String botName;
    private String botToken;

    public FinBot(String botName, String botToken) {
        this.botName = botName;
        this.botToken = botToken;
        register(new StartCommand());

        
    }
    
    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        
        return botToken;
    }



    @Override
    public void processNonCommandUpdate(Update update) {
        
    }



    // @Override
    // public void onUpdateReceived(Update update) {
       
    //     if (update.hasMessage() && update.getMessage().hasText()) {
    //         String message = update.getMessage().getText();
    //         System.out.println(message);
    //         if (message.equals("/mark")) {
    //         System.out.println(1);
    //         SendMessage sendMessage = new SendMessage();
    //         sendMessage.setChatId(update.getMessage().getChatId().toString());
    //         sendMessage.setText("your kb");
    //         ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
    //         List<KeyboardRow> keyboardRows = new ArrayList<>();

    //         KeyboardRow row = new KeyboardRow();
 
    //         keyboardRows.add(row);
    //         keyboardMarkup.setKeyboard(keyboardRows);
    //         sendMessage.setReplyMarkup(keyboardMarkup);
    //         try {
    //             execute(sendMessage);    
    //         } catch (TelegramApiException e) {
    //             e.printStackTrace();
    //         }
            
    //         }

    //     }
    // }
} 