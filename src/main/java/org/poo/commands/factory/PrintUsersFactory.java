package org.poo.commands.factory;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bank.Bank;
import org.poo.commands.Command;
import org.poo.commands.PrintUsers;
import org.poo.fileio.CommandInput;

public class PrintUsersFactory implements CommandFactory {
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
