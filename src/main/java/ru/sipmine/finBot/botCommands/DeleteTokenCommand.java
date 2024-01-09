package ru.sipmine.finBot.botCommands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import ru.sipmine.data.services.ApiIntegService;
import ru.sipmine.data.services.UserService;
import ru.sipmine.data.tables.ApiIngegratioTable;

public class DeleteTokenCommand extends AbstractMultiCommand {
    private UserService userService;
    private ApiIntegService apiIntegService;

    public DeleteTokenCommand(SessionFactory sessionFactory) {
        super("delete", "delete tokens");
        userService = new UserService(sessionFactory);
        apiIntegService = new ApiIntegService(sessionFactory); // TODO Auto-generated constructor stub
    }

    @Override
    public SendMessage handle(Update update) {
        // get user

        var msg = update.getMessage().getFrom();
        long chatId = update.getMessage().getChatId();
        int userId = userService.findIdByTelegramUserName(msg.getUserName());

        Set<ApiIngegratioTable> aip = userService.getAllApiIngegratioTables(userId);
        List<String> tokenNames = new ArrayList<>();
        Iterator<ApiIngegratioTable> aipIterator = aip.iterator();
        SendMessage message = new SendMessage();

        message.setChatId(chatId);
        message.setText("Выбери токен для удаления");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> Buttons = new ArrayList<InlineKeyboardButton>();

        while (aipIterator.hasNext()) {
            tokenNames.add(aipIterator.next().getNameApi());
        }
        for (int i = 0; i < tokenNames.size(); i++) {
            InlineKeyboardButton btn = new InlineKeyboardButton();
            btn.setText(tokenNames.get(i));
            btn.setCallbackData("gdc" + tokenNames.get(i));
            Buttons.add(btn);
        }
        keyboard.add(Buttons);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(inlineKeyboardMarkup);
        // get list of tokens (name)

        // choice token remove

        // TODO Auto-generated method stub
        return message;
    }

    @Override
    public SendMessage callback(Update update, String arg) {
        // TODO Auto-generated method stub

        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        System.out.println("l");
        int id_api = apiIntegService.findIdByName(arg);
        System.out.println("t");
        apiIntegService.deleteApiInteg(id_api);
        System.out.println("jj");
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("токен успешно удалён");
        return message;
    }

}
