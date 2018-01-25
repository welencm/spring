package red.mockers.poller;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.springframework.web.client.RestTemplate;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        RestTemplate restTemplate = new RestTemplate();
        
        String text = restTemplate.getForObject("http://webrates.truefx.com/rates/connect.html?f=csv", String.class);        
        log.info(text);
        restTemplate.getForObject("http://localhost:8080/data", String.class);
    }
}