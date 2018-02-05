package red.mockers.banker;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Banker {
       
    private Account plnAccount = new Account("890672560720523647", "PLN", 50000.0d);
    private Account eurAccount = new Account("946196021636549184", "EUR", 3000.0d);
    
    @RequestMapping(value = "/account/890672560720523647/balance", method = RequestMethod.GET)
    Account getPlnAccount() {
        return plnAccount;
    }
    
    @RequestMapping(value = "/account/946196021636549184/balance", method = RequestMethod.GET)
    Account getEurAccount() {
        return eurAccount;
    }
    
    @RequestMapping(value = "/transaction", method = RequestMethod.POST)
    ResponseEntity<?> transaction(@RequestBody Transaction transaction) {
        Double transactionAmount = transaction.getAmount();
        boolean hasFunds = true;
                       
        if(transactionAmount >= 0) {
            if (plnAccount.getAmount() < (transactionAmount * transaction.getRate()))
                hasFunds = false;
        }
        else {
            if (eurAccount.getAmount() < -1.0d * transactionAmount)
                hasFunds = false;
        }
        
        if(hasFunds) {
            plnAccount.sub(transaction.getAmount() * transaction.getRate());
            eurAccount.add(transaction.getAmount());
            return ResponseEntity.accepted().build();
        }
        else
            return ResponseEntity.status(403).build();
    }
    
    @RequestMapping(value = "/reset")
    String reset() {
        plnAccount = new Account("890672560720523647", "PLN", 50000.0d);
        eurAccount = new Account("946196021636549184", "EUR", 3000.0d);
        return "OK";
    }
}
