package org.poo.bank.commands;

import org.poo.bank.Bank;
import org.poo.bank.users.User;

public final class AcceptSplitPayment implements Command {
    private final Bank bank;
    private final String command;
    private final String email;
    private final String paymentType;
    private final int timestamp;

    public AcceptSplitPayment(final Bank bank, final String command,
                              final String email, final String paymentType,
                              final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.email = email;
        this.paymentType = paymentType;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        User currentUser = bank.getUser(email);
        if (currentUser == null) {
            bank.errorOccured(timestamp, command, User.NOT_FOUND);
            return;
        }

        SplitPayment payment = currentUser.getFirstSplitPayment(paymentType);
        try {
            payment.incrementAccountsThatAccepted();
            payment.execute();
        } catch (NullPointerException ignored) {
        }
    }
}
