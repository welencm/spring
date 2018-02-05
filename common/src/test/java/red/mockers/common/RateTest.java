package red.mockers.common;

import java.text.ParseException;
import junit.framework.TestCase;
import org.junit.Test;

public class RateTest extends TestCase{
    
    @Test
    public void testConversionToString() throws ParseException{
        Rate rate = new Rate("20180127-020547", "EUR/PLN", 4.1291, 4.135);
        assertEquals("{\"date\":\"20180127-010547\",\"pair\":\"EUR/PLN\",\"bid\":4.1291,\"ask\":4.135}", rate.toString());
    }
}
