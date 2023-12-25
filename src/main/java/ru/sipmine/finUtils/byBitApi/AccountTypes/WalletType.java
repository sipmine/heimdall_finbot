package ru.sipmine.finUtils.byBitApi.AccountTypes;

import java.util.List;

import com.google.gson.internal.LinkedHashTreeMap;

import ru.sipmine.finUtils.byBitApi.ResponseJson;

public class WalletType {
    private List<LinkedHashTreeMap<String, Object>> list;

    public WalletType(ResponseJson responseJson) {
        this.list = (List<LinkedHashTreeMap<String, Object>>) responseJson.getResult().get("list");
    }
    
    public List<LinkedHashTreeMap<String, Object>> getWalletList() {
        return list;
    }




}
