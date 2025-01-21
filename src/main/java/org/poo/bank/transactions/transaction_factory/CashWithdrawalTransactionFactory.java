package org.poo.bank.transactions.transaction_factory;

import org.poo.bank.transactions.CashWithdrawalTransaction;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.transactions.TransactionInput;

public final class CashWithdrawalTransactionFactory implements TransactionFactory {
    private final Transaction.Type transactionType;
    private final int timestamp;
    private final String description;
    private final String error;
    private final double amount;

    public CashWithdrawalTransactionFactory(final TransactionInput input) {
        transactionType = input.getTransactionType();
        timestamp = input.getTimestamp();
        description = input.getDescription();
        error = input.getError();
        amount = input.getAmount();
    }


    @Override
    public Transaction createTransaction() {
        return new CashWithdrawalTransaction(transactionType, timestamp,
                                             description, error, amount);
    }
}
