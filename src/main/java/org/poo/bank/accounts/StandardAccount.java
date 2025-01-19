package org.poo.bank.accounts;


import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.poo.bank.Bank;
import org.poo.bank.commerciants.AccountBonuses;
import org.poo.bank.commerciants.Commerciant;
import org.poo.bank.transactions.PayOnlineTransaction;
import org.poo.bank.transactions.SendMoneyTransaction;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.transactions.TransactionInput;
import org.poo.bank.users.User;
import org.poo.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Class used to represent a StandardAccount
 */
@Getter
@Setter
public final class StandardAccount extends Account {
    private final List<Transaction> onlineTransactions;
    private final List<Commerciant> commerciants;
    private Double spendingThresholdAmount;

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
        spendingThresholdAmount = 0.0;
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
            ObjectNode toAdd = commerciant.commerciantToJson(this, startTimestamp, endTimestamp);
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

        Commerciant transactionCommerciant = transaction.getCommerciant();
        addTransactionHelper(transactionCommerciant, transaction.getAmount(), transaction.getTimestamp());
    }

    public void addTransaction(final SendMoneyTransaction transaction) {
        if (transaction == null) {
            return;
        }

        if (transaction.getCommerciant() == null) {
            getTransactions().add(transaction);
            return;
        }

        onlineTransactions.add(transaction);
        Commerciant transactionCommerciant = transaction.getCommerciant();
        addTransactionHelper(transactionCommerciant, transaction.getAmount(), transaction.getTimestamp());
    }

    private void
    addTransactionHelper(final Commerciant transactionCommerciant,
                         final double amount, final int timestamp) {

        Commerciant.Payment payment = new Commerciant.Payment(amount, timestamp);

        for (Commerciant commerciant : commerciants) {
            if (commerciant.equals(transactionCommerciant)) {
                commerciant.getReceivedPaymentsFromAccount().get(this).add(payment);
                return;
            }
        }

        /// If it does not exist
        List<Commerciant.Payment> paymentList = new ArrayList<>();
        paymentList.add(payment);

        transactionCommerciant.getReceivedPaymentsFromAccount().put(this, paymentList);
        commerciants.add(transactionCommerciant);
    }

    /// amount is without commission
    public void transferToCommerciant(Bank bank, double amount, int timestamp, Commerciant commerciant) {
        User accountOwner = bank.getUser(getOwnerEmail());
        double conversionRate = bank.getRate(getCurrency(), Commerciant.MAIN_CURRENCY);

        double totalSumWithCommission = accountOwner.getStrategy().calculateSumWithComision(amount);
        setBalance(getBalance() - totalSumWithCommission);
        commerciant.acceptCashback(accountOwner.getStrategy(), this, amount, conversionRate);
    }


}
