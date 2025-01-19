package org.poo.bank.accounts;


import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import org.poo.bank.commerciants.AccountBonuses;
import org.poo.bank.commerciants.Commerciant;
import org.poo.bank.transactions.PayOnlineTransaction;
import org.poo.bank.transactions.Transaction;
import org.poo.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Class used to represent a StandardAccount
 */
@Getter
public final class StandardAccount extends Account {
    private final List<Transaction> onlineTransactions;
    private final List<Commerciant> commerciants;
    private final AccountBonuses bonuses;

    /**
     * Constructor used to the StandardAccount class
     *
     * @param ownerEmail  email of the owner
     * @param currency    currency of the account
     * @param accountType the accountType
     */
    public StandardAccount(final String ownerEmail, final String currency, final Type accountType) {
        super(ownerEmail, currency, accountType);
        onlineTransactions = new ArrayList<>();
        commerciants = new ArrayList<>();
        bonuses = new AccountBonuses();
    }


    @Override
    public String addInterest() {
        return NOT_SAVINGS_ACCOUNT;
    }

    @Override
    public String changeInterest(final double interestRate) {
        return NOT_SAVINGS_ACCOUNT;
    }

    @Override
    protected ArrayNode
    generateReportTransaction(final int startTimestamp, final int endTimestamp) {
        return transactionsToArrayNode(getTransactions(), startTimestamp, endTimestamp);
    }

    @Override
    public ObjectNode spendingsReport(final int startTimestamp, final int endTimestamp) {
        ObjectNode spendingsReportNode = Utils.MAPPER.createObjectNode();
        spendingsReportNode.put("IBAN", getIban());
        spendingsReportNode.put("balance", Utils.roundIfClose(getBalance()));
        spendingsReportNode.put("currency", getCurrency());
        spendingsReportNode.set("transactions", transactionsToArrayNode(onlineTransactions,
                                                                         startTimestamp,
                                                                         endTimestamp));

        Collections.sort(commerciants);
        ArrayNode commerciantsArray = Utils.MAPPER.createArrayNode();
        for (Commerciant commerciant : commerciants) {
            ObjectNode toAdd = commerciant.commerciantToJson(startTimestamp, endTimestamp);
            if (toAdd != null) {
                commerciantsArray.add(toAdd);
            }
        }
        spendingsReportNode.set("commerciants", commerciantsArray);

        return spendingsReportNode;
    }

    @Override
    public void addTransaction(final PayOnlineTransaction transaction) {
        if (transaction == null) {
            return;
        }

        if (transaction.getError() != null) {
            return;
        }

        getTransactions().add(transaction);
        onlineTransactions.add(transaction);

        /// If the commerciant exists in the list just add the new payment to it
        for (Commerciant commerciant : commerciants) {
            if (commerciant.getName().equals(transaction.getCommerciant())) {
                Commerciant.Payment payment = new Commerciant.Payment(transaction.getAmount(),
                                                                      transaction.getTimestamp());
                commerciant.getReceivedPayments().add(payment);
                return;
            }
        }

        /// If it does not exist create it and add the payment to the list
        Commerciant commerciantToAdd = new Commerciant(transaction.getCommerciant());
        Commerciant.Payment payment = new Commerciant.Payment(transaction.getAmount(),
                                                              transaction.getTimestamp());
        commerciantToAdd.getReceivedPayments().add(payment);
        commerciants.add(commerciantToAdd);
    }

}
