package org.poo.commands.transactions.transaction_factory;

import org.poo.commands.transactions.PayOnlineTransaction;
import org.poo.commands.transactions.Transaction;
import org.poo.commands.transactions.TransactionInput;

public class PayOnlineTransactionFactory implements TransactionFactory {
    private final Transaction.Type transactionType;
    private final int timestamp;
    private final String description;
    private final double amount;
    private final String commerciant;
    private final String error;

    public PayOnlineTransactionFactory(TransactionInput input) {
        transactionType = input.getTransactionType();
        timestamp = input.getTimestamp();
        description = input.getDescription();
        amount = input.getAmount();
        commerciant = input.getCommerciant();
        error = input.getError();
    }

    @Override
    public Transaction createTransaction() {
        return new PayOnlineTransaction(transactionType, timestamp, description, amount, commerciant, error);
    }
}
