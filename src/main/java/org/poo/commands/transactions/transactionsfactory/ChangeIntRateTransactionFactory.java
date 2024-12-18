package org.poo.commands.transactions.transactionsfactory;

import org.poo.commands.transactions.ChangeIntRateTransaction;
import org.poo.commands.transactions.Transaction;
import org.poo.commands.transactions.TransactionInput;

public class ChangeIntRateTransactionFactory implements TransactionFactory {
//    "timestamp" : 2,
//            "description" : "Interest rate of the account changed to 0.81"
    private final int timestamp;
    private final String description;

    public ChangeIntRateTransactionFactory(TransactionInput input) {
        timestamp = input.getTimestamp();
        description = input.getDescription();
    }


    @Override
    public Transaction createTransaction() {
        return new ChangeIntRateTransaction(timestamp, description);
    }
}
