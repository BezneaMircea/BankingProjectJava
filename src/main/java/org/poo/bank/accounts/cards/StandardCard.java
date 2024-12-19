package org.poo.bank.accounts.cards;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.commands.transactions.Transaction;
import org.poo.bank.commands.transactions.TransactionInput;
import org.poo.bank.users.User;

/**
 * Class used to represent a StandardCard
 */
public final class StandardCard extends Card {
    /**
     * Constructor for the StandardCard, just calls the superclass constructor with
     * the given params
     * @param status status of the card
     * @param account account to which the card is linked
     */
    public StandardCard(String status, Account account, Type cardType) {
        super(status, account, cardType);
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
