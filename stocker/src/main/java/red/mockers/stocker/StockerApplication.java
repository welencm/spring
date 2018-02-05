package red.mockers.stocker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StockerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockerApplication.class, args);
    }   
}
