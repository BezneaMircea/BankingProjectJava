package org.poo.bank.accounts.cards;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.commands.transactions.*;
import org.poo.users.User;

/**
 * Class used to represent a one time card
 */
public final class OneTimeCard extends Card {
    /**
     * Constructor for the OneTimeCard, just calls the superclass constructor with
     * the given params
     * @param status status of the card
     * @param account account to which the card is linked
     */
    public OneTimeCard(String status, Account account) {
        super(status, account);
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public void pay(final Bank bank, final double amount,
                      final int timestamp, final String commerciant) {
        String error = null;

        if (getStatus().equals(FROZEN))
            error = Card.IS_FROZEN;

        Account associatedAccount = getAccount();
        User owner = bank.getEmailToUser().get(getAccount().getOwnerEmail());

        if (associatedAccount.getBalance() < amount && error == null) {
            error = Account.INSUFFICIENT_FUNDS;
        }

        TransactionInput payOnline = new TransactionInput.Builder(Transaction.Type.PAY_ONLINE, timestamp, "Card payment")
                .amount(amount)
                .commerciant(commerciant)
                .error(error)
                .build();

        if (error != null) {
            bank.generateTransaction(payOnline).addTransaction(owner, associatedAccount);
            return;
        }



        associatedAccount.setBalance(associatedAccount.getBalance() - amount);
        associatedAccount.removeCard(this);
        bank.getCardNrToCard().remove(getCardNumber());

        Card cardToAdd = new OneTimeCard(getStatus(), getAccount());
        associatedAccount.addCard(cardToAdd);
        bank.getCardNrToCard().put(cardToAdd.getCardNumber(), cardToAdd);



        TransactionInput deleteCard = new TransactionInput.Builder(Transaction.Type.DELETE_CARD, timestamp, Card.DESTROYED)
                .card(getCardNumber())
                .cardHolder(getAccount().getOwnerEmail())
                .account(getAccount().getIban())
                .build();

        TransactionInput createCard = new TransactionInput.Builder(Transaction.Type.CREATE_CARD, timestamp, Card.CARD_CREATED)
                .card(cardToAdd.getCardNumber())
                .cardHolder(getAccount().getOwnerEmail())
                .account(getAccount().getIban())
                .build();


        bank.generateTransaction(payOnline).addTransaction(owner, associatedAccount);
        bank.generateTransaction(deleteCard).addTransaction(owner, associatedAccount);
        bank.generateTransaction(createCard).addTransaction(owner, associatedAccount);
    }
}
