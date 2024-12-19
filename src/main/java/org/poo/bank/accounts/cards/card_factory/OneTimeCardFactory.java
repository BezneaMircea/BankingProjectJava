package org.poo.bank.accounts.cards.card_factory;

import org.poo.bank.accounts.Account;
import org.poo.bank.accounts.cards.Card;
import org.poo.bank.accounts.cards.CardInput;
import org.poo.bank.accounts.cards.OneTimeCard;

/**
 * Class used to represent the OneTimeCardFactory
 */
public final class OneTimeCardFactory implements CardFactory {
    private final String status;
    private final Account account;
    private final Card.Type cardType;

    /**
     * Constructor for the OneTimeCardFactory
     * @param input the card input
     */
    public OneTimeCardFactory(CardInput input) {
        status = input.getStatus();
        account = input.getAccount();
        cardType = input.getCardType();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Card createCard() {
        return new OneTimeCard(status, account, cardType);
    }
}
