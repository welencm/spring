package red.mockers.stocker;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {
    private String symbol;
    private String companyName;
    private double latestPrice;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm:ss a")
    private Date latestTime;
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    public Quote() {
        
    }
    
    @Override
    public String toString() {
        String json = null;
        try {            
            json = mapper.writeValueAsString(this);    
        } catch (JsonProcessingException exception) {
            exception.printStackTrace();
        }
        return json;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(double latestPrice) {
        this.latestPrice = latestPrice;
    }

    public Date getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(Date latestTime) {
        this.latestTime = latestTime;
    }      
}
