package org.poo.bank.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.accounts.cards.Card;
import org.poo.bank.commands.transactions.Transaction;
import org.poo.bank.commands.transactions.TransactionInput;
import org.poo.bank.users.User;


/**
 * Class used to represent the checkCardStatus command
 */
public final class CheckCardStatus implements Command, Transactionable {
    private final Bank bank;
    private final String command;
    private final String cardNumber;
    private final int timestamp;

    /**
     * Constructor for the checkCardStatusCommand
     *
     * @param bank       the receiver bank of the command
     * @param command    the command name
     * @param cardNumber the number of the card to be checked
     * @param timestamp  the timestamp of the command
     */
    public CheckCardStatus(final Bank bank, final String command,
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
        Card cardToCheck = bank.getCard(cardNumber);
        if (cardToCheck == null) {
            bank.errorOccured(timestamp, command, Card.NOT_FOUND);
            return;
        }

        if (cardToCheck.getStatus().equals(Card.FROZEN)) {
            return;
        }

        Account associatedAccount = cardToCheck.getAccount();
        if (associatedAccount.getBalance() < associatedAccount.getMinBalance()) {
            cardToCheck.setStatus(Card.FROZEN);
        } else if (associatedAccount.getBalance() - associatedAccount.getMinBalance() < Account.WARNING_THRESHOLD) {
            cardToCheck.setStatus(Card.FROZEN);

            User owner = bank.getUser(associatedAccount.getOwnerEmail());
            addTransaction(null, owner, associatedAccount);
        }

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void addTransaction(TransactionInput input, final User user, final Account account) {
        input = new TransactionInput.Builder(Transaction.Type.CHECK_CARD_STAT, timestamp, Card.LIMIT_REACHED).build();
        bank.generateTransaction(input).addTransaction(user, account);
    }
}
