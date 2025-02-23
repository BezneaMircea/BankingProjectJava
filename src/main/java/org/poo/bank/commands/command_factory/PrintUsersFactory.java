package org.poo.bank.commands.command_factory;

import org.poo.bank.Bank;
import org.poo.bank.commands.Command;
import org.poo.bank.commands.PrintUsers;
import org.poo.fileio.CommandInput;

public final class PrintUsersFactory implements CommandFactory {
    private final Bank bank;
    private final String command;
    private final int timestamp;


    public PrintUsersFactory(final Bank bank, final CommandInput input) {
        this.bank = bank;
        command = input.getCommand();
        timestamp = input.getTimestamp();
    }

    @Override
    public Command createCommand() {
        return new PrintUsers(bank, command, timestamp);
    }
}
