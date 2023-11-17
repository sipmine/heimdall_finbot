package ru.sipmine.finBot.BotCommands;

import org.hibernate.SessionFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ru.sipmine.data.Services.ApiIntegService;
import ru.sipmine.data.Services.UserService;
import ru.sipmine.data.tables.UsersTable;

public class FinAnalyzeCommands extends AbstractBotCommand {
     // Userservice is used to create a new user or to find an existing user.
    // private final UserService userService;
    // private final ApiIntegService apiIntegService;
    private Update update;
    public FinAnalyzeCommands(SessionFactory sessionFactory, Update update) {
        super("fin", "аналитика");
        this.update = update;
        // this.userService = new UserService(sessionFactory);
        // this.apiIntegService = new ApiIntegService(sessionFactory);
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        System.out.println(22);
        if (update.getMessage().getText().equals(getCommandIdentifier())) {
            System.out.println(1);
        } else {
            System.out.println(update.getMessage().getText());
        }

        
    }   
}
