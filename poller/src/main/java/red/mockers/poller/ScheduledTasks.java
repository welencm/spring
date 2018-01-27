package red.mockers.poller;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.springframework.web.client.RestTemplate;
import red.mockers.common.ForexApiParser;
import red.mockers.common.Rate;
import red.mockers.common.TrueFXApiParser;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    private ForexApiParser parser = new TrueFXApiParser();

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        RestTemplate restTemplate = new RestTemplate();
        String responseBody = restTemplate.getForObject("http://webrates.truefx.com/rates/connect.html?f=csv", String.class);
        
        parser.parse(responseBody);
        
        while(parser.hasNextRate()) {
            Rate rate = parser.getNextRate();
            log.info(rate.toString());
            restTemplate.postForLocation("http://localhost:8080/rate", rate);
        }        
    }
}