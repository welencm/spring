package red.mockers.mocker;

import java.io.File;
import java.io.IOException;
import junit.framework.TestCase;
import org.junit.Test;
import red.mockers.common.Rate;

public class FileRateProviderTest extends TestCase {
    
    private String testDataFileName;
    
    public FileRateProviderTest() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("FileRateProvider.TestData.csv").getFile());
        testDataFileName = file.getAbsolutePath();
    }
    
    @Test
    public void testLoadRate() throws IOException {
        FileRateProvider provider = new FileRateProvider(testDataFileName);
        Rate rate = provider.getNextRate();
        
        assertEquals("EUR/PLN", rate.getPair());
        assertEquals(4.17998, rate.getAsk());
    }
    
    @Test
    public void testLoadMultipleRates() throws IOException {
        FileRateProvider provider = new FileRateProvider(testDataFileName);
        
        Rate rate = provider.getNextRate();
        assertEquals("EUR/PLN", rate.getPair());
        assertEquals(4.17998, rate.getAsk());
        
        rate = provider.getNextRate();
        assertEquals("EUR/PLN", rate.getPair());
        assertEquals(4.18019, rate.getAsk());
        
        rate = provider.getNextRate();
        assertEquals("EUR/PLN", rate.getPair());
        assertEquals(4.18033, rate.getAsk());
        
        assertNull(provider.getNextRate());
    }
}
