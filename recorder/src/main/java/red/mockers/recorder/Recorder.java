package red.mockers.recorder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Recorder {
    RestTemplate restTemplate = new RestTemplate();
    BufferedWriter quoteWriter;

    public Recorder() throws IOException {
        quoteWriter = new BufferedWriter(new FileWriter("stock.quotes", true)); 
    }     
    
    @Scheduled(fixedDelayString = "${polling.interval}")
    public void poll() throws IOException {
        String[] symbols = {"aapl", "msft", "ibm", "snap", "fb", "tri", "ge", "googl", "hpq", "nok", "amzn", "ford", "gm"};
        for(String symbol : symbols) {
            String responseBody = restTemplate.getForObject("https://ws-api.iextrading.com/1.0/stock/" + symbol + "/quote", String.class);
            System.out.println(responseBody);

            quoteWriter.append(responseBody);
            quoteWriter.append("\n");
            quoteWriter.flush();
        }
    }
}
