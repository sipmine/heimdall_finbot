/**
 * This class represents the main bot class that extends the TelegramLongPollingCommandBot class.
 * It is responsible for handling incoming updates and registering bot commands.
 */
package ru.sipmine.finBot;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.SessionFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ru.sipmine.finBot.botCommands.AbstractMultiCommand;
import ru.sipmine.finBot.botCommands.ApiAddCommands;
import ru.sipmine.finBot.botCommands.PortfolioCommand;
import ru.sipmine.finBot.botCommands.StartCommand;

import java.util.ArrayList;
import java.util.List;

public class FinBot extends TelegramLongPollingCommandBot {
    private String botName;
    private String botToken;
    private String callbackkb;
    private SessionFactory sessionFactory;
    private final Map<String, String> bindingBy = new ConcurrentHashMap<>();
    private final Map<String, AbstractMultiCommand> commandList = new ConcurrentHashMap<>();

    /**
     * Constructor for the FinBot class.
     * 
     * @param botName        The name of the bot.
     * @param botToken       The token of the bot.
     * @param sessionFactory The session factory for the bot.
     */
    public FinBot(String botName, String botToken, SessionFactory sessionFactory) {
        this.botName = botName;
        this.botToken = botToken;
        this.sessionFactory = sessionFactory;
        register(new StartCommand(this.sessionFactory));
        register(new PortfolioCommand(this.sessionFactory));
        commandList.put("/apiadd", new ApiAddCommands(sessionFactory));

    }

    /**
     * Returns the username of the bot.
     * 
     * @return The username of the bot.
     */
    @Override
    public String getBotUsername() {
        return botName;
    }

    /**
     * Returns the token of the bot.
     * 
     * @return The token of the bot.
     */
    @Override
    public String getBotToken() {
        return botToken;
    }

    public void pubMsg(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Processes non-command updates.
     * 
     * @param update The update to process.
     */
    @Override
    public void processNonCommandUpdate(Update update) {
        
        if (update.hasMessage() && update.getMessage().hasText()) {
            String key = update.getMessage().getText();
            String chatID = update.getMessage().getChatId().toString();    
            if (commandList.containsKey(key)) {
                pubMsg(commandList.get(key).handle(update));
                bindingBy.put(chatID, key);
            } else if (bindingBy.containsKey(update.getMessage().getChatId().toString())) {
                pubMsg(commandList.get(bindingBy.get(chatID)).callback(update, callbackkb));
                bindingBy.remove(chatID);
                callbackkb = null;

            }
        } else if (update.hasCallbackQuery()) {
            callbackkb = update.getCallbackQuery().getData().toString();
            System.out.println(callbackkb);
            if (callbackkb.equals("tininv")) {
                System.out.println(1);
                SendMessage sm = new SendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), "введи токен от тинькофф инвестиций"  );
                pubMsg(sm);
            }   
        }
    }

}
