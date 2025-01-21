package org.poo.bank.accounts;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessAccount extends Account {
    public static final String NOT_AUTHORIZED;
    public static final double INITIAL_DEPOSIT_LIMIT = 500.0;
    public static final double INITIAL_SPENDING_LIMIT = 500.0;
    private Double spendingThresholdAmount;

    static {
        NOT_AUTHORIZED = "You are not authorized to make this transaction.";
    }

    private Double depositLimit;
    private Double spendingLimit;

    public BusinessAccount(final String ownerEmail, final String currency, final Type accountType,
                           final double depositLimit, final double spendingLimit) {
        super(ownerEmail, currency, accountType);
        this.depositLimit = depositLimit;
        this.spendingLimit = spendingLimit;
        spendingThresholdAmount = 0.0;
    }

    @Override
    public String addInterest() {
        return "";
    }

    @Override
    public String changeInterest(final double newInterestRate) {
        return "";
    }

    @Override
    protected ArrayNode generateReportTransaction(final int startTimestamp,
                                                  final int endTimestamp) {
        return null;
    }

    @Override
    public ObjectNode spendingsReport(int startTimestamp, int endTimestamp) {
        return null;
    }
}
