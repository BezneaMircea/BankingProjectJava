package org.poo.bank.accounts.accountfactory;

import org.poo.bank.accounts.Account;
import org.poo.bank.accounts.AccountInput;
import org.poo.bank.accounts.EconomyAccount;

/**
 * Class used to represent the economyAccountFactory
 */
public class EconomyAccountFactory implements AccountFactory {
    private final String ownerEmail;
    private final String currency;
    private final Account.Type accountType;
    private final double interestRate;

    /**
     * Constructor for the EconomyAccountFactory
     */
    public EconomyAccountFactory(AccountInput input) {
        ownerEmail = input.getOwnerEmail();
        currency = input.getCurrency();
        accountType = input.getAccountType();
        interestRate = input.getInterestRate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account createAccount() {
        return new EconomyAccount(ownerEmail, currency, accountType, interestRate);
    }
}
