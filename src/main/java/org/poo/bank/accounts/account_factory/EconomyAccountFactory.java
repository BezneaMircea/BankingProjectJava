package org.poo.bank.accounts.account_factory;

import org.poo.bank.accounts.Account;
import org.poo.bank.accounts.AccountInput;
import org.poo.bank.accounts.EconomyAccount;

/**
 * Class used to represent the economyAccountFactory
 */
public final class EconomyAccountFactory implements AccountFactory {
    private final String ownerEmail;
    private final String currency;
    private final Account.Type accountType;
    private final double interestRate;

    /**
     * Constructor for the EconomyAccountFactory
     */
    public EconomyAccountFactory(final AccountInput input) {
        ownerEmail = input.getOwnerEmail();
        currency = input.getCurrency();
        accountType = input.getAccountType();
        interestRate = input.getInterestRate();
    }

    @Override
    public Account createAccount() {
        return new EconomyAccount(ownerEmail, currency, accountType, interestRate);
    }
}
