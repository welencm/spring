package red.mockers.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Rate {
    private final static String DATE_FORMAT = "yyyyMMdd-HHmmss";
       
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private Date date;       
    private String pair;
    private double bid;
    private double ask;  
        
    private Rate(String pair, double bid, double ask) {               
        this.pair = pair;
        this.bid = bid;        
        this.ask = ask;      
    }
    
    public Rate(String date, String pair, double bid, double ask) throws ParseException{
        this(pair, bid, ask);
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        this.date = df.parse(date);
    }

    public Rate(Date date, String pair, double bid, double ask) {               
        this(pair, bid, ask);
        this.date = date;
    }
        
    public Rate (){
    }
    
    @Override
    public String toString() {
        String json = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(this);    
        } catch (JsonProcessingException exception) {
            exception.printStackTrace();
        }
        return json;
    }
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }          
}
