package ru.sipmine.finUtils.byBitApi.MarkeTypes;

import java.util.List;

import ru.sipmine.finUtils.byBitApi.ResponseJson;

public class OrderBookType {
    private String symbol;
    private List<?> bid;
    private List<?> ask;
    private double ts;
    private double uid;

    public OrderBookType(ResponseJson responseJson) {
        this.symbol = (String) responseJson.getResult().get("s");
        this.bid = (List<?>) responseJson.getResult().get("b");
        this.ask = (List<?>) responseJson.getResult().get("a");
        this.ts = (double) responseJson.getResult().get("ts");
        this.uid = (double) responseJson.getResult().get("u");
    }

    public String getSymbol() {
        return symbol;
    }

    public List<?> getBid() {
        return bid;
    }

    public List<?> getAsk() {
        return ask;
    }

    public double getTs() {
        return ts;
    }

    public double getUid() {
        return uid;
    }

    public String toString() {
        return "symbol: " + symbol + "\n" + "bid: " + bid + "\n" + "ask: " + ask + "\n" + "ts: " + ts + "\n" + "uid: "
                + uid + "\n";
    }

}
