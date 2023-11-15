package ru.sipmine;



import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import ru.sipmine.data.PsqlConnector;
import ru.sipmine.finBot.FinBot;

/**
 * The Main class is the entry point of the application.
 * It contains the main method which initializes and starts the FinBot Telegram bot.
 */
public class Main {
    private static Properties configData() {
        Properties properties = new Properties();
        try {
            File configFile = new File("src/main/resources/config.yml");
            FileInputStream inputStream = new FileInputStream(configFile);
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }


    public static void main(String[] args) throws SQLException {

        try {

            PsqlConnector psqlConnector = new PsqlConnector();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new FinBot(configData().getProperty("bot_name"),
                    configData().getProperty("bot_token"), psqlConnector.getSessionFactory()));
        } catch (TelegramApiException e) {
            // if (transaction != null) {
            // transaction.rollback(); // Rollback the transaction in case of an error
            // }
            e.printStackTrace();
        }

    }
}
