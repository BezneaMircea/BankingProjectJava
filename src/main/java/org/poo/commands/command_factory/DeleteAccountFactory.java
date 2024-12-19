package org.poo.commands.command_factory;

import org.poo.bank.Bank;
import org.poo.commands.Command;
import org.poo.commands.DeleteAccount;
import org.poo.fileio.CommandInput;

public class DeleteAccountFactory implements CommandFactory{
    private final Bank bank;
    private final String command;
    private final String account;
    private final String email;
    private final int timestamp;

    public DeleteAccountFactory(Bank bank, CommandInput input) {
        this.bank = bank;
        command = input.getCommand();
        account = input.getAccount();
        email = input.getEmail();
        timestamp = input.getTimestamp();
    }

    @Override
    public Command createCommand() {
        return new DeleteAccount(bank, command, account, email, timestamp);
    }
}
