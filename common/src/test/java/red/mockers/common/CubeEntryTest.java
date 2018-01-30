package red.mockers.common;

import java.text.ParseException;
import junit.framework.TestCase;
import org.junit.Test;

public class CubeEntryTest extends TestCase{
    
    @Test
    public void testConversionToString() throws ParseException{
        CubeEntry cube = new CubeEntry("testReport", 1, new Rate("20180127-020547", "EUR/PLN", 4.21, 4.25));
        System.out.println(cube);
    }
}
