package org.poo.bank.commands;

import org.poo.bank.Bank;
import org.poo.bank.users.User;

public final class RejectSplitPayment implements Command {
    private final Bank bank;
    private final String command;
    private final String email;
    private final String splitPaymentType;
    private final int timestamp;

    public RejectSplitPayment(final Bank bank, final String command, final String email,
                              final String splitPaymentType, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.email = email;
        this.splitPaymentType = splitPaymentType;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        User currentUser = bank.getUser(email);
        if (currentUser == null) {
            bank.errorOccured(timestamp, command, User.NOT_FOUND);
            return;
        }

        SplitPayment payment = currentUser.getFirstSplitPayment(splitPaymentType);
        try {
            payment.refusePayment();
            payment.execute();
        } catch (NullPointerException ignored) {
        }
    }
}
