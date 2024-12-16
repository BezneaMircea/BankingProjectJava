package org.poo.bank.accounts.cards;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.commands.transactions.PayOnlineTransaction;

public class StandardCard extends Card {
    public StandardCard(String status, Account account) {
        super(status, account);
    }

    @Override
    public String pay(final Bank bank, final double amount) {
        if (getStatus().equals(FROZEN))
            return PayOnlineTransaction.IS_FROZEN;

        Account associatedAccount = getAccount();

        if (associatedAccount.getBalance() < amount) {
            return INSUFFICIENT_FUNDS;
        }

        associatedAccount.setBalance(associatedAccount.getBalance() - amount);

        return null;
    }
}
