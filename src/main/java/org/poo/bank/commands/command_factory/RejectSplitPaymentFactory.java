package org.poo.bank.commands.command_factory;

import org.poo.bank.Bank;
import org.poo.bank.commands.Command;
import org.poo.bank.commands.RejectSplitPayment;
import org.poo.fileio.CommandInput;

public final class RejectSplitPaymentFactory implements CommandFactory {
    private final Bank bank;
    private final String command;
    private final String email;
    private final String splitPaymentType;
    private final int timestamp;

    public RejectSplitPaymentFactory(final Bank bank, final CommandInput input) {
        this.bank = bank;
        command = input.getCommand();
        email = input.getEmail();
        splitPaymentType = input.getSplitPaymentType();
        timestamp = input.getTimestamp();
    }

    @Override
    public Command createCommand() {
        return new RejectSplitPayment(bank, command, email, splitPaymentType, timestamp);
    }
}
