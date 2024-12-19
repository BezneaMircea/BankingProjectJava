package org.poo.bank.accounts;


import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.poo.commands.transactions.ChangeIntRateTransaction;
import org.poo.commands.transactions.Transaction;
import org.poo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to represent an EconomyAccount
 */
@Getter
@Setter
public final class EconomyAccount extends Account {
    private double interestRate;
    private final List<Transaction> interestTransactions;

    /**
     * Constructor for the EconomyAccount class
     * @param ownerEmail email of the owner
     * @param currency currency of the account
     * @param accountType the account type
     * @param interestRate the interest rate of the account
     */
    public EconomyAccount(final String ownerEmail, final String currency,
                          final Type accountType, final double interestRate) {
        super(ownerEmail, currency, accountType);
        this.interestRate = interestRate;
        interestTransactions = new ArrayList<>();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String addInterest() {
        setInterestRate(getInterestRate() + interestRate);
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String changeInterest(double interestRate) {
        this.interestRate = interestRate;
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ArrayNode generateReportTransaction(int startTimestamp, int endTimestamp) {
        ArrayNode transactionArray = Utils.mapper.createArrayNode();
        for (Transaction transaction : interestTransactions) {
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
        ObjectNode errorNode = Utils.mapper.createObjectNode();
        errorNode.put("error", Account.NOT_FOR_SAVINGS_ACCOUNT);
        return errorNode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTransaction(ChangeIntRateTransaction transaction) {
        getTransactions().add(transaction);
        interestTransactions.add(transaction);
    }

}
