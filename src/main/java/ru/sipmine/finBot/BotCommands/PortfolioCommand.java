package ru.sipmine.finBot.BotCommands;

import java.util.Set;

import org.glassfish.grizzly.http.server.Session;
import org.hibernate.SessionFactory;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import ru.sipmine.apiIntegration.TinkoffInvestApi;
import ru.sipmine.data.Services.ApiIntegService;
import ru.sipmine.data.Services.UserService;
import ru.sipmine.data.tables.ApiIngegratioTable;

public class PortfolioCommand extends AbstractBotCommand {


    private TinkoffInvestApi tinkoffInvestApi;
    private UserService userService;
    public PortfolioCommand(SessionFactory sessionFactory) {
        super("getP", "получить портофлио");
        userService = new UserService(sessionFactory);
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        int id = userService.findIdByTelegramUserName(message.getFrom().getUserName());
        Set<ApiIngegratioTable> aip = userService.getAllApiIngegratioTables(id);

        super.processMessage(absSender, message, strings);
    }

    @Override
    public String toString() {
        return super.toString();
    }
    
}
