package org.poo.bank.commands.transactions.transaction_factory;

import org.poo.bank.commands.transactions.AddAccountTransaction;
import org.poo.bank.commands.transactions.Transaction;
import org.poo.bank.commands.transactions.TransactionInput;

public class AddAccountTransactionFactory implements TransactionFactory {
    private final Transaction.Type transactionType;
    private final int timestamp;
    private final String description;

    public AddAccountTransactionFactory(TransactionInput input) {
        transactionType = input.getTransactionType();
        timestamp = input.getTimestamp();
        description = input.getDescription();
    }


    @Override
    public Transaction createTransaction() {
        return new AddAccountTransaction(transactionType, timestamp, description);
    }
}
