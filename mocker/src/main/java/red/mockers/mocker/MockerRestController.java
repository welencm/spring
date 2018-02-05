package red.mockers.mocker;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import red.mockers.common.Rate;

@RestController
public class MockerRestController {
    
    private static final Logger log = LoggerFactory.getLogger(MockerRestController.class);
    
    private final RateProvider generator;
    private final RateProvider eurplnProvider;
    private final RateProvider usdplnProvider;

    public MockerRestController() throws IOException{
        generator = new RateGenerator("CHF/PLN", 3.58, 0.02, 10);
        eurplnProvider = new FileRateProvider(getClass().getResourceAsStream("/eurpln.csv"));
        usdplnProvider = new FileRateProvider(getClass().getResourceAsStream("/usdpln.csv"));
    }
    
    @RequestMapping(value = "/rate/chf/pln", method = RequestMethod.GET)
    Rate generateRate() throws Exception  {
        Rate rate = generator.getNextRate(); 
        log.info(rate.toString());
        return rate;
    }
    
    @RequestMapping(value = "/rate/eur/pln", method = RequestMethod.GET)
    Rate generateEURPLN() throws Exception {
        Rate rate = eurplnProvider.getNextRate();
        log.info(rate.toString());
        return rate;
    }    
    
    @RequestMapping(value = "/rate/usd/pln", method = RequestMethod.GET)
    Rate generateUSDPLN() throws Exception {
        Rate rate = usdplnProvider.getNextRate();
        log.info(rate.toString());
        return rate;        
    } 
}
