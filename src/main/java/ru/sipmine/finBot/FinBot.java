/**
 * This class represents the main bot class that extends the TelegramLongPollingCommandBot class.
 * It is responsible for handling incoming updates and registering bot commands.
 */
package ru.sipmine.finBot;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.SessionFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ru.sipmine.finBot.BotCommands.FinAnalyzeCommands;
import ru.sipmine.finBot.BotCommands.StartCommand;

public class FinBot extends TelegramLongPollingCommandBot {
    private String botName;
    private String botToken;
    private SessionFactory sessionFactory;
    private FinAnalyzeCommands finAnalyzeCommands;
    private final Map<String, String> bindingBy = new ConcurrentHashMap<>();

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
        finAnalyzeCommands = new FinAnalyzeCommands(sessionFactory);
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

    /**
     * Processes non-command updates.
     * 
     * @param update The update to process.
     */
    @Override
    public void processNonCommandUpdate(Update update) {

        if (update.getMessage().getText().startsWith("/fin")) {
            try {
                execute(finAnalyzeCommands.handle(update));
                bindingBy.put(update.getMessage().getChatId().toString(), "");
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (bindingBy.containsKey(update.getMessage().getChatId().toString())) {
            try {
                execute(finAnalyzeCommands.callback(update));
                bindingBy.remove(update.getMessage().getChatId().toString());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }

}
