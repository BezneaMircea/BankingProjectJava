package org.poo.bank.accounts.cards.cardfactory;

import org.poo.bank.accounts.Account;
import org.poo.bank.accounts.cards.Card;
import org.poo.bank.accounts.cards.OneTimeCard;

/**
 * Class used to represent the OneTimeCardFactory
 */
public final class OneTimeCardFactory implements CardFactory {
    private final String status;
    private final Account account;

    /**
     * Constructor for the OneTimeCardFactory
     * @param status the status of the card
     * @param account the account to which the card is linked
     */
    public OneTimeCardFactory(String status, Account account) {
        this.status = status;
        this.account = account;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Card createCard() {
        return new OneTimeCard(status, account);
    }
}
