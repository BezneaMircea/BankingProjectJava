package org.poo.bank.accounts.accountfactory;


import lombok.Getter;
import org.poo.bank.accounts.Account;
import org.poo.bank.accounts.AccountInput;
import org.poo.bank.accounts.StandardAccount;

/**
 * Class used to represent the economyAccountFactory
 */
@Getter
public class StandardAccountFactory implements AccountFactory {
    private final String ownerEmail;
    private final String currency;
    private final Account.Type accountType;

    /**
     * Constructor for the EconomyAccountFactory
     */
    public StandardAccountFactory(AccountInput input) {
        ownerEmail = input.getOwnerEmail();
        currency = input.getCurrency();
        accountType = input.getAccountType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account createAccount() {
        return new StandardAccount(ownerEmail, currency, accountType);
    }
}
