package org.poo.commands;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.accounts.cards.Card;
import org.poo.commands.transactions.CheckCardStatusTransaction;
import org.poo.commands.transactions.Transaction;
import org.poo.commands.transactions.TransactionInput;
import org.poo.commands.transactions.transactionsfactory.CheckCardStatusTransactionFactory;
import org.poo.commands.transactions.transactionsfactory.TransactionFactory;
import org.poo.users.User;
import org.poo.utils.Utils;

public class CheckCardStatus implements Command, Transactionable {
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
            bank.errorOccured(timestamp, command, "Card not found");
            return;
        }

        if (cardToCheck.getStatus().equals(Card.FROZEN))
            return;

        Account associatedAccount = cardToCheck.getAccount();
        if (associatedAccount.getBalance() < associatedAccount.getMinBalance()) {
            cardToCheck.setStatus(Card.FROZEN);
        } else if (associatedAccount.getBalance() - associatedAccount.getMinBalance() < 30) {
            cardToCheck.setStatus(Card.FROZEN);

            User owner = bank.getEmailToUser().get(associatedAccount.getOwnerEmail());

            TransactionInput input = new TransactionInput.Builder(Transaction.Type.CHECK_CARD_STAT, timestamp, CheckCardStatusTransaction.LIMIT_REACHED).build();
            bank.generateTransaction(input).addTransaction(owner, associatedAccount);
        }

    }

    @Override
    public Transaction generateTransaction(TransactionInput input) {
        TransactionFactory factory = new CheckCardStatusTransactionFactory(input);
        return factory.createTransaction();
    }

}
