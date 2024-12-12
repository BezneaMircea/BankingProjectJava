package org.poo.bank.accounts.factory;

import org.poo.bank.accounts.Account;
import org.poo.bank.accounts.EconomyAccount;


public class EconomyAccountFactory extends StandardAccountFactory {
    private final double interestRate;

    public EconomyAccountFactory(String ownerEmail, String currency, String accountType, double interestRate) {
        super(ownerEmail, currency, accountType);
        this.interestRate = interestRate;
    }

    @Override
    public Account createAccount() {
        return new EconomyAccount(getOwnerEmail(), getCurrency(), getAccountType(), interestRate);
    }
}
