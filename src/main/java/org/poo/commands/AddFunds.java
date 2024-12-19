package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;

/**
 * Class used to represent the addFunds command
 */
public final class AddFunds implements Command {
    private final Bank bank;
    private final String command;
    private final String account;
    private final double amount;
    private final int timestamp;

    /**
     * Constructor for the AddFunds class
     * @param bank the receiver bank of the command
     * @param command the command name
     * @param account the account to add funds to
     * @param amount the ammount of money to add
     * @param timestamp the timestamp of the command
     */
    public AddFunds(final Bank bank, final String command,
                    final String account, final double amount, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.account = account;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        Account accountToAddFunds = bank.getAccount(account);
        if (accountToAddFunds == null)
            return;

        accountToAddFunds.setBalance(accountToAddFunds.getBalance() + amount);
    }
}
