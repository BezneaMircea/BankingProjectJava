package org.poo.commands.transactions.transactionsfactory;

import org.poo.commands.transactions.CheckCardStatusTransaction;
import org.poo.commands.transactions.Transaction;
import org.poo.commands.transactions.TransactionInput;

public class CheckCardStatusTransactionFactory implements TransactionFactory {
    private final Transaction.Type transactionType;
    private final int timestamp;
    private final String description;

    public CheckCardStatusTransactionFactory(TransactionInput input) {
        transactionType = input.getTransactionType();
        timestamp = input.getTimestamp();
        description = input.getDescription();
    }

    @Override
    public Transaction createTransaction() {
        return new CheckCardStatusTransaction(transactionType, timestamp, description);
    }
}
