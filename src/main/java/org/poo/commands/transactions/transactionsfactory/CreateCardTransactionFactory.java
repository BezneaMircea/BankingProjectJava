package org.poo.commands.transactions.transactionsfactory;

import org.poo.commands.transactions.CreateCardTransaction;
import org.poo.commands.transactions.Transaction;
import org.poo.commands.transactions.TransactionInput;

public class CreateCardTransactionFactory implements TransactionFactory {
    private final int timestamp;
    private final String description;
    private final String card;
    private final String cardHolder;
    private final String account;
    private final String error;

    public CreateCardTransactionFactory(TransactionInput transaction) {
        timestamp = transaction.getTimestamp();
        description = transaction.getDescription();
        card = transaction.getCard();
        cardHolder = transaction.getCardHolder();
        account = transaction.getAccount();
        error = transaction.getError();
    }

    @Override
    public Transaction createTransaction() {
        return new CreateCardTransaction(timestamp, description, card, cardHolder, account, error);
    }
}
