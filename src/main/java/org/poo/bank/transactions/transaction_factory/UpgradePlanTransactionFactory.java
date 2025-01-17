package org.poo.bank.transactions.transaction_factory;

import org.poo.bank.transactions.Transaction;
import org.poo.bank.transactions.TransactionInput;
import org.poo.bank.transactions.UpgradePlanTransaction;

public class UpgradePlanTransactionFactory implements TransactionFactory {
    private final Transaction.Type transactionType;
    private final int timestamp;
    private final String description;
    private final String account;
    private final String newPlanType;
    private final String error;

    public UpgradePlanTransactionFactory(TransactionInput input) {
        transactionType = input.getTransactionType();
        timestamp = input.getTimestamp();
        description = input.getDescription();
        account = input.getAccount();
        newPlanType = input.getNewPlanType();
        error = input.getError();
    }

    @Override
    public Transaction createTransaction() {
        return new UpgradePlanTransaction(transactionType, timestamp,
                                            description, account,
                                            newPlanType, error);
    }
}
