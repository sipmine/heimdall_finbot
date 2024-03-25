/**
 * This class represents the main bot class that extends the TelegramLongPollingCommandBot class.
 * It is responsible for handling incoming updates and registering bot commands.
 */
package ru.sipmine.finBot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.SessionFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ru.sipmine.finBot.Keyboards.RoutesKeyboard;
import ru.sipmine.finBot.botCommands.AbstractMultiCommand;
import ru.sipmine.finBot.botCommands.ApiAddCommands;
import ru.sipmine.finBot.botCommands.DeleteTokenCommand;
import ru.sipmine.finBot.botCommands.GetCryptoPortfolioCommand;
import ru.sipmine.finBot.botCommands.GetPortfolioCommand;
import ru.sipmine.finBot.botCommands.GetRateCurrencyCommand;
import ru.sipmine.finBot.botCommands.GetYieldCommand;
import ru.sipmine.finBot.botCommands.StartCommand;

public class FinBot extends TelegramLongPollingCommandBot {
    private String botName;
    private String botToken;
    private String callbackkb;
    
    private SessionFactory sessionFactory;
    private final Map<String, String> bindingBy = new ConcurrentHashMap<>();
    private final Map<String, AbstractMultiCommand> commandList = new ConcurrentHashMap<>();
    private static final List<String> directors = new ArrayList<>();
    @SuppressWarnings("deprecation")
    public FinBot(String botName, String botToken, SessionFactory sessionFactory) {
        this.botName = botName;
        this.botToken = botToken;
        this.sessionFactory = sessionFactory;
        RoutesKeyboard.choiceKeyboard();
        directors.add("Setting");
        directors.add("Finance");
        directors.add("Crypto");
        directors.add("Back");
        register(new StartCommand(this.sessionFactory));
        register(new GetCryptoPortfolioCommand(this.sessionFactory));
        commandList.put("/apiAdd", new ApiAddCommands(sessionFactory));
        commandList.put("/getPortfolio", new GetPortfolioCommand(sessionFactory));
        commandList.put("/getYield", new GetYieldCommand(sessionFactory));
        commandList.put("/apiDel", new DeleteTokenCommand(sessionFactory));
        commandList.put("/getRate", new GetRateCurrencyCommand(sessionFactory));
        
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
            e.printStackTrace();
        }
    }

    public void delMsg(DeleteMessage deleteMessage) {
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
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
            if (directors.contains(update.getMessage().getText())) {
                RoutesKeyboard.setKeyboardName(update.getMessage().getText());
                RoutesKeyboard.choiceKeyboard();                
                SendMessage updKeyboard = new SendMessage();
                updKeyboard.setChatId(chatID);
                updKeyboard.setText("Вы перешли в категорию " + update.getMessage().getText());
                updKeyboard.setReplyMarkup(RoutesKeyboard.getKeyboardMarkup());
                pubMsg(updKeyboard);      
            }

        } else if (update.hasCallbackQuery()) {
            callbackkb = update.getCallbackQuery().getData().toString();

            String commandPrefix = null;

            if (callbackkb.startsWith("gpc")) {
                commandPrefix = "gpc";
            } else if (callbackkb.startsWith("gyc")) {
                commandPrefix = "gyc";
            } else if (callbackkb.startsWith("gdc")) {
                commandPrefix = "gdc";
            } else if (callbackkb.startsWith("grc")) {
                commandPrefix = "grc";
            }

            if (commandPrefix != null) {
                String commandSuffix = callbackkb.split(commandPrefix)[1];

                switch (commandPrefix) {
                    case "gpc":
                    case "gyc":
                    case "gdc":
                    case "grc":
                        pubMsg(commandList
                                .get(bindingBy.get(update.getCallbackQuery().getMessage().getChatId().toString()))
                                .callback(update, commandSuffix));
                        bindingBy.remove(update.getCallbackQuery().getMessage().getChatId().toString());
                        callbackkb = null;
                        break;
                    default:
                        // Handle unexpected commandPrefix
                        break;
                }
            }

            if (callbackkb.equals("tininv") || callbackkb.equals("bybit")) {

                SendMessage sm = new SendMessage(update.getCallbackQuery().getMessage().getChatId().toString(),
                        "введи токен");
                pubMsg(sm);
            }

        }
    }

}
