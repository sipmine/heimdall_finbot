package ru.sipmine.finUtils.byBitApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class MarketService {
    private SessionGenerator sessionGenerator;

    public MarketService(SessionGenerator sessionGenerator) {
        this.sessionGenerator = sessionGenerator;
    }

    private ResponseJson getResponse(String endpoint, Map<String, Object> map) {
        ResponseJson responseJson = null;
        HttpURLConnection connection;
        try {
            if (map == null) {
                connection = sessionGenerator.connectionGET(endpoint);
            } else {
                connection = sessionGenerator.connectionGET(endpoint, map);
            }
            // Получаем поток данных из ответа
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            Gson gson = new Gson();
            responseJson = gson.fromJson(response.toString(), ResponseJson.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseJson;
    }

    public ResponseJson getServerTime()
            throws InvalidKeyException, NoSuchAlgorithmException, JsonSyntaxException, IOException {
        return getResponse("/v4/market/time", null);

    }

    public ResponseJson getKline(String pairCurrency, int interval, String category)
            throws InvalidKeyException, NoSuchAlgorithmException, JsonSyntaxException, IOException {

        Map<String, Object> map = new HashMap<>();
        map.put("symbol", pairCurrency);
        map.put("interval", interval);
        map.put("limit", 1);
        map.put("category", category);
        return getResponse("/v5/market/kline", map);

    }

    public ResponseJson getMarkPriceKline(String pairCurrency, int interval)
            throws InvalidKeyException, NoSuchAlgorithmException, JsonSyntaxException, IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("symbol", pairCurrency);
        map.put("interval", interval);
        return getResponse("/v5/market/mark-price-kline", map);

    }

    public ResponseJson getIndexPriceKline(String pairCurrency, int interval)
            throws InvalidKeyException, NoSuchAlgorithmException, JsonSyntaxException, IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("symbol", pairCurrency);
        map.put("interval", interval);
        return getResponse("/v5/market/index-price-kline", map);
    }

    public ResponseJson getInstrumentInfo(String pairCurrency, String category)
            throws InvalidKeyException, NoSuchAlgorithmException, JsonSyntaxException, IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("symbol", pairCurrency);
        map.put("category", category);
        return getResponse("/v5/market/instruments-info", map);

    }

    public ResponseJson getOrderBook(String pairCurrency, String category, int limit)
            throws InvalidKeyException, NoSuchAlgorithmException, JsonSyntaxException, IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("symbol", pairCurrency);
        map.put("category", category);
        map.put("limit", limit);

        return getResponse("/v5/market/orderbook", map);

    }

    public ResponseJson getTicker(String category, String pairCurrency)
            throws InvalidKeyException, NoSuchAlgorithmException, JsonSyntaxException, IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("category", category);
        map.put("symbol", pairCurrency);

        return getResponse("/v5/market/tickers", map);

    }

    public ResponseJson getFundingRateHistory(String category, String pairCurrency)
            throws InvalidKeyException, NoSuchAlgorithmException, JsonSyntaxException, IOException {

        Map<String, Object> map = new HashMap<>();
        map.put("category", category);
        map.put("symbol", pairCurrency);
        return getResponse("/v5/market/funding/history", map);
    }

    public ResponseJson getPublicRecentTraidingHistory(String category, String pairCurrency)
            throws InvalidKeyException, NoSuchAlgorithmException, JsonSyntaxException, IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("category", category);
        map.put("symbol", pairCurrency);

        return getResponse("/v5/market/recent-trade", map);

    }

    public ResponseJson getOpenInterest(String category, String pairCurrency, int intervalTime)
            throws InvalidKeyException, NoSuchAlgorithmException, JsonSyntaxException, IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("category", category);
        map.put("symbol", pairCurrency);
        map.put("intervalTime", intervalTime);

        return getResponse("/v5/market/open-interest", map);

    }

    public ResponseJson getHistoricalVolatility(String category, String baseCoin)
            throws InvalidKeyException, NoSuchAlgorithmException, JsonSyntaxException, IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("category", category);
        map.put("baseCoin", baseCoin);
        return getResponse("/v5/market/historical-volatility", map);

    }

    public ResponseJson getInsurance(String coin)
            throws InvalidKeyException, NoSuchAlgorithmException, JsonSyntaxException, IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("coin", coin);
        return getResponse("/v5/market/insurance", map);

    }

    public ResponseJson getRiskLimit(String category, String pairCurrency)
            throws InvalidKeyException, NoSuchAlgorithmException, JsonSyntaxException, IOException {

        Map<String, Object> map = new HashMap<>();
        map.put("category", category);
        map.put("symbol", pairCurrency);
        return getResponse("/v5/market/risk-limit", map);

    }

    public ResponseJson getDeliveryPrice(String category, String baseCoin)
            throws InvalidKeyException, NoSuchAlgorithmException, JsonSyntaxException, IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("category", category);
        map.put("baseCoin", baseCoin);
        return getResponse("/v5/market/delivery-price", map);

    }

    public ResponseJson getLongShortRatio(String category, String pairCurrancy, String period)
            throws InvalidKeyException, NoSuchAlgorithmException, JsonSyntaxException, IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("category", category);
        map.put("symbol", pairCurrancy);
        map.put("period", period);
        return getResponse("/v5/market/delivery-price", map);

    }
}