package ru.sipmine.finBot.botCommands;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import ru.sipmine.apiIntegration.TinkoffInvestApi;
import ru.sipmine.data.services.ApiIntegService;
import ru.sipmine.data.services.UserService;
import ru.sipmine.data.tables.ApiIngegratioTable;
import ru.sipmine.finUtils.apiFun;
import ru.tinkoff.piapi.core.models.Portfolio;
import ru.tinkoff.piapi.core.models.Position;

public class GetPortfolioCommand extends AbstractMultiCommand {

    private UserService userService;
    private ApiIntegService apiIntegService;
    private TinkoffInvestApi tinkoffInvestApi;
    private String token;
    private Map<String, String> portflios;

    public GetPortfolioCommand(SessionFactory sessionFactory) {
        super("getPortfolio", "Вовзращает портфель");
        userService = new UserService(sessionFactory);
        apiIntegService = new ApiIntegService(sessionFactory);
    }



    @Override
    public SendMessage handle(Update update) {
        Long chatId =  getChatId(update.getMessage());
        int id = getUserId(update.getMessage(), userService);
        
        Set<ApiIngegratioTable> aip = userService.getAllApiIngegratioTables(id);
        token = apiFun.getToken(aip, apiIntegService, "tininv")[0];
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
            btn.setCallbackData("gpc" + names);
            row.add(btn);
        }
        InlineKeyboardButton all = new InlineKeyboardButton();
        all.setText("Все");
        all.setCallbackData("gpc" + "all");
        row.add(all);
        rows.add(row);
        keyboardMarkup.setKeyboard(rows);
        sm.setReplyMarkup(keyboardMarkup);
        return sm;
    }

    public SendMessage callback(Update update, String arg) {
        Set<String> keyName = new HashSet<>();
        if (arg.equals("all")) {
            keyName = portflios.keySet();
        } else {
            keyName.add(arg);
        }
        
        SendMessage sm = new SendMessage();
        StringBuilder htmlBuilder = new StringBuilder();
        for (String keyN: keyName) {
            String id = portflios.get(keyN);
            Portfolio portfolio = tinkoffInvestApi.GetPortfolio(id);
            List<Position> pos = portfolio.getPositions();

            htmlBuilder.append("<b>---- ").append(keyN).append(" ----</b>\n");

            for (Position position : pos) {
                String inst = position.getInstrumentType().toString();
                String figi = position.getFigi();
                String[] info = tinkoffInvestApi.getNameandTick(figi, inst);
                BigDecimal curPrice = position.getCurrentPrice().getValue();
                BigDecimal buyPrice = position.getAveragePositionPrice().getValue();
                BigDecimal quantity = position.getQuantity();

                // Append position information to the HTML message
                htmlBuilder.append("<b>Название:</b> ").append(info[1]).append("\n")
                        .append("<b>Тикер:</b> ").append(info[0]).append("\n")
                        .append("<b>Текущая цена:</b> ").append(curPrice.doubleValue()).append("\n")
                        .append("<b>Цена покупки:</b> ").append(buyPrice.doubleValue()).append("\n")
                        .append("<b>Количество:</b> ").append(quantity.doubleValue()).append("\n")
                        .append("-------------------------").append("\n");
            }
            htmlBuilder.append("\n");
        }
        sm.setParseMode("HTML");
        sm.setText(htmlBuilder.toString());
        sm.setChatId(update.getCallbackQuery().getMessage().getChatId());
        return sm;
    }

}
