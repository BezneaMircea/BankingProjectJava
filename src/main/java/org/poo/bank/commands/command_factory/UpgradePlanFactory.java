package org.poo.bank.commands.command_factory;

import org.poo.bank.Bank;
import org.poo.bank.commands.Command;
import org.poo.bank.commands.UpgradePlan;
import org.poo.fileio.CommandInput;

public final class UpgradePlanFactory implements CommandFactory {
    private final Bank bank;
    private final String command;
    private final String newPlanType;
    private final String accountIban;
    private final int timestamp;

    public UpgradePlanFactory(final Bank bank, final CommandInput input) {
        this.bank = bank;
        command = input.getCommand();
        newPlanType = input.getNewPlanType();
        accountIban = input.getAccount();
        timestamp = input.getTimestamp();
    }

    @Override
    public Command createCommand() {
        return new UpgradePlan(bank, command, newPlanType, accountIban, timestamp);
    }
}
