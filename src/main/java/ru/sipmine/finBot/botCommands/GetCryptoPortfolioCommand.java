package ru.sipmine.finBot.botCommands;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import com.google.gson.internal.LinkedHashTreeMap;
import com.google.gson.internal.LinkedTreeMap;

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

    public GetCryptoPortfolioCommand(SessionFactory sessionFactory){
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
            
        return new String[] {token, secret};
    }




    @Override
    public void processMessage(AbsSender absSender, org.telegram.telegrambots.meta.api.objects.Message message, String[] strings) {
        // TODO Auto-generated method stub
        User user = message.getFrom(); 
    
        
        int id = userService.findIdByTelegramUserName(user.getUserName());
        Set<ApiIngegratioTable> aip = userService.getAllApiIngegratioTables(id);
        
        tokens = getToken(aip);
        SessionGenerator sessionGenerator = new SessionGenerator(tokens[1], tokens[0]);
        AccountService as = new AccountService(sessionGenerator);
        WalletType walletType = new WalletType(as.getWalletBalance("UNIFIED"));     
        Map<String, Object>  map = new HashMap<>();
        map = walletType.getWalletList().get(0);

        message.setText("k");
        super.processMessage(absSender, message, null); 
        
    }






}
