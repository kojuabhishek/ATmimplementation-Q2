/*
package com.progressoft.induction.atm;

import com.progressoft.induction.atm.Impl.ATMImpl;
import com.progressoft.induction.atm.Impl.BankingSystemImpl;
import com.progressoft.induction.atm.exceptions.AccountNotFoundException;

import java.math.BigDecimal;
import java.util.*;

public class Main {
    public static void main(String args[]){
       // your code here
    }
}*/

package com.progressoft.induction.atm;

import com.progressoft.induction.atm.Impl.ATMImpl;
import com.progressoft.induction.atm.Impl.BankingSystemImpl;
import com.progressoft.induction.atm.exceptions.AccountNotFoundException;
import com.progressoft.induction.atm.exceptions.InsufficientFundsException;
import com.progressoft.induction.atm.exceptions.NotEnoughMoneyInATMException;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String args[]) {
    
        ATM atm = new ATMImpl();
        BankingSystem bankingSystem = new BankingSystemImpl();


        try {
         
            atm.withdraw("14141414141", new BigDecimal("120.0"));
        } catch (AccountNotFoundException e) {
            System.out.println("Account not found: " + e.getMessage());
        }

        try {

            atm.withdraw("123456789", new BigDecimal("20000.0"));
        } catch (InsufficientFundsException e) {
            System.out.println("Insufficient funds: " + e.getMessage());
        }

        try {
           
            atm.withdraw("123456789", new BigDecimal("1000.0"));
           

        } catch (NotEnoughMoneyInATMException e) {
            System.out.println("Not enough money in ATM: " + e.getMessage());
        }


        BigDecimal requestedAmount = new BigDecimal(700);
        List<Banknote> receivedBanknotes = atm.withdraw("111111111", requestedAmount);
        BigDecimal sumOfAllBanknotes = receivedBanknotes.stream().map(Banknote::getValue).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        if (sumOfAllBanknotes.compareTo(requestedAmount) == 0) {
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Withdrawal failed: Sum of received banknotes does not match the requested amount.");
        }

        try {
           
         
            atm.withdraw("222222222", new BigDecimal("500"));
        } catch (InsufficientFundsException e) {
            System.out.println("Insufficient funds: " + e.getMessage());
        }
    }
}

