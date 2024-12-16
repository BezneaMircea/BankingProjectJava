package org.poo.commands.transactions.transactionsfactory;

import org.poo.commands.transactions.CheckCardStatusTransaction;
import org.poo.commands.transactions.Transaction;
import org.poo.commands.transactions.TransactionInput;

public class CheckCardStatusTransactionFactory implements TransactionFactory {
    private final int timestamp;
    private final String description;

    public CheckCardStatusTransactionFactory(TransactionInput input) {
        timestamp = input.getTimestamp();
        description = input.getDescription();
    }

    @Override
    public Transaction createTransaction() {
        return new CheckCardStatusTransaction(timestamp, description);
    }
}
