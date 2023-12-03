package ru.sipmine.finBot.botCommands;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import java.util.Set;

import org.hibernate.SessionFactory;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import ru.sipmine.apiIntegration.TinkoffInvestApi;
import ru.sipmine.data.services.ApiIntegService;
import ru.sipmine.data.services.UserService;
import ru.sipmine.data.tables.ApiIngegratioTable;
import ru.sipmine.finUtils.StockPortoflioUtil;
import ru.tinkoff.piapi.core.models.Portfolio;
import ru.tinkoff.piapi.core.models.Position;
// ... (imports and class declaration remain unchanged)
// ... (imports and class declaration remain unchanged)

public class PortfolioCommand extends AbstractBotCommand {

    // Existing variables remain unchanged
    private UserService userService;
    private ApiIntegService aIntegService;
    // Existing variables remain unchanged
    private TinkoffInvestApi tinkoffInvestApi;

    public PortfolioCommand(SessionFactory sessionFactory) {
        super("getP", "получить портфель");
        userService = new UserService(sessionFactory);
        aIntegService = new ApiIntegService(sessionFactory);
        // Instantiate once
        aIntegService = new ApiIntegService(sessionFactory);
        // Instantiate once
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        int id = userService.findIdByTelegramUserName(message.getFrom().getUserName());
        Set<ApiIngegratioTable> aip = userService.getAllApiIngegratioTables(id);
        int idApi = aIntegService.findIdByName("tininv");
        String token = aip.stream()
                .filter(table -> table.getId() == idApi)
                .findFirst()
                .map(ApiIngegratioTable::getTokenApi)
                .orElse(null);

        if (token != null) {
            // tinkoffInvestApi = new TinkoffInvestApi(token);
            // Map<String, String> portfolioInfo = tinkoffInvestApi.getAllPortoflio();

            // Portfolio portfolio = tinkoffInvestApi.GetPortfolio(portfolioInfo.entrySet().iterator().next().getValue()); // Fetch portfolio info at once
            // List<Position> pos = portfolio.getPositions();

            StringBuilder messageText = new StringBuilder(); // StringBuilder for message content
            StockPortoflioUtil stockPortoflioUtil = new StockPortoflioUtil(token);
            stockPortoflioUtil.getAllYield();
            messageText.append(0);
            // for (Position position : pos) {
            //     String inst = position.getInstrumentType().toString();
            //     String figi = position.getFigi();
            //     String[] info = tinkoffInvestApi.getNameandTick(figi, inst);
            //     BigDecimal curPrice = position.getCurrentPrice().getValue();
            //     BigDecimal buyPrice = position.getAveragePositionPrice().getValue();
            //     BigDecimal quantity = position.getQuantity();

            //     // Append position information to the message text
            //     messageText.append("Название: ").append(info[1])
            //             .append(" Тикер: ").append(info[0])
            //             .append(" Текущая цена: ").append(curPrice.doubleValue())
            //             .append(" Цена покупки: ").append(buyPrice.doubleValue())
            //             .append(" Количество: ").append(quantity.doubleValue()).append("\n")
            //             .append("-------------------------\n");
            // }

            // Set the message text and send the message
            message.setText(messageText.toString());
            super.processMessage(absSender, message, strings);
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
