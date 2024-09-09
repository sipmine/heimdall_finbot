package ru.sipmine.finUtils.byBitApi.MarkeTypes;

import java.util.List;

import com.google.gson.internal.LinkedTreeMap;

import ru.sipmine.finUtils.byBitApi.ResponseJson;
@SuppressWarnings("unchecked")
public class OpenInterestType {

    private String category;
    private String symbol;
    private List<LinkedTreeMap<String, Object>> list;
    private String nextPageCursor;

    public OpenInterestType(ResponseJson responseJson) {
        this.category = (String) responseJson.getResult().get("category");
        this.symbol = (String) responseJson.getResult().get("symbol");
        this.list = (List<LinkedTreeMap<String, Object>>) responseJson.getResult().get("list");
        this.nextPageCursor = (String) responseJson.getResult().get("nextPageCursor");
    }

    public String getCategory() {
        return category;
    }

    public String getSymbol() {
        return symbol;
    }

    public LinkedTreeMap<String, Object> getList() {
        return list.get(0);
    }

    public String getNextPageCursor() {
        return nextPageCursor;
    }

    public String toString() {
        return "category: " + category + "\n" + "symbol: " + symbol + "\n" + "list: " + list + "\n"
                + "next page cursor: " + nextPageCursor + "\n";
    }
}
