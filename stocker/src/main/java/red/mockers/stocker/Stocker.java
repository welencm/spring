package red.mockers.stocker;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Component
@RestController
public class Stocker {
    private static final Logger log = LoggerFactory.getLogger(Stocker.class);
    
    @Value("${environment.host}")
    private String environmentHost;
    
    @Value("${environment.login}")
    private String environmentLogin;

    @Value("${environment.password}")
    private String environmentPassword;
    
    @Value("${stock.cube}")
    private String stockCube;

    @Value("${stock.cube.max}")
    private long stockCubeMax;
    
    private boolean authenticated = false;
    private final RestTemplate restTemplate = new RestTemplate();    
    private HttpHeaders headers;
    
    private BufferedReader reader;
    private ObjectMapper objectMapper = new ObjectMapper();
    
    private final ConcurrentHashMap<String, AtomicLong> limitedGenerators;
    
    public Stocker() throws IOException {
        reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/stock.quotes")));        
        this.limitedGenerators = new ConcurrentHashMap<>();
    }
     
//    private int tickNumber = 0;
//    private int maxTickNumber = 500;
    
    @Scheduled(fixedDelay = 10000)
    public void tick() throws IOException {
        if(!authenticated)
            authenticate();  

        readAndPost();
        
//        tickNumber++;
//        log.info(Integer.toString(tickNumber));
//        if(tickNumber <= maxTickNumber) {            
//            readAndPost();
//        }
//        else {
//            log.info("Max tick reached");
//        }
    }

    private void readAndPost() throws IOException {
        Date readDate = new Date();
        for(int i = 0; i<13; i++) {
            Quote quote = readQuote();
            quote.setLatestTime(readDate);
            log.info("Read: " + quote.toString());

            AtomicLong limitedGenerator = limitedGenerators.get(quote.getSymbol());
            if(limitedGenerator == null) {
                limitedGenerator = new AtomicLong();
                limitedGenerators.put(quote.getSymbol(), limitedGenerator);
            }  

            long id = limitedGenerator.incrementAndGet();
            if(id > stockCubeMax) {
                limitedGenerator.set(1);
                id = limitedGenerator.get();
            }               
            StockCubeEntry cubeEntry = new StockCubeEntry(stockCube, id, quote);
            postStockCubeEntry(cubeEntry);           
        }
    }

    private String postStockCubeEntry(StockCubeEntry cubeEntry) {
        HttpEntity<StockCubeEntry> request = new HttpEntity<>(cubeEntry, headers);        
        log.info("Send: " + cubeEntry.toString());        
        String response = restTemplate.postForObject(environmentHost + "/api/cube/import", request, String.class);
        return response;
    }
    
    private Quote readQuote() throws IOException {
        return objectMapper.readValue(reader.readLine(), Quote.class);
    }
    
    private void authenticate() {
        Credentials credentials = new Credentials(environmentLogin, environmentPassword);
        AuthenticationData authenticationData = restTemplate.postForObject(environmentHost + "/api/cargo/oauth/auth", credentials, AuthenticationData.class);
        headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", authenticationData.getToken());
        authenticated = true;
    }
}
