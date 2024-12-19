package org.poo.bank.accounts.factory;

import org.poo.bank.accounts.Account;
import org.poo.bank.accounts.EconomyAccount;

/**
 * Class used to represent the economyAccountFactory
 */
public class EconomyAccountFactory extends StandardAccountFactory {
    private final double interestRate;

    /**
     * Constructor for the EconomyAccountFactory
     */
    public EconomyAccountFactory(String ownerEmail, String currency, String accountType, double interestRate) {
        super(ownerEmail, currency, accountType);
        this.interestRate = interestRate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account createAccount() {
        return new EconomyAccount(getOwnerEmail(), getCurrency(), getAccountType(), interestRate);
    }
}
