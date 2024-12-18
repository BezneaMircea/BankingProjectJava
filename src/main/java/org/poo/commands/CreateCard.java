package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.accounts.cards.Card;
import org.poo.commands.transactions.CreateCardTransaction;
import org.poo.commands.transactions.Transaction;
import org.poo.commands.transactions.TransactionInput;
import org.poo.commands.transactions.transactionsfactory.CreateCardTransactionFactory;
import org.poo.commands.transactions.transactionsfactory.TransactionFactory;
import org.poo.users.User;

public class CreateCard implements Command, Transactionable {
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

        Card cardToAdd = bank.createCard(Card.ACTIVE, associatedAccount, command);
        associatedAccount.getCards().add(cardToAdd);
        bank.getCardNrToCard().put(cardToAdd.getCardNumber(), cardToAdd);

        TransactionInput input = new TransactionInput.Builder(timestamp, CreateCardTransaction.CARD_CREATED)
                .card(cardToAdd.getCardNumber())
                .cardHolder(owner.getEmail())
                .account(account)
                .error(null)
                .build();

        Transaction transaction = generateTransaction(input);
        owner.getTransactions().add(transaction);
        associatedAccount.getTransactions().add(transaction);
    }

    @Override
    public Transaction generateTransaction(TransactionInput input) {
        TransactionFactory factory = new CreateCardTransactionFactory(input);
        return factory.createTransaction();
    }
}
