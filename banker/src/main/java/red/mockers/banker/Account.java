package red.mockers.banker;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Account {
    private String id;
    private String currency;
    private double amount;

    public Account(String id, String currency, double amount) {
        this.id = id;
        this.currency = currency;
        this.amount = amount;
    }
    
    private void round() {
        amount = new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
    
    public void add(double amount) {
        this.amount += amount;
        round();
    }

    public void sub(double amount) {
        this.amount -= amount;
        round();
    }
    
    public Account() {
        
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
