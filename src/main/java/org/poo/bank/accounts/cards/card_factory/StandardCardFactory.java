package org.poo.bank.accounts.cards.card_factory;

import org.poo.bank.accounts.Account;
import org.poo.bank.accounts.cards.Card;
import org.poo.bank.accounts.cards.CardInput;
import org.poo.bank.accounts.cards.StandardCard;

/**
 * Class used to represent the StandardCardFactory
 */
public final class StandardCardFactory implements CardFactory {
    private final String status;
    private final Account account;
    private final Card.Type cardType;

    /**
     * Constructor for the OneTimeCardFactory
     * @param input the card input
     */
    public StandardCardFactory(final CardInput input) {
        status = input.getStatus();
        account = input.getAccount();
        cardType = input.getCardType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Card createCard() {
        return new StandardCard(status, account, cardType);
    }
}
