package red.mockers.common;

import java.text.ParseException;
import junit.framework.TestCase;
import org.junit.Test;

public class CubeEntryTest extends TestCase{
    
    @Test
    public void testConversionToString() throws ParseException{
        CubeEntry cubeEntry = new CubeEntry("testCube", 1, new Rate("20180127-020547", "EUR/PLN", 4.21, 4.25));
        
        String expected = "{\"reportName\":\"testCube\",\"columnNames\":[\"id:PK\",\"currency:PK\",\"price\",\"date\",\"time\"],\"columnTypes\":[\"long\",\"string\",\"double\",\"datetime\",\"time\"],\"rows\":[[\"id=1\",\"currency=EUR/PLN\",\"price=4.25\",\"date=2018-01-27T02:05:47\",\"time=02:05:47\"]]}";
        String actual = cubeEntry.toString();
        assertEquals(expected, actual);
    }
}
