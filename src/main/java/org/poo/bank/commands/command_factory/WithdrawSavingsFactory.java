package org.poo.bank.commands.command_factory;

import org.poo.bank.Bank;
import org.poo.bank.commands.Command;
import org.poo.bank.commands.WithdrawSavings;
import org.poo.fileio.CommandInput;

public class WithdrawSavingsFactory implements CommandFactory {
    private final Bank bank;
    private final String command;
    private final String account;
    private final double amount;
    private final String currency;
    private final int timestamp;

    public WithdrawSavingsFactory(final Bank bank, final CommandInput input) {
        this.bank = bank;
        command = input.getCommand();
        account = input.getAccount();
        amount = input.getAmount();
        currency = input.getCurrency();
        timestamp = input.getTimestamp();
    }

    @Override
    public Command createCommand() {
        return new WithdrawSavings(bank, command, account, amount, currency, timestamp);
    }
}
