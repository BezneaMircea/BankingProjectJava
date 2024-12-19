package org.poo.bank.commands.transactions.transaction_factory;

import org.poo.bank.commands.transactions.ChangeIntRateTransaction;
import org.poo.bank.commands.transactions.Transaction;
import org.poo.bank.commands.transactions.TransactionInput;

public class ChangeIntRateTransactionFactory implements TransactionFactory {
    private final Transaction.Type transactionType;
    private final int timestamp;
    private final String description;

    public ChangeIntRateTransactionFactory(TransactionInput input) {
        transactionType = input.getTransactionType();
        timestamp = input.getTimestamp();
        description = input.getDescription();
    }


    @Override
    public Transaction createTransaction() {
        return new ChangeIntRateTransaction(transactionType, timestamp, description);
    }
}
