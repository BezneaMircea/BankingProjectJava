package org.poo.bank.cards.card_factory;

import org.poo.bank.cards.Card;

/**
 * Interface implemented by specific banking card factories
 */
public interface CardFactory {
    /**
     * Method used to create a banking card
     *
     * @return the created card
     */
    Card createCard();
}
