import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import ru.sipmine.apiIntegration.TinkoffInvestApi;
import ru.tinkoff.piapi.core.models.Portfolio;

public class ApiTinkoffInvestTest {
    public static String token = "";
    private static TinkoffInvestApi tinkoffInvestApi;
    private static Map<String, String> portfolioInfo;
    @BeforeClass
    public static void setUp() {
        tinkoffInvestApi = new TinkoffInvestApi(token);
    }

    @Test
    public void testGetPortfolioId() {
        String portfolioId = "testPortfolioId";
        tinkoffInvestApi.setPortfolioId(portfolioId);
        assertEquals(portfolioId, tinkoffInvestApi.getPortfolioId());
        tinkoffInvestApi.setPortfolioId(null);
    }

    @Test
    public void testPortfolioKeysAndValues() {
        portfolioInfo = tinkoffInvestApi.getAllPortoflio();
        assertNotNull(portfolioInfo);

        assertTrue(portfolioInfo.containsKey("Брокерский счет"));

        assertTrue(portfolioInfo.containsKey("ИИС"));
    
    }

    @Test
    public void testGetPortolio() {
        portfolioInfo = tinkoffInvestApi.getAllPortoflio();
        Portfolio portfolio = tinkoffInvestApi.GetPortfolio(portfolioInfo.get("Брокерский счет"));
        assertNotNull(portfolio);
    
        tinkoffInvestApi.setPortfolioId(portfolioInfo.get("ИИС"));
        portfolio = tinkoffInvestApi.GetPortfolio();
        assertNotNull(portfolio);
    }








}


      
    
   

