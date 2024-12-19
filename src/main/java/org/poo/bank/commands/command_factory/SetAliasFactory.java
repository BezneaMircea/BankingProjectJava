package org.poo.bank.commands.command_factory;

import org.poo.bank.Bank;
import org.poo.bank.commands.Command;
import org.poo.bank.commands.SetAlias;
import org.poo.fileio.CommandInput;

public class SetAliasFactory implements CommandFactory {
    private final Bank bank;
    private final String command;
    private final String email;
    private final String alias;
    private final String account;
    private final int timestamp;

    public SetAliasFactory(Bank bank, CommandInput input) {
        this.bank = bank;
        command = input.getCommand();
        email = input.getEmail();
        alias = input.getAlias();
        account = input.getAccount();
        timestamp = input.getTimestamp();
    }

    @Override
    public Command createCommand() {
        return new SetAlias(bank, command, email, alias, account, timestamp);
    }
}
