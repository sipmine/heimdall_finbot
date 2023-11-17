package ru.sipmine.finBot.BotCommands;

import org.hibernate.SessionFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class FinAnalyzeCommands extends AbstractMultiCommand {
     // Userservice is used to create a new user or to find an existing user.
    // private final UserService userService;
    // private final ApiIntegService apiIntegService;
    public FinAnalyzeCommands(SessionFactory sessionFactory) {
        super("fin", "аналитика");
        // this.userService = new UserService(sessionFactory);
        // this.apiIntegService = new ApiIntegService(sessionFactory);
    }
    @Override
    public SendMessage handle( Update update) {
        Message msg = update.getMessage();
        String text = "hello world";
        
        return new SendMessage(msg.getChatId().toString(), text);
    }
    @Override
    public SendMessage callback( Update update) {
        Message msg = update.getMessage();
        String text = "hello world2";
        
        return new SendMessage(msg.getChatId().toString(), text);
    }
    
}
