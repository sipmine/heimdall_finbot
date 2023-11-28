package ru.sipmine.apiIntegration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.concurrent.ExecutionException;

import ru.tinkoff.piapi.contract.v1.Account;
import ru.tinkoff.piapi.core.InstrumentsService;
import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.UsersService;
import ru.tinkoff.piapi.core.models.Portfolio;
import ru.tinkoff.piapi.core.OperationsService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * This class represents a TinkoffInvestApi object that allows interaction with
 * the Tinkoff Invest API.
 * It provides methods to get the user's portfolio and other investment-related
 * information.
 * This class represents a TinkoffInvestApi object that allows interaction with
 * the Tinkoff Invest API.
 * It provides methods to get the user's portfolio and other investment-related
 * information.
 */
public class TinkoffInvestApi {

    private InvestApi api;
    private UsersService usersService;
    private String accountid;
    private Map<String, String> portfolioInfo;
    private List<Account> accounts;

    public TinkoffInvestApi(String token_tin) {
        this.api = InvestApi.create(token_tin);
        usersService = api.getUserService();
        try {
            accounts = usersService.getAccounts().get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void SetAccId(String accId) {
        this.accountid = accId;
    }

    public Map<String, String> getAllPortoflio() {
        int l = accounts.size();
        portfolioInfo = new HashMap<String, String>();
        for (int i = 0; i < l; i++) {
            String portfolioName = accounts.get(i).getName();
            String portfolioId = accounts.get(i).getId();
            
            portfolioInfo.put(portfolioName, portfolioId);
        }
        portfolioInfo.remove("");
        return portfolioInfo;
    }

    public Portfolio GetPortfolio(String portoflioId) {
        try {
            OperationsService operationsService;
            operationsService = api.getOperationsService();

            Portfolio portfolio = operationsService.getPortfolio(portoflioId).get(5, TimeUnit.SECONDS);
            return portfolio;
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return null;
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

        return new String[] { ticker, name };
    }


}
