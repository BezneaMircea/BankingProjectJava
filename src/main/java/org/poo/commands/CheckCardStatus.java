package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.accounts.cards.Card;

public class CheckCardStatus implements Command {
    private final Bank bank;
    private final String command;
    private final String cardNumber;
    private final int timestamp;

    public CheckCardStatus(final Bank bank, final String command,
                           final String cardNumber, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.cardNumber = cardNumber;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        Card cardToCheck = bank.getCardNrToCard().get(cardNumber);
        if (cardToCheck == null) {
            /// TODO: add logic here
            return;
        }

        Account associatedAccount = cardToCheck.getAccount();

        if (associatedAccount.getBalance() < associatedAccount.getMinBalance()) {
            cardToCheck.setStatus(Card.FROZEN);
        } else {
            /// TODO: add logic here
        }

    }
}
