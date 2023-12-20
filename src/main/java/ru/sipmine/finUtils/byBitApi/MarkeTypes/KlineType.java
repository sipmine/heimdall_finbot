package ru.sipmine.finUtils.byBitApi.MarkeTypes;

import java.util.List;
import java.util.Map;

import ru.sipmine.finUtils.byBitApi.ResponseJson;

public class KlineType {
    private String category;
    private String symbol;
    private List<String> list;

    public KlineType(ResponseJson responseJson) {
        Map<String, Object> map = responseJson.getResult();
        this.category = (String) map.get("category");
        this.symbol = (String) map.get("symbol");
        this.list = (List<String>) map.get("list");

    }

    public String getCategory() {
        return category;

    }

    public String getSymbol() {
        return symbol;
    }

    public List<String> getList() {
        return list;
    }

    public String toString() {
        return "Category: " + category + "\n" + "Symbol: " + symbol + "\n" + "list: " + list + "\n";
    }
}
