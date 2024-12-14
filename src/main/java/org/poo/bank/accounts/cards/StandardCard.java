package org.poo.bank.accounts.cards;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;

public class StandardCard extends Card {
    public StandardCard(String status, Account account) {
        super(status, account);
    }

    @Override
    public ObjectNode pay(final Bank bank, final double amount) {
        Account associatedAccount = getAccount();

        if (associatedAccount.getBalance() < amount) {
            return null;
        }

        associatedAccount.setBalance(associatedAccount.getBalance() - amount);

        return null;
    }
}
