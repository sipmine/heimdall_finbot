package ru.sipmine.apiIntegration;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import ru.tinkoff.piapi.contract.v1.Account;
import ru.tinkoff.piapi.core.InstrumentsService;
import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.UsersService;
import ru.tinkoff.piapi.core.models.Portfolio;
import ru.tinkoff.piapi.core.models.Position;
import ru.tinkoff.piapi.core.OperationsService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * This class represents a TinkoffInvestApi object that allows interaction with
 * the Tinkoff Invest API.
 * It provides methods to get the user's portfolio and other investment-related
 * information.
 */
public class TinkoffInvestApi {

    private InvestApi api;
    private UsersService usersService;
    private String accountid;
 

    public TinkoffInvestApi(String token_tin) {
        this.api = InvestApi.create(token_tin);
        usersService = api.getUserService();
        try {
            List<Account> accounts = usersService.getAccounts().get(5, TimeUnit.SECONDS);
            this.accountid = accounts.get(0).getId();
            System.out.println(accounts.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }

    public Portfolio GetPortfolio() {
        try {
            OperationsService operationsService;
            operationsService = api.getOperationsService();
            
            Portfolio portfolio = operationsService.getPortfolio(accountid).get(5, TimeUnit.SECONDS);
            return portfolio;
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return null;

    }

    public InstrumentsService gInstrumentsService() {
        return api.getInstrumentsService();
    }

    public String[] getNameandTick(String figi, String inst) {
        String name = null;
        String ticker = null;
        try {
        switch (inst) {

            case "share":
                name = gInstrumentsService().getShareByFigi(figi)
                        .get(5, TimeUnit.SECONDS).getName();
                ticker = gInstrumentsService().getShareByFigi(figi)
                        .get(5, TimeUnit.SECONDS).getTicker();
                break;
            case "etf":
                name = gInstrumentsService().getEtfByFigi(figi)
                        .get(5, TimeUnit.SECONDS).getName();
                ticker = gInstrumentsService().getEtfByFigi(figi)
                        .get(5, TimeUnit.SECONDS).getTicker();
                break;
            case "bond":
                name = gInstrumentsService().getBondByFigi(figi)
                        .get(5, TimeUnit.SECONDS).getName();
                ticker = gInstrumentsService().getBondByFigi(figi)
                        .get(5, TimeUnit.SECONDS).getTicker();
                break;
            default:
                name = " ";
                ticker = " ";
                break;
        }
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
        
        e.printStackTrace();
    }

        return new String[] {ticker, name};
    }

}
