package ru.sipmine.finBot.BotCommands;

import org.hibernate.SessionFactory;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import ru.sipmine.data.Services.ApiIntegService;
import ru.sipmine.data.Services.UserService;

public class FinAnalyze extends AbstractBotCommand {
     // Userservice is used to create a new user or to find an existing user.
    private final UserService userService;
    private final ApiIntegService apiIntegService;
    public FinAnalyze(SessionFactory sessionFactory) {
        super("fin", "аналитика");
        this.userService = new UserService(sessionFactory);
        this.apiIntegService = new ApiIntegService(sessionFactory);
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        User user = message.getFrom();
        int id = userService.findIdByTelegramUserName(user.getUserName());
        apiIntegService.addApiInteg("lfdgd");
        System.out.println(userService.getAllApiIngegratioTables(id));
        

        super.processMessage(absSender, message, null);
    }   
}
