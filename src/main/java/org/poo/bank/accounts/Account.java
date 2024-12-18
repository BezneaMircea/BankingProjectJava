package org.poo.bank.accounts;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.poo.bank.accounts.cards.Card;
import org.poo.commands.AddInterest;
import org.poo.commands.transactions.*;
import org.poo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class Account {
    public static String NOT_SAVINGS_ACCOUNT = "This is not a savings account";
    public static String NOT_FOR_SAVINGS_ACCOUNT = "This kind of report is not supported for a saving account";

    private final String ownerEmail;
    private final String currency;
    private final String accountType;
    private final String iban;
    private double balance;
    private double minBalance;
    private final List<Card> cards;
    private final List<Transaction> transactions;

    public Account(String ownerEmail, String currency, String accountType) {
        this.ownerEmail = ownerEmail;
        this.currency = currency;
        this.accountType = accountType;
        iban = Utils.generateIBAN();
        cards = new ArrayList<>();
        transactions = new ArrayList<>();
        balance = 0;
        minBalance = 0;
    }

    public boolean hasCard(Card card) {
        return cards.contains(card);
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    /**
     * Performs a money transfer to accountToTransfer
     * @param accountToTransfer the account that receives the money
     * @param amountToTransfer the amount to transfer from the current account
     * @param amountToReceive the amount that the receiver account gets (the converted amount)
     */
    public void transfer(final Account accountToTransfer,
                         final double amountToTransfer,
                         final double amountToReceive) {
        balance -= amountToTransfer;
        accountToTransfer.balance += amountToReceive;
    }

    public ObjectNode accountToObjectNode() {
        ObjectNode accountNode = Utils.mapper.createObjectNode();
        accountNode.put("IBAN", iban);
        accountNode.put("balance", balance);
        accountNode.put("currency", currency);
        accountNode.put("type", accountType);
        accountNode.set("cards", writeCards());

        return accountNode;
    }


    private ArrayNode writeCards() {
        ArrayNode cardsNode = Utils.mapper.createArrayNode();
        for (Card card : cards) {
            cardsNode.add(card.cardToObjectNode());
        }

        return cardsNode;
    }


    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

//    public void addTransaction(AddAccountTransaction transaction) {
//        transactions.add(transaction);
//    }
//
//    public void addTransaction(ChangeIntRateTransaction transaction) {
//        transactions.add(transaction);
//    }
//
//    public void addTransaction(CheckCardStatusTransaction transaction) {
//        transactions.add(transaction);
//    }
//
//    public void addTransaction(CreateCardTransaction transaction) {
//        transactions.add(transaction);
//    }
//
//    public void addTransaction(DeleteCardTransaction transaction) {
//        transactions.add(transaction);
//    }
//
    public void addTransaction(PayOnlineTransaction transaction) {
        transactions.add(transaction);
    }
//
//    public void addTransaction(SendMoneyTransaction transaction) {
//        transactions.add(transaction);
//    }
//
//    public void addTransaction(SplitPaymentTranscation transaction) {
//        transactions.add(transaction);
//    }
    /// MORE TO BE ADDED

    public abstract String addInterest();
    public abstract String changeInterest(double interestRate);
    public abstract ArrayNode generateReport(int startTimestamp, int endTimestamp);
    public abstract ObjectNode spendingsReport(int startTimestamp, int endTimestamp);

}
