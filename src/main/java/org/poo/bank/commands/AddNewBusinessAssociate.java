package org.poo.bank.commands;

import org.poo.bank.Bank;

public class AddNewBusinessAssociate implements Command {
    private final Bank bank;
    private final String command;
    private final String role;
    private final String email;
    private final int timestamp;

    public AddNewBusinessAssociate(final Bank bank, final String command, final String role,
                                   final String email, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.role = role;
        this.email = email;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
    }
}
