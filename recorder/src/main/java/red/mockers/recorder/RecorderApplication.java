package red.mockers.recorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class RecorderApplication {
    public static void main(String[] args){
        SpringApplication.run(RecorderApplication.class, args);
    }
}
