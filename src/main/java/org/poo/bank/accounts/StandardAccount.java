package org.poo.bank.accounts;


import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import org.poo.bank.commerciants.Commerciant;
import org.poo.bank.commands.transactions.PayOnlineTransaction;
import org.poo.bank.commands.transactions.Transaction;
import org.poo.utils.Utils;

import java.util.*;

/**
 * Class used to represent a StandardAccount
 */
@Getter
public final class StandardAccount extends Account {
    private final List<Transaction> onlineTransactions;
    private final List<Commerciant> commerciants;

    /**
     * Constructor used to the StandardAccount class
     * @param ownerEmail email of the owner
     * @param currency currency of the account
     * @param accountType the accountType
     */
    public StandardAccount(final String ownerEmail, final String currency, final Type accountType) {
        super(ownerEmail, currency, accountType);
        onlineTransactions = new ArrayList<>();
        commerciants = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String addInterest() {
        return NOT_SAVINGS_ACCOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String changeInterest(double interestRate) {
        return NOT_SAVINGS_ACCOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ArrayNode generateReportTransaction(int startTimestamp, int endTimestamp) {
        ArrayNode transactionArray = Utils.MAPPER.createArrayNode();
        for (Transaction transaction : getTransactions()) {
            if (transaction.getTimestamp() >= startTimestamp && transaction.getTimestamp() <= endTimestamp) {
                transactionArray.add(transaction.toJson());
            } else if (transaction.getTimestamp() > endTimestamp)
                break;
        }

        return transactionArray;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectNode spendingsReport(int startTimestamp, int endTimestamp) {
        ObjectNode spendingsReportNode = Utils.MAPPER.createObjectNode();
        spendingsReportNode.put("IBAN", getIban());
        spendingsReportNode.put("balance", getBalance());
        spendingsReportNode.put("currency", getCurrency());

        ArrayNode transactionsArray = Utils.MAPPER.createArrayNode();
        for (Transaction transaction : onlineTransactions) {
            if (transaction.getTimestamp() >= startTimestamp && transaction.getTimestamp() <= endTimestamp) {
                transactionsArray.add(transaction.toJson());
            } else if (transaction.getTimestamp() > endTimestamp)
                break;
        }
        spendingsReportNode.set("transactions", transactionsArray);

        Collections.sort(commerciants);
        ArrayNode commerciantsArray = Utils.MAPPER.createArrayNode();
        for (Commerciant commerciant : commerciants) {
            ObjectNode toAdd = commerciant.commerciantToJson(startTimestamp, endTimestamp);
            if (toAdd != null)
                commerciantsArray.add(toAdd);
        }
        spendingsReportNode.set("commerciants", commerciantsArray);

        return spendingsReportNode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTransaction(PayOnlineTransaction transaction) {
        if (transaction == null)
            return;

        if (transaction.getError() != null)
            return;

        getTransactions().add(transaction);
        onlineTransactions.add(transaction);

        /// If the commerciant exists in the list just add the new payment to it
        for (Commerciant commerciant : commerciants) {
            if (commerciant.getName().equals(transaction.getCommerciant())) {
                Commerciant.Payment payment = new Commerciant.Payment(transaction.getAmount(), transaction.getTimestamp());
                commerciant.getReceivedPayments().add(payment);
                return;
            }
        }

        /// If it does not exist create it and add the payment to the list
        Commerciant commerciantToAdd = new Commerciant(this, transaction.getCommerciant());
        Commerciant.Payment payment = new Commerciant.Payment(transaction.getAmount(), transaction.getTimestamp());;
        commerciantToAdd.getReceivedPayments().add(payment);
        commerciants.add(commerciantToAdd);
    }

}
