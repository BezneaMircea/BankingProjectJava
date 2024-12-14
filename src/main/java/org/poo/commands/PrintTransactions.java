package org.poo.commands;

import org.poo.bank.Bank;

public class PrintTransactions implements Command {
    private final Bank bank;
    private final String email;
    private final int timestamp;

    public PrintTransactions(Bank bank, String email, int timestamp) {
        this.bank = bank;
        this.email = email;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        
    }
}
