package ru.sipmine.finBot.botCommands;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    // get token
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
        // TODO Auto-generated method stub
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
            btn.setCallbackData("gpc" + names); 
            row.add(btn);
        }
        rows.add(row);
        keyboardMarkup.setKeyboard(rows);
        sm.setReplyMarkup(keyboardMarkup);
        return sm;
    }

    public SendMessage callback(Update update, String arg) {
        SendMessage sm = new SendMessage();
        String id = portflios.get(arg);
        Portfolio portflio = tinkoffInvestApi.GetPortfolio(id);
        List<Position> pos = portflio.getPositions();
        StringBuilder str = new StringBuilder();
        for (Position position : pos) {
            String inst = position.getInstrumentType().toString();
            String figi = position.getFigi();
            
            String[] info = tinkoffInvestApi.getNameandTick(figi, inst);
            BigDecimal curPrice = position.getCurrentPrice().getValue();
            BigDecimal buyPrice = position.getAveragePositionPrice().getValue();
            BigDecimal quantity = position.getQuantity();

                // Append position information to the message text
            str.append("Название: ").append(info[1])
                        .append(" Тикер: ").append(info[0])
                        .append(" Текущая цена: ").append(curPrice.doubleValue())
                        .append(" Цена покупки: ").append(buyPrice.doubleValue())
                        .append(" Количество: ").append(quantity.doubleValue()).append("\n")
                        .append("-------------------------\n");
        }
        sm.setText(str.toString());
        sm.setChatId(update.getMessage().getChatId());
        return sm;
    }
}
