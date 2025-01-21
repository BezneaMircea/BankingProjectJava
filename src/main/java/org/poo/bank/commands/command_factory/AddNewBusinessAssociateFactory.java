package org.poo.bank.commands.command_factory;

import org.poo.bank.Bank;
import org.poo.bank.commands.AddNewBusinessAssociate;
import org.poo.bank.commands.Command;
import org.poo.fileio.CommandInput;

public final class AddNewBusinessAssociateFactory implements CommandFactory {
    private final Bank bank;
    private final String command;
    private final String role;
    private final String email;
    private final int timestamp;

    public AddNewBusinessAssociateFactory(final Bank bank, final CommandInput input) {
        this.bank = bank;
        command = input.getCommand();
        role = input.getRole();
        email = input.getEmail();
        timestamp = input.getTimestamp();
    }

    @Override
    public Command createCommand() {
        return new AddNewBusinessAssociate(bank, command, role, email, timestamp);
    }
}
