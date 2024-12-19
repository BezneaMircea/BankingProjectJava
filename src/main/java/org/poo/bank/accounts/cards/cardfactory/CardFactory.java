package org.poo.bank.accounts.cards.cardfactory;

import org.poo.bank.accounts.cards.Card;

/**
 * Interface implemented by specific banking card factories
 */
public interface CardFactory {
    /**
     * Method used to create a banking card
     * @return the created card
     */
    Card createCard();
}
