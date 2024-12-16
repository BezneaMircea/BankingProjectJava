package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.accounts.cards.Card;
import org.poo.commands.transactions.DeleteCardTransaction;
import org.poo.commands.transactions.Transaction;
import org.poo.commands.transactions.TransactionInput;
import org.poo.commands.transactions.transactionsfactory.DeleteCardTransactionFactory;
import org.poo.commands.transactions.transactionsfactory.TransactionFactory;
import org.poo.users.User;

public class DeleteCard implements Command, Transactionable {
    private final Bank bank;
    private final String cardNumber;
    private final int timestamp;

    public DeleteCard(final Bank bank, final String cardNumber, final int timestamp) {
        this.bank = bank;
        this.cardNumber = cardNumber;
        this.timestamp = timestamp;
    }


    @Override
    public void execute() {
        Card cardToDelete = bank.getCardNrToCard().get(cardNumber);
        if (cardToDelete == null)
            return;


        bank.getCardNrToCard().remove(cardNumber);
        Account associatedAccount = cardToDelete.getAccount();
        associatedAccount.getCards().remove(cardToDelete);

        User owner = bank.getEmailToUser().get(associatedAccount.getOwnerEmail());
        TransactionInput input = new TransactionInput.Builder(timestamp, DeleteCardTransaction.DELETED_CARD)
                .card(cardNumber)
                .cardHolder(owner.getEmail())
                .account(associatedAccount.getIban())
                .build();


        owner.getTransactions().add(generateTransaction(input));
    }

    @Override
    public Transaction generateTransaction(TransactionInput input) {
        TransactionFactory factory = new DeleteCardTransactionFactory(input);
        return factory.createTransaction();
    }
}
