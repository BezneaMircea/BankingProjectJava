package org.poo.commands.factory;

import org.poo.bank.Bank;
import org.poo.commands.Command;
import org.poo.commands.PrintTransactions;
import org.poo.fileio.CommandInput;

public class PrintTransactionsFactory implements CommandFactory {
    private final Bank bank;
    private final String email;
    private final int timestamp;

    public PrintTransactionsFactory(final Bank bank, final CommandInput input) {
        this.bank = bank;
        email = input.getEmail();
        timestamp = input.getTimestamp();
    }

    @Override
    public Command createCommand() {
        return new PrintTransactions(bank, email, timestamp);
    }
}
