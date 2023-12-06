package ru.sipmine.finUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ru.sipmine.apiIntegration.TinkoffInvestApi;
import ru.tinkoff.piapi.core.models.Portfolio;
import ru.tinkoff.piapi.core.models.Position;

public class StockPortoflioUtil {

    // 1. Portfolio info 
    // Stock, ETF, Features, Wallet 
    // 2. Portfolio indicators
    // Income, infil, div, ...
    private TinkoffInvestApi tinkoffInvestApi;

    private Map<String, Portfolio> portfolioandPos = new HashMap<>();
    public StockPortoflioUtil(String apiToken) {
        tinkoffInvestApi = new TinkoffInvestApi(apiToken);
        Map<String, String> nameAndTick = tinkoffInvestApi.getAllPortoflio();
        // get all portfolio position 
        int lenght = nameAndTick.size();
        Iterator<String> portfolioId = nameAndTick.values().iterator();
        Iterator<String> portfolioName = nameAndTick.keySet().iterator();
        for (int i = 0; i < lenght; i++) {
            Portfolio portfolio = tinkoffInvestApi.GetPortfolio(portfolioId.next().toString());
            portfolioandPos.put(portfolioName.next().toString(), portfolio);
        }
  
    }
    // all portfolio yield
    public double getAllYield() {
        double yield = 0.0; 

        Iterator<Portfolio> iterator = portfolioandPos.values().iterator();
       
        System.out.println(portfolioandPos.size());
        for (int i = 0; i < portfolioandPos.size(); i++) {
            System.out.println(i);
            yield += iterator.next().getExpectedYield().doubleValue();
           
        }
        return yield;
    }

    public double getYieldMonth() {
        Portfolio iterator = portfolioandPos.values().iterator().next();
        
        return tinkoffInvestApi.getYieldForPortfolio(iterator);
        

    }


}
