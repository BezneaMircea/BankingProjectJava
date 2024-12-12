package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;

public class AddFunds implements Command {
    private final Bank bank;
    private final String command;
    private final String account;
    private final double amount;
    private final int timestamp;
    public AddFunds(final Bank bank, final String command,
                         final String account, final double amount, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.account = account;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        Account accountToAddFunds = bank.getIbanToAccount().get(account);
        if (accountToAddFunds == null)
            return;

        accountToAddFunds.setBalance(accountToAddFunds.getBalance() + amount);
    }
}
