package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;

public class SetMinBalance implements Command {
    private final Bank bank;
    private final String command;
    private final double amount;
    private final String account;
    private final int timestamp;

    public SetMinBalance(final Bank bank, final String command,
                         final double amount, final String account, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.amount = amount;
        this.account = account;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        Account targetedAccount = bank.getIbanToAccount().get(account);
        if (targetedAccount == null)
            return;
        
        targetedAccount.setMinBalance(amount);
    }
}
