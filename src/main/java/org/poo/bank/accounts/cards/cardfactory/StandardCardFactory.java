package org.poo.bank.accounts.cards.cardfactory;

import org.poo.bank.accounts.Account;
import org.poo.bank.accounts.cards.Card;
import org.poo.bank.accounts.cards.StandardCard;

public class StandardCardFactory implements CardFactory {
    private final String status;
    private final Account account;

    public StandardCardFactory(String status, Account account) {
        this.status = status;
        this.account = account;
    }

    @Override
    public Card createCard() {
        return new StandardCard(status, account);
    }
}
