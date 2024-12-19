package org.poo.bank.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.accounts.cards.Card;
import org.poo.bank.accounts.cards.CardInput;
import org.poo.bank.commands.transactions.Transaction;
import org.poo.bank.commands.transactions.TransactionInput;
import org.poo.bank.users.User;

/**
 * Class used to represent the createCard command
 */
public final class CreateCard implements Command, Transactionable {
    private final Bank bank;
    private final String command;
    private final String account;
    private final String email;
    private final Card.Type cardType;
    private final int timestamp;

    /**
     * Constructor for the createCard command
     *
     * @param bank      the receiver bank of the command
     * @param command   the command name
     * @param account   the associatedAccount IBAN
     * @param email     the email of the User that owns the account && newCard
     * @param cardType  the card type
     * @param timestamp the timestamp of the command
     */
    public CreateCard(final Bank bank, final String command, final String account,
                      final String email, final Card.Type cardType, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.account = account;
        this.email = email;
        this.cardType = cardType;
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
        if (owner == null || associatedAccount == null || !email.equals(associatedAccount.getOwnerEmail())) {
            return;
        }


        CardInput newCard = new CardInput(Card.ACTIVE, associatedAccount, cardType);
        Card cardToAdd = bank.createCard(newCard);
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
    public void
    addTransaction(final TransactionInput input, final User user, final Account account) {
        bank.generateTransaction(input).addTransaction(user, account);
    }
}
