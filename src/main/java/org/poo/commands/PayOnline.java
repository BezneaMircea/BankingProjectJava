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
        Card usedCard = bank.getCardNrToCard().get(cardNumber);
        if (usedCard == null) {
            bank.errorOccured(timestamp, command, "Card not found");
            return;
        }


        Account associatedAccount = usedCard.getAccount();
        User cardOwner = bank.getEmailToUser().get(email);
        if (cardOwner == null || associatedAccount == null)
            return;

        if (!(cardOwner.hasAccount(associatedAccount) && associatedAccount.hasCard(usedCard))) {
            return;
        }

        double exchangeRate = bank.getExchangeRates().getRate(currency, associatedAccount.getCurrency());
        double totalSumToPay = amount * exchangeRate;

        usedCard.pay(bank, totalSumToPay, timestamp, commerciant);
    }

    @Override
    public Transaction generateTransaction(TransactionInput input) {
        TransactionFactory factory = new PayOnlineTransactionFactory(input);
        return factory.createTransaction();
    }
}
