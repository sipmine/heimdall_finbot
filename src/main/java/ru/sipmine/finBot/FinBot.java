/**
 * This class represents the main bot class that extends the TelegramLongPollingCommandBot class.
 * It is responsible for handling incoming updates and registering bot commands.
 */
package ru.sipmine.finBot;

import org.hibernate.SessionFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import ru.sipmine.finBot.BotCommands.FinAnalyze;
import ru.sipmine.finBot.BotCommands.StartCommand;


public class FinBot extends TelegramLongPollingCommandBot {
    private String botName;
    private String botToken;
    private SessionFactory sessionFactory;

    /**
     * Constructor for the FinBot class.
     * @param botName The name of the bot.
     * @param botToken The token of the bot.
     * @param sessionFactory The session factory for the bot.
     */
    public FinBot(String botName, String botToken, SessionFactory sessionFactory) {
        this.botName = botName;
        this.botToken = botToken;
        this.sessionFactory = sessionFactory;
        register(new StartCommand(this.sessionFactory));
        register(new FinAnalyze(this.sessionFactory));
    }

    /**
     * Returns the username of the bot.
     * @return The username of the bot.
     */
    @Override
    public String getBotUsername() {
        return botName;
    }
    
    /**
     * Returns the token of the bot.
     * @return The token of the bot.
     */
    @Override
    public String getBotToken() {
        return botToken;
    }

    /**
     * Processes non-command updates.
     * @param update The update to process.
     */
    @Override
    public void processNonCommandUpdate(Update update) {

    }
}
