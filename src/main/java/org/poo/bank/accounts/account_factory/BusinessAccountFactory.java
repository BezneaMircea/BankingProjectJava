package org.poo.bank.accounts.account_factory;

import org.poo.bank.accounts.Account;
import org.poo.bank.accounts.AccountInput;
import org.poo.bank.accounts.BusinessAccount;

public class BusinessAccountFactory implements AccountFactory {
    private final String ownerEmail;
    private final String currency;
    private final Account.Type accountType;
    private final double spendingLimit;
    private final double depositLimit;

    public BusinessAccountFactory(final AccountInput input) {
        ownerEmail = input.getOwnerEmail();
        currency = input.getCurrency();
        accountType = input.getAccountType();
        spendingLimit = input.getSpendingLimit();
        depositLimit = input.getDepositLimit();
    }

    @Override
    public Account createAccount() {
        return new BusinessAccount(ownerEmail, currency, accountType, spendingLimit, depositLimit);
    }
}
