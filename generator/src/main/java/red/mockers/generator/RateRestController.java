package red.mockers.generator;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import red.mockers.common.Rate;

@RestController
@RequestMapping("/rate")
public class RateRestController {
    
    private ExchangeCourseGenerator gen = new ExchangeCourseGenerator("PLN/EUR", 4.2, 0.3, 10);
    
    @RequestMapping(method = RequestMethod.GET)
    Rate readRate() {
        return gen.getNextRate();
    }
}
