package org.poo.commands.transactions.transactionsfactory;

import org.poo.commands.transactions.SplitPaymentTranscation;
import org.poo.commands.transactions.Transaction;
import org.poo.commands.transactions.TransactionInput;

import java.util.List;

public class SplitPaymenTransactionFactory implements TransactionFactory {
    private final int timestamp;
    private final String description;
    private final String currency;
    private final double amount;
    private final List<String> involvedAccounts;
    private final String error;

    public SplitPaymenTransactionFactory(TransactionInput input) {
        timestamp = input.getTimestamp();
        description = input.getDescription();
        currency = input.getCurrency();
        amount = input.getAmount();
        involvedAccounts = input.getInvolvedAccounts();
        error = input.getError();
    }


    @Override
    public Transaction createTransaction() {
        return new SplitPaymentTranscation(timestamp, description, currency, amount, involvedAccounts, error);
    }
}
