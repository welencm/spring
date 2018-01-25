package red.mockers.poller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PollerApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(PollerApplication.class);
    }
}