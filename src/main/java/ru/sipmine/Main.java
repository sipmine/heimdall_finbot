package ru.sipmine;


import org.hibernate.Session;
import org.hibernate.Transaction;
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
        
        Session session = null;
        Transaction transaction = null;
        try {
            // session = PsqlConnector.getSessionFactory().openSession();
            // transaction = session.beginTransaction(); // Start a new transaction
            // transaction.commit(); // Commit the transaction
            
            
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new FinBot(configData().getProperty("bot_name"), configData().getProperty("bot_token")));
        } catch (TelegramApiException e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback the transaction in case of an error
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close(); // Always close the session
            }
    }


    }
}
