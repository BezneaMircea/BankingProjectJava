package org.poo.bank.transactions.transaction_factory;

import org.poo.bank.transactions.SplitPaymentCoustomTransaction;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.transactions.TransactionInput;

import java.util.List;

public class SplitPaymentCoustomTransactionFactory implements TransactionFactory {
    private final Transaction.Type transactionType;
    private final int timestamp;
    private final String description;
    private final String currency;
    private final List<Double> amounts;
    private final String splitPaymentType;
    private final List<String> involvedAccounts;
    private final String error;


    public SplitPaymentCoustomTransactionFactory(TransactionInput input) {
        transactionType = input.getTransactionType();
        timestamp = input.getTimestamp();
        description = input.getDescription();
        currency = input.getCurrency();
        amounts = input.getAmountForUsers();
        splitPaymentType = input.getSplitPaymentType();
        involvedAccounts = input.getInvolvedAccounts();
        error = input.getError();
    }



    @Override
    public Transaction createTransaction() {
        return new SplitPaymentCoustomTransaction(transactionType, timestamp,
                                                  description, currency, amounts,
                                                  splitPaymentType, involvedAccounts, error);
    }
}
