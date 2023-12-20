package ru.sipmine.finUtils.byBitApi.MarkeTypes;

import java.util.Map;

import ru.sipmine.finUtils.byBitApi.ResponseJson;

public class ServerTimeType {
    private ResponseJson responseJson;
    private Long timeSecond;
    private Long timeNano;
    public ServerTimeType(ResponseJson responseJson) {
        this.responseJson = responseJson;
        Map<String, Object> map = responseJson.getResult();
        timeSecond =  Long.parseLong( map.get("timeSecond").toString());
        timeNano   =  Long.parseLong(map.get("timeNano").toString());
    }
    
    public Long getTimeSecond() {
        return timeSecond;
    }

    public Long getTimeNano() {
        return timeNano;
    }

    public String toString() {
        return "timeSeconds: " + timeSecond + "\n" + "timeNano: " + timeNano + "\n";
    }
}
