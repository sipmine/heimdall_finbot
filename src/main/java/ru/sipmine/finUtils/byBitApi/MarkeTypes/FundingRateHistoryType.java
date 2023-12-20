package ru.sipmine.finUtils.byBitApi.MarkeTypes;

import java.util.List;

import com.google.gson.internal.LinkedTreeMap;

import ru.sipmine.finUtils.byBitApi.ResponseJson;

public class FundingRateHistoryType {
    private String category; 
    private List<LinkedTreeMap<String, Object>> list;
    
    public FundingRateHistoryType(ResponseJson responseJson) {
        this.category = (String) responseJson.getResult().get("category");
        this.list = (List<LinkedTreeMap<String, Object>>) responseJson.getResult().get("list");
        
    }

    public String getCategory() {
        return category;
    }

    public List<LinkedTreeMap<String, Object>> getList() {
        return list;
    }

    public String toString() {
        return "category: " + category + "\n" + "list: " + list + "\n";
    }
}
