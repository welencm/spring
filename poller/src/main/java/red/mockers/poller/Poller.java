package red.mockers.poller;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.springframework.web.client.RestTemplate;
import red.mockers.common.ForexApiParser;
import red.mockers.common.Rate;
import red.mockers.common.TrueFXApiParser;

@Component
public class Poller {
    private static final Logger log = LoggerFactory.getLogger(Poller.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    private final ForexApiParser parser;

    @Value("${supplier.url}")
    private String supplierUrl;
    
    public Poller() {
        this.parser = new TrueFXApiParser();        
    }

    @Scheduled(fixedDelayString = "${polling.interval}")
    public void poll() {
        RestTemplate restTemplate = new RestTemplate();
        String responseBody = restTemplate.getForObject("http://webrates.truefx.com/rates/connect.html?f=csv", String.class);
        
        parser.parse(responseBody);
        
        while(parser.hasNextRate()) {
            Rate rate = parser.getNextRate();
            log.info(rate.toString());
            restTemplate.postForLocation(supplierUrl + "/rate", rate);
        }        
    }
}