package org.poo.bank.transactions.transaction_factory;

import org.poo.bank.transactions.DeleteAccountTransaction;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.transactions.TransactionInput;

public final class DeleteAccountTransactionFactory implements TransactionFactory {
    private final Transaction.Type transactionType;
    private final int timestamp;
    private final String description;

    public DeleteAccountTransactionFactory(final TransactionInput input) {
        transactionType = input.getTransactionType();
        timestamp = input.getTimestamp();
        description = input.getDescription();
    }


    @Override
    public Transaction createTransaction() {
        return new DeleteAccountTransaction(transactionType, timestamp, description);
    }
}
