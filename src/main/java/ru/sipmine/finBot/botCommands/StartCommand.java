/**
 * This class represents the start command of the bot.
 * It extends the AbstractBotCommand class and implements the processMessage method.
 * It creates a new user if the user is new, otherwise it welcomes the returning user.
 */
package ru.sipmine.finBot.botCommands;

import org.hibernate.SessionFactory;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import ru.sipmine.data.services.UserService;

public class StartCommand extends AbstractBotCommand {
    // Userservice is used to create a new user or to find an existing user.
    private final UserService userService;

    public StartCommand(SessionFactory sessionFactory) {
        super("start", "Запуск бота");
        this.userService = new UserService(sessionFactory);
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        User user = message.getFrom();
        // If the user is not create yet, create a new user.
        if (userService.findIdByTelegramUserName(user.getUserName()) == 0) {
            userService.createUser(user.getId(), user.getUserName());
            message.setText("Добро пожаловать новый пользователь");
        } else {
            message.setText("С возвращением");
        }

        super.processMessage(absSender, message, null);
    }
}
