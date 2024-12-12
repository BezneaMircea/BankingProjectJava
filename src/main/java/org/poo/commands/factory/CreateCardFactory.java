package org.poo.commands.factory;

import org.poo.bank.Bank;
import org.poo.commands.Command;
import org.poo.commands.CreateCard;
import org.poo.fileio.CommandInput;

public class CreateCardFactory implements CommandFactory {
    private final Bank bank;
    private final String command;
    private final String account;
    private final String email;
    private final int timestamp;

    public CreateCardFactory(final Bank bank, final CommandInput input) {
        this.bank = bank;
        command = input.getCommand();
        account = input.getAccount();
        email = input.getEmail();
        timestamp = input.getTimestamp();
    }

    @Override
    public Command createCommand() {
        return new CreateCard(bank, command, account, email, timestamp);
    }
}
