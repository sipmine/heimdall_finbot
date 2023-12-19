package ru.sipmine.finUtils.byBitApi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.google.gson.Gson;

import jakarta.websocket.ClientEndpoint;

@ClientEndpoint
public class SessionGenerator {
    private String apiKeySecret;
    private String apiKey;
    private String timestamp = Long.toString(ZonedDateTime.now().toInstant().toEpochMilli());
    private final String RECV_WINDOW = "5000";

    public SessionGenerator(String apiKeySecret, String apiKey) {
        this.apiKeySecret = apiKeySecret;
        this.apiKey = apiKey;

    }

    public int checkConnect() {
        int statusCode = -1;
        try {
            URL url = new URL("https://api.bybit.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            statusCode = connection.getResponseCode();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return statusCode;
    }
    // request GET 
    public HttpURLConnection connectionGET(String endpoint, Map map)
            throws InvalidKeyException, NoSuchAlgorithmException {
        HttpURLConnection connection = null;
        String sign = genGetSign(map);
        StringBuilder sb = genQueryStr(map);
        try {
            URL url = new URL("https://api.bybit.com" + endpoint + "?" + sb);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("X-BAPI-API-KEY", apiKey);
            connection.setRequestProperty("X-BAPI-SIGN", sign);
            connection.setRequestProperty("X-BAPI-TIMESTAMP", timestamp);
            connection.setRequestProperty("X-BAPI-RECV-WINDOW", RECV_WINDOW);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }
    public HttpURLConnection connectionGET(String endpoint)
            throws InvalidKeyException, NoSuchAlgorithmException {
        HttpURLConnection connection = null;
        
        try {
            URL url = new URL("https://api.bybit.com" + endpoint);
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }
    // requset POST
    public HttpURLConnection connectionPOST(String endpoint, Map map)
            throws InvalidKeyException, NoSuchAlgorithmException {
        HttpURLConnection connection = null;
        String sign = genPostSign(map);
        StringBuilder sb = genQueryStr(map);
        try {
            URL url = new URL("https://api.bybit.com" + endpoint + "?" + sb);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("X-BAPI-API-KEY", apiKey);
            connection.setRequestProperty("X-BAPI-SIGN", sign);
            connection.setRequestProperty("X-BAPI-TIMESTAMP", timestamp);
            connection.setRequestProperty("X-BAPI-RECV-WINDOW", RECV_WINDOW);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }
 



    private String genPostSign(Map<String, Object> params) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(apiKeySecret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        String paramJson = new Gson().toJson(params);
        String sb = timestamp + apiKey + RECV_WINDOW + paramJson;
        return bytesToHex(sha256_HMAC.doFinal(sb.getBytes()));
    }

    /**
     * The way to generate the sign for GET requests
     * 
     * @param params: Map input parameters
     * @return signature used to be a parameter in the header
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    private String genGetSign(Map<String, Object> params) throws NoSuchAlgorithmException, InvalidKeyException {
        StringBuilder sb = genQueryStr(params);
        String queryStr = timestamp + apiKey + RECV_WINDOW + sb;

        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(apiKeySecret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        return bytesToHex(sha256_HMAC.doFinal(queryStr.getBytes()));
    }

    /**
     * To convert bytes to hex
     * 
     * @param hash
     * @return hex string
     */
    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private StringBuilder genQueryStr(Map<String, Object> map) {
        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();
        StringBuilder sb = new StringBuilder();
        while (iter.hasNext()) {
            String key = iter.next();
            sb.append(key)
                    .append("=")
                    .append(map.get(key))
                    .append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb;
    }
}
