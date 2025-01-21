package org.poo.bank.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.accounts.AccountInput;
import org.poo.bank.accounts.BusinessAccount;
import org.poo.bank.users.User;

public class ChangeDepositLimit implements Command {
    private final Bank bank;
    private final String command;
    private final String email;
    private final String account;
    private final double amount;
    private final int timestamp;

    public ChangeDepositLimit(final Bank bank, final String command, final String email,
                              final String account, final double amount, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.email = email;
        this.account = account;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        Account businessAccount = bank.getAccount(account);
        User owner = bank.getUser(email);

        String error = null;
        if (owner == null) {
            error = User.NOT_FOUND;
        }

        if (!businessAccount.getOwnerEmail().equals(email)) {
            error = BusinessAccount.NOT_AUTHORIZED;
        }

        if (error != null) {
            bank.errorOccured(timestamp, command, error);
            return;
        }

       businessAccount.setDepositLimit(amount);
    }
}
