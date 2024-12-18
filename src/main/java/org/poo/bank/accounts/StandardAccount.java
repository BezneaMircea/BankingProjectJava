package org.poo.bank.accounts;


import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jdk.jshell.execution.Util;
import lombok.Getter;
import org.poo.bank.Commerciant;
import org.poo.commands.transactions.PayOnlineTransaction;
import org.poo.commands.transactions.Transaction;
import org.poo.utils.Utils;

import java.util.*;

import static java.lang.Math.round;

@Getter
public final class StandardAccount extends Account {
    private final List<Transaction> onlineTransactions;
    private final List<Commerciant> commerciants;

    public StandardAccount(String ownerEmail, String currency, String accountType) {
        super(ownerEmail, currency, accountType);
        onlineTransactions = new ArrayList<>();
        commerciants = new ArrayList<>();
    }

    @Override
    public String addInterest() {
        return NOT_SAVINGS_ACCOUNT;
    }

    @Override
    public String changeInterest(double interestRate) {
        return NOT_SAVINGS_ACCOUNT;
    }

    @Override
    public ObjectNode generateReport(int startTimestamp, int endTimestamp) {
        ObjectNode reportNode = Utils.mapper.createObjectNode();
        reportNode.put("balance", getBalance());
        reportNode.put("currency", getCurrency());
        reportNode.put("IBAN", getIban());

        ArrayNode transactionArray = Utils.mapper.createArrayNode();
        for (Transaction transaction : getTransactions()) {
            if (transaction.getTimestamp() >= startTimestamp && transaction.getTimestamp() <= endTimestamp) {
                transactionArray.add(transaction.toJson());
            } else if (transaction.getTimestamp() > endTimestamp)
                break;
        }
        reportNode.set("transactions", transactionArray);

        return reportNode;
    }

    @Override
    public ObjectNode spendingsReport(int startTimestamp, int endTimestamp) {
        ObjectNode spendingsReportNode = Utils.mapper.createObjectNode();
        spendingsReportNode.put("IBAN", getIban());
        spendingsReportNode.put("balance", getBalance());
        spendingsReportNode.put("currency", getCurrency());

        ArrayNode transactionsArray = Utils.mapper.createArrayNode();
        for (Transaction transaction : onlineTransactions) {
            if (transaction.getTimestamp() >= startTimestamp && transaction.getTimestamp() <= endTimestamp) {
                transactionsArray.add(transaction.toJson());
            } else if (transaction.getTimestamp() > endTimestamp)
                break;
        }
        spendingsReportNode.set("transactions", transactionsArray);

        Collections.sort(commerciants);
        ArrayNode commerciantsArray = Utils.mapper.createArrayNode();
        for (Commerciant commerciant : commerciants) {
            ObjectNode toAdd = commerciant.commerciantToJson(startTimestamp, endTimestamp);
            if (toAdd != null)
                commerciantsArray.add(toAdd);
        }
        spendingsReportNode.set("commerciants", commerciantsArray);

        return spendingsReportNode;
    }

    @Override
    public void addTransaction(PayOnlineTransaction transaction) {
        if (transaction == null)
            return;

        if (transaction.getError() != null)
            return;

        getTransactions().add(transaction);
        onlineTransactions.add(transaction);

        for (Commerciant commerciant : commerciants) {
            if (commerciant.getName().equals(transaction.getCommerciant())) {
                Commerciant.Payment payment = new Commerciant.Payment(transaction.getAmount(), transaction.getTimestamp());
                commerciant.getReceivedPayments().add(payment);
                return;
            }
        }

        Commerciant commerciantToAdd = new Commerciant(this, transaction.getCommerciant());
        Commerciant.Payment payment = new Commerciant.Payment(transaction.getAmount(), transaction.getTimestamp());;
        commerciantToAdd.getReceivedPayments().add(payment);
        commerciants.add(commerciantToAdd);
    }

}
