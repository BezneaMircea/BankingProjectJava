package org.poo.bank.accounts.factory;


import lombok.Getter;
import org.poo.bank.accounts.Account;
import org.poo.bank.accounts.StandardAccount;

@Getter
public class StandardAccountFactory implements AccountFactory {
    private final String ownerEmail;
    private final String currency;
    private final String accountType;

    public StandardAccountFactory(String ownerEmail, String currency, String accountType) {
        this.ownerEmail = ownerEmail;
        this.currency = currency;
        this.accountType = accountType;
    }

    @Override
    public Account createAccount() {
        return new StandardAccount(ownerEmail, currency, accountType);
    }
}
