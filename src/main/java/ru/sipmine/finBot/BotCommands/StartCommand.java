package ru.sipmine.finBot.BotCommands;


import java.util.List;

import org.hibernate.SessionFactory;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.sipmine.data.Services.UserService;
import ru.sipmine.data.tables.Users;

public final class StartCommand extends AbstractBotCommand {
    private SessionFactory sessionFactory;
    public StartCommand(SessionFactory sessionFactory) {
        super("start", "Запуск бота");
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {

        User user = message.getFrom();
        UserService userService = new UserService(sessionFactory);
        
        
        message.setText("Добро пожаловать, новый пользователь!\n");
        userService.creaateUser(user.getId(), user.getUserName());
        List<Users> users = userService.findByTelegramUserName();
        System.out.println("ID: " + users.get(0).getId());

        
        super.processMessage(absSender, message, null);

    }
}
