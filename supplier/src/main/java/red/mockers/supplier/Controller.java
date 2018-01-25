package red.mockers.supplier;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    
    @RequestMapping("/data")
    public String data() {
        System.out.println("Data accepted");
        return "Data accepted";
    }
}
