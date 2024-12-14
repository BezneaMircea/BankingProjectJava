package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.accounts.cards.Card;

public class DeleteCard implements Command {
    private final Bank bank;
    private final String cardNumber;
    private final int timestamp;

    public DeleteCard(final Bank bank, final String cardNumber, final int timestamp) {
        this.bank = bank;
        this.cardNumber = cardNumber;
        this.timestamp = timestamp;
    }


    @Override
    public void execute() {
        Card cardToDelete = bank.getCardNrToCard().get(cardNumber);
        if (cardToDelete == null)
            return;

        bank.getCardNrToCard().remove(cardNumber);
        Account associatedAccount = cardToDelete.getAccount();
        associatedAccount.getCards().remove(cardToDelete);
    }
}
