package org.poo.commands.factory;

import org.poo.bank.Bank;
import org.poo.commands.Command;
import org.poo.commands.Report;
import org.poo.fileio.CommandInput;

public class ReportFactory implements CommandFactory {
    private final Bank bank;
    private final String command;
    private final int startTimestamp;
    private final int endTimestamp;
    private final String account;
    private final int timestamp;

    public ReportFactory(final Bank bank, final CommandInput input) {
        this.bank = bank;
        command = input.getCommand();
        startTimestamp = input.getStartTimestamp();
        endTimestamp = input.getEndTimestamp();
        account = input.getAccount();
        timestamp = input.getTimestamp();
    }

    @Override
    public Command createCommand() {
        return new Report(bank, command, startTimestamp, endTimestamp, account, timestamp);
    }
}
