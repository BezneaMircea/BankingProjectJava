package org.poo.commands.factory;

import org.poo.bank.Bank;
import org.poo.commands.ChangeInterestRate;
import org.poo.commands.Command;
import org.poo.fileio.CommandInput;

public class ChangeInterestRateFactory implements CommandFactory {
    private final Bank bank;
    private final String command;
    private final String account;
    private final double interestRate;
    private final int timestamp;

    public ChangeInterestRateFactory(final Bank bank, final CommandInput input) {
        this.bank = bank;
        command = input.getCommand();
        account = input.getAccount();
        interestRate = input.getInterestRate();
        timestamp = input.getTimestamp();
    }

    @Override
    public Command createCommand() {
        return new ChangeInterestRate(bank, command, account, interestRate, timestamp);
    }
}
