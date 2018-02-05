package red.mockers.mocker;

import junit.framework.TestCase;
import org.junit.Test;
import red.mockers.common.Rate;

public class RateGeneratorTest extends TestCase {

    @Test
    public void testGettingRate() {
        RateGenerator gen = new RateGenerator("PLN/EUR", 4.2, 0.3, 5);
        
        Rate rate = gen.getNextRate();
        assertNotNull(rate);
        assertEquals("PLN/EUR", rate.getPair());
    }
    
    @Test
    public void testRateValues() throws Exception {
        RateGenerator gen = new RateGenerator("PLN/EUR", 4.2, 0.3, 10);
        
        for(int i = 0; i < 100; i++) {
            Rate rate = gen.getNextRate();
            boolean inBboundaries = true;
            
            if(rate.getBid() > 4.2 + 0.3 || rate.getBid() < 4.2 - 0.3)
                inBboundaries = false;
            
            assertTrue(inBboundaries);
            
//            boolean bidLowerThanAsk = true;
//            if(rate.getBid() >= rate.getAsk())
//                bidLowerThanAsk = false;
//            
//            assertTrue(bidLowerThanAsk);
        }
    }
}
