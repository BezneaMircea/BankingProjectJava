package org.poo.bank.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;

/**
 * Class used to represent SetMinBalance command
 */
public final class SetMinBalance implements Command {
    private final Bank bank;
    private final String command;
    private final double amount;
    private final String account;
    private final int timestamp;

    /**
     * Constructor for the setMinBalance command
     * @param bank the receiver bank of the command
     * @param command the command name
     * @param amount the min balance to set on the account
     * @param account IBAN of the account to set min balance to
     * @param timestamp timestamp of the command
     */
    public SetMinBalance(final Bank bank, final String command,
                         final double amount, final String account, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.amount = amount;
        this.account = account;
        this.timestamp = timestamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        Account targetedAccount = bank.getAccount(account);
        if (targetedAccount == null)
            return;
        
        targetedAccount.setMinBalance(amount);
    }
}
