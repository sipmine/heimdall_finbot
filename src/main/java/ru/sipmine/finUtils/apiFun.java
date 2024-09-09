package ru.sipmine.finUtils;

import java.util.Set;

import ru.sipmine.data.services.ApiIntegService;
import ru.sipmine.data.tables.ApiIngegratioTable;

public  class apiFun {
    

    public static String[] getToken(Set<ApiIngegratioTable> aip, ApiIntegService apiIntegService, String apiName) {
        int idApi = apiIntegService.findIdByName(apiName);
        String token = aip.stream()
                .filter(table -> table.getId() == idApi)
                .findFirst()
                .map(ApiIngegratioTable::getTokenApi)
                .orElse(null);
        String secret = aip.stream()
                .filter(table -> table.getId() == idApi)
                .findFirst()
                .map(ApiIngegratioTable::getSecret)
                .orElse(null);
        return new String[]{token, secret};
    }

}
