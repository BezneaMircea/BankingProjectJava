package org.poo.commands;

import org.poo.bank.Bank;

public class DeleteAccount implements Command {
    private final Bank bank;
    private final String command;
    private final String account;
    private final String email;
    private final int timestamp;

    public DeleteAccount(final Bank bank, final String command,
                         final String account, final String email, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.account = account;
        this.email = email;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {

    }
}
