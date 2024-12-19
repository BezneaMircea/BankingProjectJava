package org.poo.bank.accounts.cards.cardfactory;

import org.poo.bank.accounts.Account;
import org.poo.bank.accounts.cards.Card;
import org.poo.bank.accounts.cards.StandardCard;

/**
 * Class used to represent the StandardCardFactory
 */
public final class StandardCardFactory implements CardFactory {
    private final String status;
    private final Account account;

    /**
     * Constructor for the OneTimeCardFactory
     * @param status the status of the card
     * @param account the account to which the card is linked
     */
    public StandardCardFactory(String status, Account account) {
        this.status = status;
        this.account = account;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Card createCard() {
        return new StandardCard(status, account);
    }
}
