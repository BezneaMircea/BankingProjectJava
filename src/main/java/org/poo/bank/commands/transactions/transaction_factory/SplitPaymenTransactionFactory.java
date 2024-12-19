package org.poo.bank.commands.transactions.transaction_factory;

import org.poo.bank.commands.transactions.SplitPaymentTranscation;
import org.poo.bank.commands.transactions.Transaction;
import org.poo.bank.commands.transactions.TransactionInput;

import java.util.List;

public class SplitPaymenTransactionFactory implements TransactionFactory {
    private final Transaction.Type transactionType;
    private final int timestamp;
    private final String description;
    private final String currency;
    private final double amount;
    private final List<String> involvedAccounts;
    private final String error;

    public SplitPaymenTransactionFactory(TransactionInput input) {
        transactionType = input.getTransactionType();
        timestamp = input.getTimestamp();
        description = input.getDescription();
        currency = input.getCurrency();
        amount = input.getAmount();
        involvedAccounts = input.getInvolvedAccounts();
        error = input.getError();
    }


    @Override
    public Transaction createTransaction() {
        return new SplitPaymentTranscation(transactionType, timestamp, description, currency, amount, involvedAccounts, error);
    }
}
