package org.poo.bank.commands.command_factory;

import org.poo.bank.Bank;
import org.poo.bank.commands.Command;
import org.poo.bank.commands.AcceptSplitPayment;
import org.poo.fileio.CommandInput;

public final class AcceptSplitPaymentFactory implements CommandFactory {
    private final Bank bank;
    private final String command;
    private final String email;
    private final String paymentType;
    private final int timestamp;

    public AcceptSplitPaymentFactory(final Bank bank, final CommandInput input) {
        this.bank = bank;
        command = input.getCommand();
        email = input.getEmail();
        paymentType = input.getSplitPaymentType();
        timestamp = input.getTimestamp();
    }


    @Override
    public Command createCommand() {
        return new AcceptSplitPayment(bank, command, email, paymentType, timestamp);
    }
}
