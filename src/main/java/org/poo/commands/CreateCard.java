package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.accounts.cards.Card;
import org.poo.users.User;

public class CreateCard implements Command {
    private final Bank bank;
    private final String command;
    private final String account;
    private final String email;
    private final int timestamp;

    public CreateCard(final Bank bank, final String command, final String account,
                      final String email, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.account = account;
        this.email = email;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        User owner = bank.getEmailToUser().get(email);
        if (owner == null)
            return;

        Account associatedAccount = bank.getIbanToAccount().get(account);
        if (!owner.hasAccount(associatedAccount)) {
            // TODO: add transaction logic;
            return;
        }

        Card cardToAdd = bank.createCard("active", associatedAccount, command);
        /// maybe check for fails here

        associatedAccount.getCards().add(cardToAdd);
        bank.getCardNrToCard().put(cardToAdd.getCardNumber(), cardToAdd);
    }
}
