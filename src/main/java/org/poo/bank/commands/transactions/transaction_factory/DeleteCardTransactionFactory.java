package org.poo.bank.commands.transactions.transaction_factory;

import org.poo.bank.commands.transactions.DeleteCardTransaction;
import org.poo.bank.commands.transactions.Transaction;
import org.poo.bank.commands.transactions.TransactionInput;

public final class DeleteCardTransactionFactory implements TransactionFactory {
    private final Transaction.Type transactionType;
    private final int timestamp;
    private final String description;
    private final String card;
    private final String cardHolder;
    private final String account;

    public DeleteCardTransactionFactory(final TransactionInput input) {
        transactionType = input.getTransactionType();
        timestamp = input.getTimestamp();
        description = input.getDescription();
        card = input.getCard();
        cardHolder = input.getCardHolder();
        account = input.getAccount();
    }

    @Override
    public Transaction createTransaction() {
        return new DeleteCardTransaction(transactionType, timestamp, description,
                                         card, cardHolder, account);
    }
}
