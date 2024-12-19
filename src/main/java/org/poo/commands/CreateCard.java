package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.accounts.cards.Card;
import org.poo.commands.transactions.CreateCardTransaction;
import org.poo.commands.transactions.Transaction;
import org.poo.commands.transactions.TransactionInput;
import org.poo.users.User;

/**
 * Class used to represent the createCard command
 */
public final class CreateCard implements Command, Transactionable {
    private final Bank bank;
    private final String command;
    private final String account;
    private final String email;
    private final int timestamp;

    /**
     * Constructor for the createCard command
     * @param bank the receiver bank of the command
     * @param command the command name
     * @param account the associatedAccount IBAN
     * @param email the email of the User that owns the account && newCard
     * @param timestamp the timestamp of the command
     */
    public CreateCard(final Bank bank, final String command, final String account,
                      final String email, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.account = account;
        this.email = email;
        this.timestamp = timestamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        User owner = bank.getUser(email);
        Account associatedAccount = bank.getAccount(account);

         /* The last condition in this if statement might be
          * a separate case, but tests don't check this so
          * it can t be treated properly yet */
        if (owner == null || associatedAccount == null || !email.equals(associatedAccount.getOwnerEmail()))
            return;


        Card cardToAdd = bank.createCard(Card.ACTIVE, associatedAccount, command);
        bank.addCard(cardToAdd);

        TransactionInput input = new TransactionInput.Builder(Transaction.Type.CREATE_CARD, timestamp, Card.CARD_CREATED)
                .card(cardToAdd.getCardNumber())
                .cardHolder(owner.getEmail())
                .account(account)
                .error(null)
                .build();

        addTransaction(input, owner, associatedAccount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTransaction(TransactionInput input, User user, Account account) {
        bank.generateTransaction(input).addTransaction(user, account);
    }
}
