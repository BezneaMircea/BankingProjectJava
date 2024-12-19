package org.poo.bank.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.accounts.cards.Card;
import org.poo.bank.commands.transactions.Transaction;
import org.poo.bank.commands.transactions.TransactionInput;
import org.poo.bank.users.User;

/**
 * Class used to represent the deleteCard command
 */
public final class DeleteCard implements Command, Transactionable {
    private final Bank bank;
    private final String command;
    private final String cardNumber;
    private final int timestamp;

    /**
     * Constructor for the deleteCard command
     *
     * @param bank       the receiver bank of the command
     * @param command    the command name
     * @param cardNumber the number of the card to be deleted
     * @param timestamp  timestamp of the command
     */
    public DeleteCard(final Bank bank, final String command,
                      final String cardNumber, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.cardNumber = cardNumber;
        this.timestamp = timestamp;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        Card cardToDelete = bank.getCard(cardNumber);
        if (cardToDelete == null) {
            return;
        }

        Account associatedAccount = cardToDelete.getAccount();
        User owner = bank.getUser(associatedAccount.getOwnerEmail());

        bank.deleteCard(cardToDelete);

        addTransaction(null, owner, associatedAccount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTransaction(TransactionInput input, final User user, final Account account) {
        input = new TransactionInput.Builder(Transaction.Type.DELETE_CARD, timestamp, Card.DESTROYED)
                .card(cardNumber)
                .cardHolder(user.getEmail())
                .account(account.getIban())
                .build();

        bank.generateTransaction(input).addTransaction(user, account);
    }
}
