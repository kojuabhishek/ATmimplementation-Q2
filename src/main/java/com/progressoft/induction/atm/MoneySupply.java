package com.progressoft.induction.atm;

import com.progressoft.induction.atm.Banknote;
import com.progressoft.induction.atm.exceptions.NotEnoughMoneyInATMException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class MoneySupply {
    private EnumMap<Banknote, Integer> atmCashMap = new EnumMap<>(Banknote.class);

    public MoneySupply() {

        atmCashMap.put(Banknote.FIFTY_JOD, 10);
        atmCashMap.put(Banknote.TWENTY_JOD, 20);
        atmCashMap.put(Banknote.TEN_JOD, 100);
        atmCashMap.put(Banknote.FIVE_JOD, 100);
    }

    public boolean canDispense(BigDecimal amount) {
        BigDecimal lowestDenominationAmount = amount.multiply(BigDecimal.valueOf(100));

 
        return canDispenseAmount(lowestDenominationAmount);
    }

    private boolean canDispenseAmount(BigDecimal amount) {
    
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            return true;
        }

    
        for (Banknote banknote : Banknote.values()) {
            int availableNotes = atmCashMap.get(banknote);
            BigDecimal value = banknote.getValue();

            if (amount.compareTo(value) >= 0 && availableNotes > 0) {
               
                atmCashMap.put(banknote, availableNotes - 1);
                BigDecimal remainingAmount = amount.subtract(value);
                if (canDispenseAmount(remainingAmount)) {
                    return true;
                }
               
                atmCashMap.put(banknote, availableNotes);
            }
        }

        return false;
    }

    public List<Banknote> dispense(BigDecimal amount) throws NotEnoughMoneyInATMException {
        BigDecimal lowestDenominationAmount = amount.multiply(BigDecimal.valueOf(100));

        if (!canDispenseAmount(lowestDenominationAmount)) {
            throw new NotEnoughMoneyInATMException("ATM does not have enough funds to complete the withdrawal");
        }


        List<Banknote> dispensedBanknotes = new ArrayList<>();
        for (Banknote banknote : Banknote.values()) {
            int availableNotes = atmCashMap.get(banknote);
            BigDecimal value = banknote.getValue();

            while (amount.compareTo(value) >= 0 && availableNotes > 0) {
                dispensedBanknotes.add(banknote);
                amount = amount.subtract(value);
                availableNotes--;
            }
        }

        return dispensedBanknotes;
    }


    public void updateMoneySupply(List<Banknote> dispensedBanknotes) {
  
        for (Banknote banknote : dispensedBanknotes) {
            atmCashMap.put(banknote, atmCashMap.get(banknote) - 1);
        }
    }
}

