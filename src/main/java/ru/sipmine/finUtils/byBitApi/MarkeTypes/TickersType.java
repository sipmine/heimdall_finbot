package ru.sipmine.finUtils.byBitApi.MarkeTypes;

import java.util.List;

import com.google.gson.internal.LinkedTreeMap;

import ru.sipmine.finUtils.byBitApi.ResponseJson;

public class TickersType {
    private String category;
    private List<LinkedTreeMap<String, Object>> list;

    public TickersType(ResponseJson responseJson) {
        category = (String) responseJson.getResult().get("category");
        list = (List<LinkedTreeMap<String, Object>>) responseJson.getResult().get("list");
    }

    public String getCategory() {
        return category;
    }

    public LinkedTreeMap<String, Object> getList() {
        return list.get(0);
    }


    public String toString() {
        return "category: " + category + "\n" + "list: " + list + "\n";
    }
}
