package ru.sipmine.finUtils.byBitApi.MarkeTypes;

import java.util.List;

import com.google.gson.internal.LinkedTreeMap;

import ru.sipmine.finUtils.byBitApi.ResponseJson;
@SuppressWarnings("unchecked")

public class InsuranceType {
    private String updateTime;

    private List<LinkedTreeMap<String, Object>> list;

    public InsuranceType(ResponseJson responseJson) {
        this.updateTime = (String) responseJson.getResult().get("updatedTime");
        this.list = (List<LinkedTreeMap<String, Object>>) responseJson.getResult().get("list");
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public LinkedTreeMap<String, Object> getList() {
        return list.get(0);
    }

    public String toString () {
        return "updateTime: " + updateTime + "\n" +  "list: " + list + "\n";
    }
}
