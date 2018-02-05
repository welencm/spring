package red.mockers.mocker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Random;
import red.mockers.common.Rate;

public class RateGenerator implements RateProvider{    
    static double SPREAD_RATE = 0.02;
    
    String currencyPair;
    double baseCourse;
    double deviation;
    
    int maxChaingLength;
    int chainLength;
    int currentChainPosition;
            
    double maxBound;
    double minBound;
    double spread;
    double lastValue;
    double targetValue;
    
    Random rand;
    
    public RateGenerator(String currencyPair, double baseCourse, double deviation, int maxChaingLength) {
        this.currencyPair = currencyPair;
        this.baseCourse = baseCourse;
        this.lastValue = baseCourse;
        this.deviation = deviation;
        this.maxChaingLength = maxChaingLength;
        
        this.spread = baseCourse * SPREAD_RATE;
        
        rand = new Random();
        restartChain();
    }
    
    @Override
    public Rate getNextRate() {
        double value = round(getNextValue());
        return new Rate(new Date(), currencyPair, value, value);
    }
    
    private double round(double value) {
       return new BigDecimal(value).setScale(5, RoundingMode.HALF_UP).doubleValue();
    }
    
    private double getNextValue() {       
        
        if(currentChainPosition >= chainLength)
            restartChain();
        else
            currentChainPosition++;
                
        double nextValue = minBound + rand.nextDouble() * (maxBound - minBound);
        
        if(targetValue > nextValue)
            minBound = nextValue;
        else
            maxBound = nextValue;
        
        return nextValue;
    }
    
    private void restartChain(){
        //determine new chain lenght
        chainLength = 1 + (int) (rand.nextFloat() * maxChaingLength);
        currentChainPosition = 1;

        //select new target value
        do {
            targetValue = generateTargetValue();
        }while(lastValue == targetValue);

        if(targetValue > lastValue){
            minBound = lastValue;
            maxBound = targetValue;
        }
        else{
            minBound = targetValue;
            maxBound = lastValue;
        }       
    }
    
    private double generateTargetValue() {
        return (baseCourse - deviation) + rand.nextDouble() * deviation * 2;
    }
}