package ru.sipmine.finUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ru.sipmine.apiIntegration.TinkoffInvestApi;
import ru.tinkoff.piapi.core.models.Portfolio;

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
    

    public double getYield(String portfolioName) {
        Set<String> names = new HashSet<>();
        double yield = 0.0;
        if (portfolioName.equals("all")) {
            names = portfolioandPos.keySet();
        } else {
            names.add(portfolioName);
        }
        for(String name: names) {
            yield += tinkoffInvestApi.getYieldForPortfolio(portfolioandPos.get(name));
        }
        
        return yield;
        

    }


}
