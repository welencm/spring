package red.mockers.common;

import junit.framework.TestCase;
import org.junit.Test;

public class TrueFXApiParserTest extends TestCase {
    
    private String sampleInput = 
    "EUR/USD,1517003100315,1.24,293,1.24,303,1.23694,1.24938,1.23956\n" +
    "USD/JPY,1517003100347,108.,623,108.,641,108.281,109.775,109.419\n" +
    "GBP/USD,1517003100399,1.41,524,1.41,539,1.41067,1.42891,1.41446\n" +
    "EUR/GBP,1517003100379,0.87,812,0.87,823,0.87217,0.87850,0.87626\n" +
    "USD/CHF,1517003100347,0.93,352,0.93,362,0.93232,0.94529,0.94109\n" +
    "EUR/JPY,1517003100411,135.,008,135.,045,134.538,136.145,135.621\n" +
    "EUR/CHF,1517003100405,1.16,027,1.16,055,1.15874,1.17400,1.16820\n" +
    "USD/CAD,1517003100274,1.23,235,1.23,254,1.22930,1.23921,1.23798\n" +
    "AUD/USD,1517003100429,0.81,101,0.81,107,0.80044,0.81363,0.80262\n" +
    "GBP/JPY,1517003100420,153.,729,153.,764,153.425,155.867,154.784\n" +
    "\r";
    
    @Test
    public void testRateCount() {
        TrueFXApiParser parser = new TrueFXApiParser();
        parser.parse(sampleInput);
        assertEquals(10, parser.getRateCount());
    }
    
    @Test
    public void testRateParsing() {
        TrueFXApiParser parser = new TrueFXApiParser();
        parser.parse("EUR/USD,1517003100315,1.24,293,1.24,303,1.23694,1.24938,1.23956");
        assertEquals(1, parser.getRateCount());
        assertEquals(true, parser.hasNextRate());
        
        Rate rate = parser.getNextRate();
        assertEquals("EUR/USD", rate.getPair());
        assertEquals(1517003100315L, rate.getDate().getTime());
        assertEquals(1.24293, rate.getBid());
        assertEquals(1.24303, rate.getAsk());
    }
    
    @Test
    public void testMultipleRateParsing() {
        TrueFXApiParser parser = new TrueFXApiParser();
        parser.parse(
                "EUR/CHF,1517003100405,1.16,027,1.16,055,1.15874,1.17400,1.16820\n" +
                "USD/CAD,1517003100274,1.23,235,1.23,254,1.22930,1.23921,1.23798\n"
        );
        
        assertEquals(2, parser.getRateCount());
        assertEquals(true, parser.hasNextRate());
        
        Rate rate = parser.getNextRate();
        assertEquals("EUR/CHF", rate.getPair());
        assertEquals(1517003100405L, rate.getDate().getTime());
        assertEquals(1.16027, rate.getBid());
        assertEquals(1.16055, rate.getAsk());
        
        rate = parser.getNextRate();
        assertEquals("USD/CAD", rate.getPair());
        assertEquals(1517003100274L, rate.getDate().getTime());
        assertEquals(1.23235, rate.getBid());
        assertEquals(1.23254, rate.getAsk());
        
        assertEquals(false, parser.hasNextRate());        
    }
    
    @Test
    public void testEmptyLine() {
        TrueFXApiParser parser = new TrueFXApiParser();
        parser.parse("");
        assertEquals(0, parser.getRateCount());
        assertEquals(false, parser.hasNextRate());
    }
}
