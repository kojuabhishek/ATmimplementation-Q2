package com.progressoft.induction.atm.Impl;

import com.progressoft.induction.atm.ATM;
import com.progressoft.induction.atm.Banknote;
import com.progressoft.induction.atm.MoneySupply;
import com.progressoft.induction.atm.exceptions.AccountNotFoundException;
import com.progressoft.induction.atm.exceptions.InsufficientFundsException;
import com.progressoft.induction.atm.exceptions.NotEnoughMoneyInATMException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ATMImpl implements ATM {
    private final BankingSystemImpl bankingSystem=new BankingSystemImpl();
    private final MoneySupply moneySupply = new MoneySupply(); 
    
    
    @Override
    public List<Banknote> withdraw(String accountNumber, BigDecimal amount) {
        // Your code here
        //return null;
    	 BigDecimal accountBalance = bankingSystem.getAccountBalance(accountNumber);

        
         if (accountBalance == null) {
             throw new AccountNotFoundException("Account not found");
         }

    
         if (accountBalance.compareTo(amount) < 0) {
             throw new InsufficientFundsException("Insufficient funds in the account");
         }

     
         if (!moneySupply.canDispense(amount)) {
             throw new NotEnoughMoneyInATMException("ATM does not have enough funds to complete the withdrawal");
         }

        
         bankingSystem.debitAccount(accountNumber, amount);

      
         List<Banknote> dispensedBanknotes = moneySupply.dispense(amount);

         return dispensedBanknotes;
    }

    @Override
    public BigDecimal checkBalance(String accountNumber) {
        return bankingSystem.getAccountBalance(accountNumber);
    }
}
