package org.poo.bank.accounts;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.poo.bank.accounts.cards.Card;
import org.poo.commands.AddInterest;
import org.poo.commands.transactions.*;
import org.poo.utils.Utils;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.round;

@Getter
@Setter
public abstract class Account {
    public static String NOT_SAVINGS_ACCOUNT = "This is not a savings account";
    public static String NOT_FOR_SAVINGS_ACCOUNT = "This kind of report is not supported for a saving account";
    public static String NOT_FOUND = "Account not found";
    public static String DELETED = "Account deleted";
    public static String FUNDS_REMAINING = "Account couldn't be deleted - see org.poo.transactions for details";
    public static String INSUFFICIENT_FUNDS = "Insufficient funds";
    public static int WARNING_THRESHOLD = 30;

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
     * Here there should also be something like
     * public void addTransaction(AddIntRateTransaction transaction)
     * that specifies how AddIntRateTransaction interacts with different account types.
     * However, this transaction doesn't appear anywhere, even tho it is specified
     * that the addInterest command may generate a transaction. This is left to be implemented
     * in case the functionality will be set clear
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
    public void addTransaction(ChangeIntRateTransaction transaction) {
        transactions.add(transaction);
    }
    public void addTransaction(PayOnlineTransaction transaction) {
        transactions.add(transaction);
    }


    public ObjectNode generateReport(int startTimestamp, int endTimestamp) {
        ObjectNode reportNode = Utils.mapper.createObjectNode();
        reportNode.put("balance", getBalance());
        reportNode.put("currency", getCurrency());
        reportNode.put("IBAN", getIban());
        reportNode.set("transactions", generateReportTransaction(startTimestamp, endTimestamp));

        return reportNode;
    }

    /**
     * Method used to add interest to an account
     * @return null if no error occurred or an appropriate error otherwise
     */
    public abstract String addInterest();

    /**
     * This method is used to change the interest of an account
     * @param newInterestRate the new interestRate
     * @return null if no error occurred or an appropriate error otherwise
     *         (e.g. the account wasn't a saving account)
     */
    public abstract String changeInterest(double newInterestRate);
    protected abstract ArrayNode generateReportTransaction(int startTimestamp, int endTimestamp);
    public abstract ObjectNode spendingsReport(int startTimestamp, int endTimestamp);



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
}
