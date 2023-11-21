package ru.sipmine.finBot.BotCommands;

import java.util.ArrayList;

import org.hibernate.SessionFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import ru.sipmine.data.Services.ApiIntegService;
import ru.sipmine.data.Services.UserService;
import ru.sipmine.data.tables.UsersTable;
import java.util.List; // Add this import statement

public class ApiAddCommands extends AbstractMultiCommand {
    // Userservice is used to create a new user or to find an existing user.
    private final UserService userService;
    private final ApiIntegService apiIntegService;
    public ApiAddCommands(SessionFactory sessionFactory) {
        super("apiadd", "аналитика");
        this.userService = new UserService(sessionFactory);
        this.apiIntegService = new ApiIntegService(sessionFactory);
    }


    @Override
    public SendMessage handle(Update update) {
        Message msg = update.getMessage();
        String text = "Выбери сервис";
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        
        button1.setText("Тинькофф");
        button1.setCallbackData("tininv");
        // Rest of the code...
        rowInline.add(button1);
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        SendMessage message = new SendMessage();
        message.setChatId(msg.getChatId());
        message.setText(text);
        message.setReplyMarkup(markupInline);
        return message;
    }


    @Override
    public SendMessage callback(Update update, String arg) {
        Message msg = update.getMessage();
        String text;
        int id = userService.findIdByTelegramUserName(msg.getFrom().getUserName());

        if (id > 0 && !(arg == null)) {
            UsersTable ut = userService.getUserById(id);
            apiIntegService.addApiInteg(ut,arg ,msg.getText().toString());
            text = "Токен добавлен";
        } else {
            text = "Вы не зарегестрированы введите /start, или не выбрали сервис";
        }
        return new SendMessage(msg.getChatId().toString(), text);
    }

}
