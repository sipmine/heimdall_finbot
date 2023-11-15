package ru.sipmine.apiIntegration;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.tinkoff.piapi.contract.v1.Account;
import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.UsersService;
import ru.tinkoff.piapi.core.models.Portfolio;
import ru.tinkoff.piapi.core.OperationsService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * This class represents a TinkoffInvestApi object that allows interaction with the Tinkoff Invest API.
 * It provides methods to get the user's portfolio and other investment-related information.
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
            Portfolio portfolio =
                    operationsService.getPortfolio(accountid).get(5, TimeUnit.SECONDS);
            return portfolio;
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return null;

    }

}
