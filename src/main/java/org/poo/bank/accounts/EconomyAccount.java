package org.poo.bank.accounts;


import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.poo.bank.transactions.ChangeIntRateTransaction;
import org.poo.bank.transactions.Transaction;
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
     *
     * @param ownerEmail   email of the owner
     * @param currency     currency of the account
     * @param accountType  the account type
     * @param interestRate the interest rate of the account
     */
    public EconomyAccount(final String ownerEmail, final String currency,
                          final Type accountType, final double interestRate) {
        super(ownerEmail, currency, accountType);
        this.interestRate = interestRate;
        interestTransactions = new ArrayList<>();
    }


    @Override
    public String addInterest() {
        setInterestRate(getInterestRate() + interestRate);
        return null;
    }

    @Override
    public String changeInterest(final double newInterestRate) {
        interestRate = newInterestRate;
        return null;
    }

    @Override
    protected ArrayNode
    generateReportTransaction(final int startTimestamp, final int endTimestamp) {
        return transactionsToObjectNode(interestTransactions, startTimestamp, endTimestamp);
    }


    @Override
    public ObjectNode spendingsReport(final int startTimestamp, final int endTimestamp) {
        ObjectNode errorNode = Utils.MAPPER.createObjectNode();
        errorNode.put("error", Account.NOT_FOR_SAVINGS_ACCOUNT);
        return errorNode;
    }

    @Override
    public void addTransaction(final ChangeIntRateTransaction transaction) {
        getTransactions().add(transaction);
        interestTransactions.add(transaction);
    }

}
