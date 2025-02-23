package org.poo.bank.commands.command_factory;

import org.poo.bank.Bank;
import org.poo.bank.commands.AddInterest;
import org.poo.bank.commands.Command;
import org.poo.fileio.CommandInput;

public final class AddInterestFactory implements CommandFactory {
    private final Bank bank;
    private final String command;
    private final String account;
    private final int timestamp;

    public AddInterestFactory(final Bank bank, final CommandInput input) {
        this.bank = bank;
        command = input.getCommand();
        account = input.getAccount();
        timestamp = input.getTimestamp();
    }

    @Override
    public Command createCommand() {
        return new AddInterest(bank, command, account, timestamp);
    }
}
