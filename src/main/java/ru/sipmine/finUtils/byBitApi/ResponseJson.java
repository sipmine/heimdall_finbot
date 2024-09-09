package ru.sipmine.finUtils.byBitApi;

import java.util.Map;

public class ResponseJson {
    private int retCode;
    private String retMsg;
    private Map<String, Object> result;
    private Map<String, Object> retExtInfo;
    private Long time;

    public ResponseJson() {

    }

    public ResponseJson(int retCode, String retMsg, Map<String, Object> result, Map<String, Object> retExtInfo,
            Long time) {
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.result = result;
        this.retExtInfo = retExtInfo;
        this.time = time;
    }


    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public Map<String, Object> getRetExtInfo() {

        return retExtInfo;
    }

    public void setRetExtInfo(Map<String, Object> retExtInfo) {
        this.retExtInfo = retExtInfo;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String toString() {
        return "RetCode: " + this.retCode + "\n" + "retMsg: " + this.retMsg + "\n" + "result: " + this.result + "\n"
                + "retExtInfo: " + this.retExtInfo + "\n" + "time: " + this.time;
    }
}
