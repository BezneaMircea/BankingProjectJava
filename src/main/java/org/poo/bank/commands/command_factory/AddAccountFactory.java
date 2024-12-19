package org.poo.bank.commands.command_factory;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.commands.AddAccount;
import org.poo.bank.commands.Command;
import org.poo.fileio.CommandInput;

public class AddAccountFactory implements CommandFactory {
    private final Bank bank;
    private final String command;
    private final String email;
    private final String currency;
    private final Account.Type accountType;
    private final int timestamp;
    private final double interestRate;

    public AddAccountFactory(final Bank bank, final CommandInput input) {
        this.bank = bank;
        command = input.getCommand();
        email = input.getEmail();
        currency = input.getCurrency();
        accountType = Account.Type.fromString(input.getAccountType());
        timestamp = input.getTimestamp();
        interestRate = input.getInterestRate();
    }

    @Override
    public Command createCommand() {
        return new AddAccount(bank, command, email, currency, accountType, timestamp, interestRate);
    }
}
