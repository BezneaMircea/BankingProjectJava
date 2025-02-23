package org.poo.bank.commands.command_factory;

import org.poo.bank.Bank;
import org.poo.bank.commands.Command;
import org.poo.bank.commands.SetMinBalance;
import org.poo.fileio.CommandInput;

public final class SetMinBalanceFactory implements CommandFactory {
    private final Bank bank;
    private final String command;
    private final double amount;
    private final String account;
    private final int timestamp;

    public SetMinBalanceFactory(final Bank bank, final CommandInput input) {
        this.bank = bank;
        command = input.getCommand();
        amount = input.getAmount();
        account = input.getAccount();
        timestamp = input.getTimestamp();
    }

    @Override
    public Command createCommand() {
        return new SetMinBalance(bank, command, amount, account, timestamp);
    }
}
