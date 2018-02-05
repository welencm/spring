package red.mockers.banker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankerApplication {
    
    private static final Logger logger = LoggerFactory.getLogger(BankerApplication.class);
        
    public static void main(String[] args) {
        SpringApplication.run(BankerApplication.class, args);
    }
}
