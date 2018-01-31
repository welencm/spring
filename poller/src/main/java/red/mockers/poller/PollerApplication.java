package red.mockers.poller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PollerApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(PollerApplication.class, args);
    }
}