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
        try {        
            if (userService.findIdByTelegramUserName(user.getUserName().toString()) == 0){
                userService.creaateUser(user.getId(), user.getUserName());   
                message.setText("Добро пожаловать новый пользователь");
            
            } else {
                message.setText("С возвращением");
            }
        } catch (IndexOutOfBoundsException e) {
        }
        // List<Users> users = userService.findByTelegramUserName();

        
        super.processMessage(absSender, message, null);

    }
}
