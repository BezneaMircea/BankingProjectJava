package org.poo.commands;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.accounts.cards.Card;
import org.poo.commands.transactions.Transaction;
import org.poo.commands.transactions.TransactionInput;
import org.poo.commands.transactions.transactionsfactory.PayOnlineTransactionFactory;
import org.poo.commands.transactions.transactionsfactory.TransactionFactory;
import org.poo.users.User;
import org.poo.utils.Utils;

@JsonAppend
public class PayOnline implements Command, Transactionable {
    private final Bank bank;
    private final String command;
    private final String cardNumber;
    private final double amount;
    private final String currency;
    private final int timestamp;
    private final String description;
    private final String commerciant;
    private final String email;

    public PayOnline(final Bank bank, final String command, final String cardNumber,
                     final double amount, final String currency, final int timestamp,
                     final String description, final String commerciant, final String email) {
        this.bank = bank;
        this.command = command;
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.currency = currency;
        this.timestamp = timestamp;
        this.description = description;
        this.commerciant = commerciant;
        this.email = email;
    }
    @Override
    public void execute() {
        User cardOwner = bank.getEmailToUser().get(email);
        Card usedCard = bank.getCardNrToCard().get(cardNumber);

        if (cardOwner == null || usedCard == null) {
            ObjectNode outputNode = Utils.mapper.createObjectNode();
            outputNode.put("description", "Card not found");
            outputNode.put("timestamp", timestamp);

            ObjectNode errorNode = Utils.mapper.createObjectNode();
            errorNode.put("command", command);
            errorNode.set("output", outputNode);
            errorNode.put("timestamp", timestamp);
            bank.getOutput().add(errorNode);

            return;
        }
        Account associatedAccount = usedCard.getAccount();



        /* Check if the owner owns the card by checking if the
         * owner owns the account associated with the card
         */

        if (!(cardOwner.hasAccount(associatedAccount) && associatedAccount.hasCard(usedCard))) {
            return;
        }

        double exchangeRate = bank.getExchangeRates().getRate(currency, associatedAccount.getCurrency());
        double totalSumToPay = amount * exchangeRate;

        String error = usedCard.pay(bank, totalSumToPay);
        TransactionInput input = new TransactionInput.Builder(timestamp, "Card payment")
                .amount(totalSumToPay)
                .commerciant(commerciant)
                .error(error)
                .build();

        Transaction transaction = generateTransaction(input);
        transaction.addTransaction(cardOwner, associatedAccount);
    }

    @Override
    public Transaction generateTransaction(TransactionInput input) {
        TransactionFactory factory = new PayOnlineTransactionFactory(input);
        return factory.createTransaction();
    }
}
