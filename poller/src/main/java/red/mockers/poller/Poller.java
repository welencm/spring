package red.mockers.poller;

import red.mockers.parser.ForexApiParser;
import red.mockers.parser.TrueFXApiParser;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.client.RestTemplate;
import red.mockers.common.Rate;

@Component
@RestController
@RequestMapping("/config")
public class Poller {
    private static final Logger log = LoggerFactory.getLogger(Poller.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    private int pollTickNumber = 5;
    private int tickNumber = 0;
    
    private final ForexApiParser parser;

    @Value("${supplier.url}")
    private String supplierUrl;
    
    @Value("${mocker.url}")
    private String mockerUrl;
    
    public Poller() {
        this.parser = new TrueFXApiParser();   
    }

    @Scheduled(fixedDelay = 1000)
    public void tick() {
        tickNumber++;
        if(tickNumber >= pollTickNumber){
            tickNumber = 0;
            pollMocker();
            pollTrueFX();
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> setInterval(@RequestBody Config config) {
        log.info("New polling interval = " + config.getInterval());        
        pollTickNumber = config.getInterval();
        tickNumber = 0;
        return ResponseEntity.accepted().build();
    }
    
    private void pollTrueFX() {
        RestTemplate restTemplate = new RestTemplate();
        String responseBody = restTemplate.getForObject("http://webrates.truefx.com/rates/connect.html?f=csv", String.class);
        
        parser.parse(responseBody);

        while(parser.hasNextRate()) {
            Rate rate = parser.getNextRate();
            log.info(rate.toString());
            restTemplate.postForLocation(supplierUrl + "/rate", rate);
        }
    }
    
    private void pollMocker() {
        RestTemplate restTemplate = new RestTemplate();
        
        Rate rate = restTemplate.getForObject( mockerUrl + "/rate/eur/pln", Rate.class);
        log.info(rate.toString());
        restTemplate.postForLocation(supplierUrl + "/rate", rate);
        
        rate = restTemplate.getForObject(mockerUrl + "/rate/usd/pln", Rate.class);
        log.info(rate.toString());
        restTemplate.postForLocation(supplierUrl + "/rate", rate);
        
        rate = restTemplate.getForObject(mockerUrl + "/rate/chf/pln", Rate.class);
        log.info(rate.toString());
        restTemplate.postForLocation(supplierUrl + "/rate", rate);
    }
}