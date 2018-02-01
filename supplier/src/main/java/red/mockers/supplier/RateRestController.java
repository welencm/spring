package red.mockers.supplier;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import red.mockers.common.CubeEntry;
import red.mockers.common.Rate;

@RestController
@RequestMapping("/rate")
public class RateRestController {
    private static final Logger log = LoggerFactory.getLogger(RateRestController.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    private boolean authenticated = false;
    
    @Value("${environment.host}")
    private String environmentHost;
    
    @Value("${environment.login}")
    private String environmentLogin;
    
    @Value("${environment.password}")
    private String environmentPassword;
    
    @Value("${cube.complete}")
    private String cubeComplete;
    
    @Value("${cube.limited}")
    private String cubeLimited;
    
    @Value("${cube.limited.max}")
    private long cubeLimitedMax;
    
    private final RestTemplate restTemplate = new RestTemplate();    
    private HttpHeaders headers;    
    
    private final ConcurrentHashMap<String, AtomicLong> completeGenerators;
    private final ConcurrentHashMap<String, AtomicLong> limitedGenerators;
    
    private Rate lastRate = new Rate(new Date(), "EUR/PLN", 4.1234, 4.4321);

    public RateRestController() {       
        this.completeGenerators = new ConcurrentHashMap<>();
        this.limitedGenerators = new ConcurrentHashMap<>();
    }
    
    @RequestMapping(method = RequestMethod.GET)
    Rate readRate() {
        return lastRate;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> addRate(@RequestBody Rate input) {               
        lastRate = input;
        
        if(!authenticated)
            authenticate();                        
        
        //complete cube entry
        AtomicLong completeGenerator = completeGenerators.get(input.getPair());
        if(completeGenerator == null) {
            completeGenerator = new AtomicLong(1);
            completeGenerators.put(input.getPair(), completeGenerator);
        }
        
        CubeEntry cubeEntry = new CubeEntry(cubeComplete, completeGenerator.incrementAndGet(), input);        
        postCubeEntry(cubeEntry);                
        
        //limited cube entry
        AtomicLong limitedGenerator = limitedGenerators.get(input.getPair());
        if(limitedGenerator == null) {
            limitedGenerator = new AtomicLong(1);
            limitedGenerators.put(input.getPair(), limitedGenerator);
        }  
        
        long id = limitedGenerator.incrementAndGet();
        if(id > cubeLimitedMax) {
            limitedGenerator.set(1);
            id = 1;
        }
        cubeEntry = new CubeEntry(cubeLimited, id, input);
        postCubeEntry(cubeEntry);                
                
        return ResponseEntity.accepted().build();
    }
    
    private String postCubeEntry(CubeEntry cubeEntry) {
        HttpEntity<CubeEntry> request = new HttpEntity<>(cubeEntry, headers);        
        log.info(cubeEntry.toString());
        String response = restTemplate.postForObject(environmentHost + "/api/cube/import", request, String.class);
        return response;
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
