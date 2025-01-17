package org.poo.bank.transactions.transaction_factory;

import org.poo.bank.transactions.Transaction;
import org.poo.bank.transactions.TransactionInput;
import org.poo.bank.transactions.WithdrawSavingsTransaction;

public class WithdrawSavingsTransactionFactory implements TransactionFactory {
    private final Transaction.Type transactionType;
    private final int timestamp;
    private final String description;

    public WithdrawSavingsTransactionFactory(TransactionInput input) {
        transactionType = input.getTransactionType();
        timestamp = input.getTimestamp();
        description = input.getDescription();
    }

    @Override
    public Transaction createTransaction() {
        return new WithdrawSavingsTransaction(transactionType, timestamp, description);
    }
}
