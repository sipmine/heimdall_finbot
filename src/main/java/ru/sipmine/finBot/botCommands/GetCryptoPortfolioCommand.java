package ru.sipmine.finBot.botCommands;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import ru.sipmine.data.services.ApiIntegService;
import ru.sipmine.data.services.UserService;
import ru.sipmine.data.tables.ApiIngegratioTable;
import ru.sipmine.finUtils.byBitApi.AccountService;
import ru.sipmine.finUtils.byBitApi.SessionGenerator;
import ru.sipmine.finUtils.byBitApi.AccountTypes.WalletType;

public class GetCryptoPortfolioCommand extends AbstractBotCommand {
    private UserService userService;
    private ApiIntegService apiIntegService;
    private String[] tokens;

    public GetCryptoPortfolioCommand(SessionFactory sessionFactory) {
        super("getCrypto", "Возвращает крипто портфель");
        userService = new UserService(sessionFactory);
        apiIntegService = new ApiIntegService(sessionFactory);
    }

    private String[] getToken(Set<ApiIngegratioTable> aip) {
        int idApi = apiIntegService.findIdByName("bybit");
        String token = aip.stream()
                .filter(table -> table.getId() == idApi)
                .findFirst()
                .map(ApiIngegratioTable::getTokenApi)
                .orElse(null);
        String secret = aip.stream()
                .filter(table -> table.getId() == idApi)
                .findFirst()
                .map(ApiIngegratioTable::getSecret)
                .orElse(null);

        return new String[] { token, secret };
    }

    @Override
    public void processMessage(AbsSender absSender, org.telegram.telegrambots.meta.api.objects.Message message,
            String[] strings) {
        User user = message.getFrom();

        int id = userService.findIdByTelegramUserName(user.getUserName());
        Set<ApiIngegratioTable> aip = userService.getAllApiIngegratioTables(id);

        tokens = getToken(aip);
        SessionGenerator sessionGenerator = new SessionGenerator(tokens[1], tokens[0]);
        AccountService as = new AccountService(sessionGenerator);
        WalletType walletType = new WalletType(as.getWalletBalance("UNIFIED"));
        Map<String, Object> map = new HashMap<>();
        map = walletType.getWalletList().get(0);
        List<?> coins = (List<?>) map.get("coin");
        StringBuilder sb = new StringBuilder();
        sb.append("----- криптопортфель -----\n");
        for (int i = 0; i<coins.size(); i++) {
            Map<?, ?> coin = (Map<?, ?>) coins.get(i);
            BigDecimal cap = new BigDecimal(coin.get("usdValue").toString());
            BigDecimal min = new BigDecimal(0.1);
            if (cap.compareTo(min) < 0.1) {
                continue;
            }
            
            sb.append("Название: " + coin.get("coin").toString() + "\n");
            sb.append("Текущея стоимсоть: " + coin.get("usdValue").toString() + "\n");
            sb.append("Кол-во: " + coin.get("walletBalance").toString() + "\n");
            sb.append("P&L: " + coin.get("cumRealisedPnl").toString() + "\n");
            sb.append("---------------------\n");
        }
        message.setText(sb.toString());
        super.processMessage(absSender, message, null);

    }

}
