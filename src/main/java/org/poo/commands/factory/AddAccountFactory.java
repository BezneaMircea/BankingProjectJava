package org.poo.commands.factory;

import org.poo.bank.Bank;
import org.poo.commands.AddAccount;
import org.poo.commands.Command;
import org.poo.fileio.CommandInput;

public class AddAccountFactory implements CommandFactory {
    private final Bank bank;
    private final String command;
    private final String email;
    private final String currency;
    private final String accountType;
    private final int timestamp;
    private final double interestRate;

    public AddAccountFactory(final Bank bank, final CommandInput input) {
        this.bank = bank;
        command = input.getCommand();
        email = input.getEmail();
        currency = input.getCurrency();
        accountType = input.getAccountType();
        timestamp = input.getTimestamp();
        interestRate = input.getInterestRate();
    }

    @Override
    public Command createCommand() {
        return new AddAccount(bank, command, email, currency, accountType, timestamp, interestRate);
    }
}
