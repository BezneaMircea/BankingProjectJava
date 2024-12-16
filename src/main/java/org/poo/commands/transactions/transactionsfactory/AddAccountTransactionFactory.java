package org.poo.commands.transactions.transactionsfactory;

import org.poo.commands.transactions.AddAccountTransaction;
import org.poo.commands.transactions.Transaction;
import org.poo.commands.transactions.TransactionInput;

public class AddAccountTransactionFactory implements TransactionFactory {
    private final int timestamp;
    private final String description;

    public AddAccountTransactionFactory(TransactionInput input) {
        timestamp = input.getTimestamp();
        description = input.getDescription();
    }


    @Override
    public Transaction createTransaction() {
        return new AddAccountTransaction(timestamp, description);
    }
}
