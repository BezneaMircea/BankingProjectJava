package org.poo.commands.transactions.transactionsfactory;

import org.poo.commands.transactions.PayOnlineTransaction;
import org.poo.commands.transactions.Transaction;
import org.poo.commands.transactions.TransactionInput;

public class PayOnlineTransactionFactory implements TransactionFactory {
    private final int timestamp;
    private final String description;
    private final double amount;
    private final String commerciant;
    private final String error;

    public PayOnlineTransactionFactory(TransactionInput input) {
        timestamp = input.getTimestamp();
        description = input.getDescription();
        amount = input.getAmount();
        commerciant = input.getCommerciant();
        error = input.getError();
    }

    @Override
    public Transaction createTransaction() {
        return new PayOnlineTransaction(timestamp, description, amount, commerciant, error);
    }
}
