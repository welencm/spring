package red.mockers.parser;

import red.mockers.common.Rate;

public interface ForexApiParser {      
    public void parse(String input);
    public int getRateCount();
    
    public boolean hasNextRate();
    public Rate getNextRate();  
}
