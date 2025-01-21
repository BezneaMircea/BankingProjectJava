package org.poo.bank.commands.command_factory;

import org.poo.bank.Bank;
import org.poo.bank.commands.ChangeDepositLimit;
import org.poo.bank.commands.Command;
import org.poo.fileio.CommandInput;

public final class ChangeDepositLimitFactory implements CommandFactory {
    private final Bank bank;
    private final String command;
    private final String email;
    private final String account;
    private final double amount;
    private final int timestamp;

    public ChangeDepositLimitFactory(final Bank bank, final CommandInput input) {
        this.bank = bank;
        command = input.getCommand();
        email = input.getEmail();
        account = input.getAccount();
        amount = input.getAmount();
        timestamp = input.getTimestamp();
    }

    @Override
    public Command createCommand() {
        return new ChangeDepositLimit(bank, command, email, account, amount, timestamp);
    }
}
