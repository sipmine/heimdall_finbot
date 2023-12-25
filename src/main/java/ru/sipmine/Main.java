package ru.sipmine;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Properties;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.google.gson.JsonSyntaxException;

import ru.sipmine.data.PsqlConnector;
import ru.sipmine.finBot.FinBot;
import ru.sipmine.finUtils.byBitApi.AccountService;
import ru.sipmine.finUtils.byBitApi.MarketService;
import ru.sipmine.finUtils.byBitApi.SessionGenerator;
import ru.sipmine.finUtils.byBitApi.AccountTypes.WalletType;
import ru.sipmine.finUtils.byBitApi.MarkeTypes.FundingRateHistoryType;
import ru.sipmine.finUtils.byBitApi.MarkeTypes.RiskLimitType;
import ru.sipmine.finUtils.byBitApi.MarkeTypes.TickersType;
import ru.tinkoff.piapi.contract.v1.Account;

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


    public static void main(String[] args) throws SQLException, InvalidKeyException, JsonSyntaxException, NoSuchAlgorithmException, IOException {

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
