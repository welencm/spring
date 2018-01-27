package red.mockers.common;

public interface ForexApiParser {      
    public void parse(String input);
    public int getRateCount();
    
    public boolean hasNextRate();
    public Rate getNextRate();  
}
