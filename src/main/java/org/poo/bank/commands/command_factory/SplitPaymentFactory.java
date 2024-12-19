package org.poo.bank.commands.command_factory;

import org.poo.bank.Bank;
import org.poo.bank.commands.Command;
import org.poo.bank.commands.SplitPayment;
import org.poo.fileio.CommandInput;

import java.util.List;

public final class SplitPaymentFactory implements CommandFactory {
    private final Bank bank;
    private final String command;
    private final List<String> accountsForSplit;
    private final int timestamp;
    private final String currency;
    private final double amount;

    public SplitPaymentFactory(final Bank bank, final CommandInput input) {
        this.bank = bank;
        command = input.getCommand();
        accountsForSplit = input.getAccounts();
        timestamp = input.getTimestamp();
        currency = input.getCurrency();
        amount = input.getAmount();
    }

    @Override
    public Command createCommand() {
        return new SplitPayment(bank, command, accountsForSplit, timestamp, currency, amount);
    }
}
