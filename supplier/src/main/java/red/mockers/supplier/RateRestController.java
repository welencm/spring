package red.mockers.supplier;

import java.util.Date;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import red.mockers.common.Rate;

@RestController
@RequestMapping("/rate")
public class RateRestController {
    
    private Rate lastRate = new Rate(new Date(), "EUR/PLN", 4.1291, 4.135);
    
    @RequestMapping(method = RequestMethod.GET)
    Rate readRate() {
        return lastRate;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> addRate(@RequestBody Rate input) {
        lastRate = input;
        System.out.println(lastRate);
        return ResponseEntity.accepted().build();
    }
}
