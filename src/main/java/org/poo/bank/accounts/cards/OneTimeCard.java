package org.poo.bank.accounts.cards;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.commands.factory.CommandFactory;
import org.poo.commands.factory.DeleteCardFactory;
import org.poo.commands.transactions.*;
import org.poo.commands.transactions.transactionsfactory.CreateCardTransactionFactory;
import org.poo.commands.transactions.transactionsfactory.DeleteCardTransactionFactory;
import org.poo.commands.transactions.transactionsfactory.TransactionFactory;
import org.poo.users.User;

public class OneTimeCard extends Card {
    public OneTimeCard(String status, Account account) {
        super(status, account);
    }


//    "timestamp" : 27,
//            "description" : "The card has been destroyed",
//            "card" : "7901879264253296",
//            "cardHolder" : "Leanne_Scott-Davies@yandex.nz",
//            "account" : "RO90POOB5450777208072365"
//}, {
//        "timestamp" : 27,
//        "description" : "New card created",
//        "card" : "7317745095154687",
//        "cardHolder" : "Leanne_Scott-Davies@yandex.nz",
//        "account" : "RO90POOB5450777208072365"
//        }, {

    @Override
    public void pay(final Bank bank, final double amount,
                      final int timestamp, final String commerciant) {
        String error = null;

        if (getStatus().equals(FROZEN))
            error = PayOnlineTransaction.IS_FROZEN;

        Account associatedAccount = getAccount();
        User owner = bank.getEmailToUser().get(getAccount().getOwnerEmail());

        if (associatedAccount.getBalance() < amount && error == null) {
            error = INSUFFICIENT_FUNDS;
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



        TransactionInput deleteCard = new TransactionInput.Builder(Transaction.Type.DELETE_CARD, timestamp, DeleteCardTransaction.DELETED_CARD)
                .card(getCardNumber())
                .cardHolder(getAccount().getOwnerEmail())
                .account(getAccount().getIban())
                .build();

        TransactionInput createCard = new TransactionInput.Builder(Transaction.Type.CREATE_CARD, timestamp, CreateCardTransaction.CARD_CREATED)
                .card(cardToAdd.getCardNumber())
                .cardHolder(getAccount().getOwnerEmail())
                .account(getAccount().getIban())
                .build();


        bank.generateTransaction(payOnline).addTransaction(owner, associatedAccount);
        bank.generateTransaction(deleteCard).addTransaction(owner, associatedAccount);
        bank.generateTransaction(createCard).addTransaction(owner, associatedAccount);
    }
}
