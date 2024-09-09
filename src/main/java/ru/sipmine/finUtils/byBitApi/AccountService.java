package ru.sipmine.finUtils.byBitApi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class AccountService {
    private SessionGenerator sessionGenerator;
    public AccountService(SessionGenerator sessionGenerator) {
        this.sessionGenerator = sessionGenerator;
    }
    private ResponseJson getResponse(String endpoint, Map<String, Object> map) {

        ResponseJson responseJson = null;
        HttpURLConnection connection;
        try {
            if (map == null) {
                connection = sessionGenerator.connectionGET(endpoint);
            } else {
                connection = sessionGenerator.connectionGET(endpoint, map);
            }
            // Получаем поток данных из ответа
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            Gson gson = new Gson();
            responseJson = gson.fromJson(response.toString(), ResponseJson.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseJson;
    }

    public ResponseJson getWalletBalance(String accountType) {
        Map<String, Object> map = new HashMap<>();
        map.put("accountType", accountType);
        return getResponse("/v5/account/wallet-balance", map);
    }
}
