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
import ru.sipmine.finUtils.StockPortoflioUtil;

public class YieldCommand extends AbstractMultiCommand {

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
        // test
        System.out.println(1111);
        StockPortoflioUtil stockPortoflioUtil = new StockPortoflioUtil(token);
        stockPortoflioUtil.getYieldMonth("Брокерский счет");
        System.out.println(2222);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton yieldMonth = new InlineKeyboardButton();
        InlineKeyboardButton yieldYear = new InlineKeyboardButton();
        InlineKeyboardButton yieldAll = new InlineKeyboardButton();
        yieldMonth.setText("за месяц");
        yieldMonth.setCallbackData("yieldMonth");
        yieldYear.setText("за год");
        yieldYear.setCallbackData("yieldYear");
        yieldAll.setText("за всё время");
        yieldAll.setCallbackData("yieldAll");
        rowInline.add(yieldMonth);
        rowInline.add(yieldYear);
        rowInline.add(yieldAll);
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(msg.getChatId());
        sendMessage.setText("Выберете за какой период, хотите получить доходность");

        sendMessage.setReplyMarkup(markupInline);
        return sendMessage;
    }

    @Override
    public SendMessage callback(Update update, String arg) {
        SendMessage sm = new SendMessage();
        sm.setChatId(update.getMessage().getChatId());
        sm.setText("done");
        return sm;
    }

}
