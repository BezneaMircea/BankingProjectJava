package org.poo.bank.transactions.transaction_factory;

import org.poo.bank.transactions.CreateCardTransaction;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.transactions.TransactionInput;

public final class CreateCardTransactionFactory implements TransactionFactory {
    private final Transaction.Type transactionType;
    private final int timestamp;
    private final String description;
    private final String card;
    private final String cardHolder;
    private final String account;
    private final String error;

    public CreateCardTransactionFactory(final TransactionInput input) {
        transactionType = input.getTransactionType();
        timestamp = input.getTimestamp();
        description = input.getDescription();
        card = input.getCard();
        cardHolder = input.getCardHolder();
        account = input.getAccount();
        error = input.getError();
    }

    @Override
    public Transaction createTransaction() {
        return new CreateCardTransaction(transactionType, timestamp, description,
                                         card, cardHolder, account, error);
    }

}
