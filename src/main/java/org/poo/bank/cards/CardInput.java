package org.poo.bank.cards;

import lombok.Getter;
import org.poo.bank.accounts.Account;

@Getter
public final class CardInput {
    private final String status;
    private final Account account;
    private final Card.Type cardType;

    public CardInput(final String status, final Account account, final Card.Type cardType) {
        this.status = status;
        this.account = account;
        this.cardType = cardType;
    }
}
