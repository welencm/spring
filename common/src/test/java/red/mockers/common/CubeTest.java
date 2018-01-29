package red.mockers.common;

import java.text.ParseException;
import junit.framework.TestCase;
import org.junit.Test;

public class CubeTest extends TestCase{
    
    @Test
    public void testConversionToString() throws ParseException{
        Cube cube = new Cube("testReport", new Rate("20180127-020547", "EUR/PLN", 4.21, 4.25));
        System.out.println(cube);
    }
}
