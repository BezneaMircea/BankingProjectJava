package org.poo.commands.command_factory;

import org.poo.bank.Bank;
import org.poo.commands.AddFunds;
import org.poo.commands.Command;
import org.poo.fileio.CommandInput;

public class AddFundsFactory implements CommandFactory{
    private final Bank bank;
    private final String command;
    private final String account;
    private final double amount;
    private final int timestamp;

    public AddFundsFactory(final Bank bank, final CommandInput input) {
        this.bank = bank;
        command = input.getCommand();
        account = input.getAccount();
        amount = input.getAmount();
        timestamp = input.getTimestamp();
    }


    @Override
    public Command createCommand() {
        return new AddFunds(bank, command, account, amount, timestamp);
    }
}
