package ru.sipmine.finBot;



import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;


import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sipmine.data.PsqlConnector;
import ru.sipmine.finBot.BotCommands.StartCommand;


public class FinBot extends TelegramLongPollingCommandBot {
    private String botName;
    private String botToken;
    private static PsqlConnector psqlConnector;
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

    // if (update.hasMessage() && update.getMessage().hasText()) {
    // String message = update.getMessage().getText();
    // System.out.println(message);
    // if (message.equals("/mark")) {
    // System.out.println(1);
    // SendMessage sendMessage = new SendMessage();
    // sendMessage.setChatId(update.getMessage().getChatId().toString());
    // sendMessage.setText("your kb");
    // ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
    // List<KeyboardRow> keyboardRows = new ArrayList<>();

    // KeyboardRow row = new KeyboardRow();

    // keyboardRows.add(row);
    // keyboardMarkup.setKeyboard(keyboardRows);
    // sendMessage.setReplyMarkup(keyboardMarkup);
    // try {
    // execute(sendMessage);
    // } catch (TelegramApiException e) {
    // e.printStackTrace();
    // }

    // }

    // }
    // }
}
