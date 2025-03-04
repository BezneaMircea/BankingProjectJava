package org.poo.bank.transactions.transaction_factory;

import org.poo.bank.transactions.SplitPaymentTranscation;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.transactions.TransactionInput;

import java.util.List;

public final class SplitPaymenTransactionFactory implements TransactionFactory {
    private final Transaction.Type transactionType;
    private final int timestamp;
    private final String description;
    private final String currency;
    private final double amount;
    private final String splitPaymentType;
    private final List<String> involvedAccounts;
    private final String error;

    public SplitPaymenTransactionFactory(final TransactionInput input) {
        transactionType = input.getTransactionType();
        timestamp = input.getTimestamp();
        description = input.getDescription();
        currency = input.getCurrency();
        amount = input.getAmount();
        splitPaymentType = input.getSplitPaymentType();
        involvedAccounts = input.getInvolvedAccounts();
        error = input.getError();
    }


    @Override
    public Transaction createTransaction() {
        return new SplitPaymentTranscation(transactionType, timestamp, description,
                                           currency, amount, involvedAccounts,
                                           splitPaymentType, error);
    }
}
