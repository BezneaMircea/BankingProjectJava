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
            ObjectNode node = Utils.mapper.createObjectNode();
            node.put("command", command);

            ObjectNode outputNode = Utils.mapper.createObjectNode();
            outputNode.put("timestamp", timestamp);
            outputNode.put("description", "Card not found");

            node.set("output", outputNode);
            node.put("timestamp", timestamp);

            bank.getOutput().add(node);
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

            TransactionInput input = new TransactionInput.Builder(timestamp, CheckCardStatusTransaction.LIMIT_REACHED).build();
            Transaction transaction = generateTransaction(input);

            owner.getTransactions().add(transaction);
            associatedAccount.getTransactions().add(transaction);
        }

    }

    @Override
    public Transaction generateTransaction(TransactionInput input) {
        TransactionFactory factory = new CheckCardStatusTransactionFactory(input);
        return factory.createTransaction();
    }

    /// TODO: check this out later pls MIRCEA

}
