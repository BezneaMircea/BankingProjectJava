package org.poo.bank.transactions.transaction_factory;

import org.poo.bank.transactions.AddInterestTransaction;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.transactions.TransactionInput;

public class AddInterestTransactionFactory implements TransactionFactory {
    private final Transaction.Type type;
    private final double amount;
    private final String currency;
    private final String description;
    private final int timestamp;

    public AddInterestTransactionFactory(final TransactionInput input) {
        type = input.getTransactionType();
        amount = input.getAmount();
        currency = input.getCurrency();
        description = input.getDescription();
        timestamp = input.getTimestamp();
    }


    @Override
    public Transaction createTransaction() {
        return new AddInterestTransaction(type, amount, currency, description, timestamp);
    }
}
