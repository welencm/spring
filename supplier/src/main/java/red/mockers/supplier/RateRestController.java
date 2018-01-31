package red.mockers.supplier;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
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
    private boolean authenticated = false;
    
    @Value("${environment.host}")
    private String environmentHost;
    
    @Value("${environment.login}")
    private String environmentLogin;
    
    @Value("${environment.password}")
    private String environmentPassword;
    
    @Value("${cube.complete}")
    private String cubeComplete;
    
    @Value("${cube.recent}")
    private String cubeRecent;
    
    private final RestTemplate restTemplate = new RestTemplate();    
    private HttpHeaders headers;    
    private final ConcurrentHashMap<String, AtomicLong> idGenerators;
    
    private Rate lastRate = new Rate(new Date(), "EUR/PLN", 4.1234, 4.4321);

    public RateRestController() {       
        this.idGenerators = new ConcurrentHashMap<>();

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
                
        AtomicLong idGenerator = idGenerators.get(input.getPair());
        if(idGenerator == null) {
            idGenerator = new AtomicLong();
            idGenerators.put(input.getPair(), idGenerator);
        }
        
        CubeEntry cubeEntry = new CubeEntry(cubeComplete, idGenerator.incrementAndGet(), input);
        
        System.out.println(cubeEntry);
        HttpEntity<CubeEntry> request = new HttpEntity<>(cubeEntry, headers);
        
        String response = restTemplate.postForObject(environmentHost + "/api/cube/import", request, String.class);
        System.out.println(response);
        
        return ResponseEntity.accepted().build();
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
