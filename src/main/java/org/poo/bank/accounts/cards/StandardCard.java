package org.poo.bank.accounts.cards;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.commands.transactions.PayOnlineTransaction;
import org.poo.commands.transactions.Transaction;
import org.poo.commands.transactions.TransactionInput;
import org.poo.users.User;

public class StandardCard extends Card {
    public StandardCard(String status, Account account) {
        super(status, account);
    }

    @Override
    public void pay(final Bank bank, final double amount,
                      final int timestamp, final String commerciant) {
        String error = null;
        if (getStatus().equals(FROZEN))
            error = PayOnlineTransaction.IS_FROZEN;

        Account associatedAccount = getAccount();
        User owner = bank.getEmailToUser().get(associatedAccount.getOwnerEmail());

        if (associatedAccount.getBalance() < amount && error == null)
            error = Account.INSUFFICIENT_FUNDS;

        TransactionInput payOnline = new TransactionInput.Builder(Transaction.Type.PAY_ONLINE, timestamp, "Card payment")
                .amount(amount)
                .commerciant(commerciant)
                .error(error)
                .build();

        bank.generateTransaction(payOnline).addTransaction(owner, associatedAccount);

        if (error == null)
            associatedAccount.setBalance(associatedAccount.getBalance() - amount);

    }
}
