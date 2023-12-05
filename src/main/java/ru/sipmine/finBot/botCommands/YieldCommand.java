package ru.sipmine.finBot.botCommands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import ru.sipmine.apiIntegration.TinkoffInvestApi;
import ru.sipmine.data.services.ApiIntegService;
import ru.sipmine.data.services.UserService;
import ru.sipmine.data.tables.ApiIngegratioTable;

public class YieldCommand extends AbstractMultiCommand  {
    
    // Existing variables remain unchanged
    private UserService userService;
    private ApiIntegService aIntegService;
    // Existing variables remain unchanged
    private String token;
    
    
    
    public YieldCommand(SessionFactory sessionFactory) {
        super("GetYield", "Returns the yield for a specified period");
        this.userService = new UserService(sessionFactory);
        this.aIntegService = new ApiIntegService(sessionFactory);
    }

    @Override
    public SendMessage handle(Update update) {
        // TODO Auto-generated method stub
        Message msg = update.getMessage();
        
        int id = userService.findIdByTelegramUserName(msg.getFrom().getUserName());
        Set<ApiIngegratioTable> aip = userService.getAllApiIngegratioTables(id);
        int idApi = aIntegService.findIdByName("tininv");
        String token = aip.stream()
                .filter(table -> table.getId() == idApi)
                .findFirst()
                .map(ApiIngegratioTable::getTokenApi)
                .orElse(null);
        this.token = token;
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(msg.getChatId());
        sendMessage.setText("Выберете за какой период, хотите получить доходность");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> Buttons = new ArrayList<InlineKeyboardButton>();

        InlineKeyboardButton yieldMonth = new InlineKeyboardButton("Доходность за месяц");
        InlineKeyboardButton yieldYear = new InlineKeyboardButton("Доходность за год");
        InlineKeyboardButton yieldAll = new InlineKeyboardButton("Доходность за всё время");

        Buttons.add(yieldMonth);
        Buttons.add(yieldYear);
        Buttons.add(yieldAll);
        keyboard.add(Buttons);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return  sendMessage;
    }

    @Override
    public SendMessage callback(Update update, String arg) {
        
        return new SendMessage();
    }

    
}
