package ru.sipmine.finUtils.byBitApi.MarkeTypes;

import java.util.List;

import com.google.gson.internal.LinkedTreeMap;

import ru.sipmine.finUtils.byBitApi.ResponseJson;
@SuppressWarnings("unchecked")

public class DeliveryPriceType {
    private String category;
    private List<LinkedTreeMap<String, Object>> list;

    public DeliveryPriceType(ResponseJson responseJson) {
        this.category = (String) responseJson.getResult().get("category");
        this.list = (List<LinkedTreeMap<String, Object>>) responseJson.getResult().get("list");
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
