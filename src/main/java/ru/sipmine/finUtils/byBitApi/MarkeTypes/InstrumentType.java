package ru.sipmine.finUtils.byBitApi.MarkeTypes;

import java.util.List;

import com.google.gson.internal.LinkedTreeMap;

import ru.sipmine.finUtils.byBitApi.ResponseJson;
@SuppressWarnings("unchecked")

public class InstrumentType {

    private String category;
    private String nextPageCursor;
    private List<LinkedTreeMap<String, Object>> list;

    public InstrumentType(ResponseJson responseJson) {

        this.category = (String) responseJson.getResult().get("category");
        this.nextPageCursor = (String) responseJson.getResult().get("nextPageCursor");
        this.list = (List<LinkedTreeMap<String, Object>>) responseJson.getResult().get("list");

    }

    public LinkedTreeMap<String, Object> getResultList() {
       return list.get(0);
    }

    public String getCategory() {
        return category;
    }

    public String getNextPageCursor() {
        return nextPageCursor;
    }

    public String toString() {
        return "category: " + category + "\n" + "next page cursor: " + nextPageCursor + "\n" + "list: " + list + "\n";
    }

}
