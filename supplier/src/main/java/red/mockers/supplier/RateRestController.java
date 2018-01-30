package red.mockers.supplier;

import java.util.Date;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicLong;
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
        
    RestTemplate restTemplate = new RestTemplate();
    AuthenticationData authenticationData;
    HttpHeaders headers;
    
    private Rate lastRate = new Rate(new Date(), "EUR/PLN", 4.1291, 4.135);
   
    private Hashtable<String, AtomicLong> idGenerators = new Hashtable<String, AtomicLong>();
        
    public RateRestController() {
        authenticate();
        headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", authenticationData.getToken());
    }
    
    @RequestMapping(method = RequestMethod.GET)
    Rate readRate() {
        return lastRate;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> addRate(@RequestBody Rate input) {
        lastRate = input;
        System.out.println(lastRate);
        
        AtomicLong idGenerator = idGenerators.get(input.getPair());
        if(idGenerator == null) {
            idGenerator = new AtomicLong();
            idGenerators.put(input.getPair(), idGenerator);
        }
        
        CubeEntry cubeEntry = new CubeEntry("supplier1", idGenerator.incrementAndGet(), input);
        
        System.out.println(cubeEntry);
        HttpEntity<CubeEntry> request = new HttpEntity<>(cubeEntry, headers);
        
        String response = restTemplate.postForObject("http://rep-red-mockers-m2.qa.ffdc.tradingbell.men/api/cube/import", request, String.class);
        System.out.println(response);
        
        return ResponseEntity.accepted().build();
    }
    
    private void authenticate() {
        Credentials credentials = new Credentials("finastra", "198a1a7412a3");        
        authenticationData = restTemplate.postForObject("http://rep-red-mockers-m2.qa.ffdc.tradingbell.men/api/cargo/oauth/auth", credentials, AuthenticationData.class);               
    }
}
