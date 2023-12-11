package ru.sipmine.finBot.botCommands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import ru.sipmine.finUtils.StockPortoflioUtil;

public class GetYieldCommand extends AbstractMultiCommand {

    private UserService userService;
    private ApiIntegService apiIntegService;
    private TinkoffInvestApi tinkoffInvestApi;
    private String token;
    private Map<String, String> portflios;


    public GetYieldCommand(SessionFactory sessionFactory) {
        super("GetYield", "Returns the yield for a specified period");
        this.userService = new UserService(sessionFactory);
        this.apiIntegService = new ApiIntegService(sessionFactory);
    }
    private String getToken(Set<ApiIngegratioTable> aip) {
        int idApi = apiIntegService.findIdByName("tininv");
        String token = aip.stream()
                .filter(table -> table.getId() == idApi)
                .findFirst()
                .map(ApiIngegratioTable::getTokenApi)
                .orElse(null);
        return token;
    }

    @Override
    public SendMessage handle(Update update) {
        var msg = update.getMessage();
        Long chatId = msg.getChatId();
        int id = userService.findIdByTelegramUserName(msg.getFrom().getUserName());
        Set<ApiIngegratioTable> aip = userService.getAllApiIngegratioTables(id);
        token = getToken(aip);
        tinkoffInvestApi = new TinkoffInvestApi(token);
        this.portflios = tinkoffInvestApi.getAllPortoflio();
        SendMessage sm = new SendMessage();
        sm.setText("Выбери портфель");
        sm.setChatId(chatId);
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<InlineKeyboardButton>();
        // buttons
        Iterator<String> portflioNames = portflios.keySet().iterator();
        for (int i = 0; i < portflios.size(); i++) {
            InlineKeyboardButton btn = new InlineKeyboardButton();
            String names = portflioNames.next();
            btn.setText(names);
            btn.setCallbackData("gyc" + names);
            row.add(btn);
        }
        InlineKeyboardButton all = new InlineKeyboardButton();
        all.setText("Все");
        all.setCallbackData("gyc" + "all");
        row.add(all);
        rows.add(row);
        keyboardMarkup.setKeyboard(rows);
        sm.setReplyMarkup(keyboardMarkup);
        return sm;
    }

    @Override
    public SendMessage callback(Update update, String arg) {
        SendMessage sm = new SendMessage();
        sm.setChatId(update.getMessage().getChatId());
        sm.setText("done");
        return sm;
    }

}
