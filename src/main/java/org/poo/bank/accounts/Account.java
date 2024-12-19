package org.poo.bank.accounts;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.poo.bank.accounts.cards.Card;
import org.poo.bank.commands.transactions.*;
import org.poo.utils.Utils;


import java.util.ArrayList;
import java.util.List;


/**
 * Class used to represent a bank account
 */
@Getter
@Setter
public abstract class Account {
    public static final String NOT_SAVINGS_ACCOUNT;
    public static final String NOT_FOR_SAVINGS_ACCOUNT;
    public static final String NOT_FOUND;
    public static final String DELETED;
    public static final String CANT_DELETE;
    public static final String INSUFFICIENT_FUNDS;
    public static final String ACCOUNT_CREATED;
    public static final String FUNDS_REMAINING;
    public static final String SPLIT_PAYMENT_ERROR;
    public static final int WARNING_THRESHOLD;

    static {
        NOT_SAVINGS_ACCOUNT = "This is not a savings account";
        NOT_FOR_SAVINGS_ACCOUNT = "This kind of report is not supported for a saving account";
        NOT_FOUND = "Account not found";
        DELETED = "Account deleted";
        CANT_DELETE = "Account couldn't be deleted - see org.poo.transactions for details";
        INSUFFICIENT_FUNDS = "Insufficient funds";
        ACCOUNT_CREATED = "New account created";
        FUNDS_REMAINING = "Account couldn't be deleted - there are funds remaining";
        SPLIT_PAYMENT_ERROR = "Account %s has insufficient funds for a split payment.";
        WARNING_THRESHOLD = 30;
    }

    @Getter
    public enum Type {
        CLASSIC("classic"),
        SAVINGS("savings");

        private final String value;

        Type(final String value) {
            this.value = value;
        }

        /**
         * returns the associated Type of input string;
         *
         * @param input the input string
         * @return the associated Type
         */
        public static Type fromString(final String input) {
            for (Type type : Type.values()) {
                if (type.value.equalsIgnoreCase(input)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Not a valid account type: " + input);
        }

    }

    private final String ownerEmail;
    private final String currency;
    private final Type accountType;
    private final String iban;
    private double balance;
    private double minBalance;
    private final List<Card> cards;
    private final List<Transaction> transactions;


    /**
     * Constructor for the account class
     *
     * @param ownerEmail  email of the owner
     * @param currency    currency of the account
     * @param accountType the account type
     */
    public Account(final String ownerEmail, final String currency, final Type accountType) {
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
     *
     * @param accountToTransfer the account that receives the money
     * @param amountToTransfer  the amount to transfer from the current account
     * @param amountToReceive   the amount that the receiver account gets (the converted amount)
     */
    public void transfer(final Account accountToTransfer,
                         final double amountToTransfer,
                         final double amountToReceive) {
        balance -= amountToTransfer;
        accountToTransfer.balance += amountToReceive;
    }


    /**
     * Method used to add a transaction to an account. This must be overridden
     * since different accounts interact differently with different transactions
     * (double dispatch)
     * Delete {
     * Here there should also be something like
     * public void addTransaction(AddIntRateTransaction transaction)
     * that specifies how AddIntRateTransaction interacts with different account types.
     * However, this transaction doesn't appear anywhere, even tho it is specified
     * that the addInterest command may generate a transaction. This is left to be implemented
     * in case the functionality will be set clear.} if implemented == true
     */
    public void addTransaction(final Transaction transaction) {
        transactions.add(transaction);
    }

    /**
     * for coding style
     */
    public void addTransaction(final ChangeIntRateTransaction transaction) {
        transactions.add(transaction);
    }

    /**
     * for coding style
     */
    public void addTransaction(final PayOnlineTransaction transaction) {
        transactions.add(transaction);
    }


    /**
     * Method used to generate a report on an account. Internally it calls
     * the generateReportTransaction method that is abstract in this class
     * and left to be implemented by each account since they might generate reports
     * differently. If a pattern occurs in at least 2 classes consider deleting the
     * abstract identifier and implement a general method in this class
     */
    public ObjectNode generateReport(final int startTimestamp, final int endTimestamp) {
        ObjectNode reportNode = Utils.MAPPER.createObjectNode();
        reportNode.put("balance", getBalance());
        reportNode.put("currency", getCurrency());
        reportNode.put("IBAN", getIban());
        reportNode.set("transactions", generateReportTransaction(startTimestamp, endTimestamp));

        return reportNode;
    }

    /**
     * Method used to add interest to an account
     *
     * @return null if no error occurred or an appropriate error otherwise
     */
    public abstract String addInterest();

    /**
     * This method is used to change the interest of an account
     *
     * @param newInterestRate the new interestRate
     * @return null if no error occurred or an appropriate error otherwise
     * (e.g. the account wasn't a saving account)
     */
    public abstract String changeInterest(double newInterestRate);

    /**
     * This is used to generateReportTransactions. Since different accounts generate
     * different reports (e.g. savings accounts don t take all the transactions in consideration)
     */
    protected abstract ArrayNode
    generateReportTransaction(int startTimestamp, int endTimestamp);

    /**
     * This method is used to generate a spending report.
     * Since different accounts generate different spendingsReports
     * (e.g. savings accounts don t take all the transactions in consideration)
     *
     * @return an objectNode representing the report
     */
    public abstract ObjectNode spendingsReport(int startTimestamp, int endTimestamp);


    /**
     * Method used to check if this account owns a card
     *
     * @param card card to check
     * @return true if it exists, false otherwise
     */
    public boolean hasCard(final Card card) {
        return cards.contains(card);
    }

    /**
     * Method used to add a card to this account
     *
     * @param card card to be added
     */
    public void addCard(final Card card) {
        cards.add(card);
    }

    /**
     * Method used to remove a card from this account
     *
     * @param card card to be removed
     */
    public void removeCard(final Card card) {
        cards.remove(card);
    }

    /**
     * Method used to write an account to an ObjectNode
     *
     * @return the ObjectNode corresponding to this account
     */
    public ObjectNode accountToObjectNode() {
        ObjectNode accountNode = Utils.MAPPER.createObjectNode();
        accountNode.put("IBAN", iban);
        accountNode.put("balance", balance);
        accountNode.put("currency", currency);
        accountNode.put("type", accountType.getValue());
        accountNode.set("cards", writeCards());

        return accountNode;
    }


    private ArrayNode writeCards() {
        ArrayNode cardsNode = Utils.MAPPER.createArrayNode();
        for (Card card : cards) {
            cardsNode.add(card.cardToObjectNode());
        }

        return cardsNode;
    }
}
