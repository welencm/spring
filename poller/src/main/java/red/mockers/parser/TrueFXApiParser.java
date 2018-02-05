package red.mockers.parser;

import java.util.Date;
import java.util.LinkedList;
import red.mockers.common.Rate;

public class TrueFXApiParser implements ForexApiParser{
    private final LinkedList<Rate> rates = new LinkedList<>();
    
    @Override
    public void parse(String input) {
        rates.clear();
        
        //"EUR/USD,1517003100315,1.24,293,1.24,303,1.23694,1.24938,1.23956\n"
        //"USD/JPY,1517003100347,108.,623,108.,641,108.281,109.775,109.419\n"
        String[] rows = input.split("\n");
        
        //"EUR/USD,1517003100315,1.24,293,1.24,303,1.23694,1.24938,1.23956"
        for(String row : rows) {
            String[] fields = row.split(",");
            
            if(fields.length == 9) {
                double bid = Double.parseDouble(fields[2].concat(fields[3]));
                double ask = Double.parseDouble(fields[4].concat(fields[5]));

                rates.add(new Rate(new Date(), fields[0], bid, ask));
            }
        }
    }

    @Override
    public int getRateCount() {
        return rates.size();
    }

    @Override
    public boolean hasNextRate() {
        if(rates.size() > 0)
            return true;
        else
            return false;
    }

    @Override
    public Rate getNextRate() {
        return rates.poll();
    }
   
}
