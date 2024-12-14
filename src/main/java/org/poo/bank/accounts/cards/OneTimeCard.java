package org.poo.bank.accounts.cards;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;

public class OneTimeCard extends Card {
    public OneTimeCard(String status, Account account) {
        super(status, account);
    }

    @Override
    public ObjectNode pay(final Bank bank, final double amount) {
        Account associatedAccount = getAccount();

        if (associatedAccount.getBalance() < amount) {
            return null;
        }

        associatedAccount.setBalance(associatedAccount.getBalance() - amount);
        associatedAccount.removeCard(this);
        bank.getCardNrToCard().remove(getCardNumber());

        associatedAccount.addCard(new OneTimeCard(getStatus(), getAccount()));

        return null;
    }
}
