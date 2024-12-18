package org.poo.bank.accounts;


import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.poo.commands.transactions.Transaction;
import org.poo.utils.Utils;

@Getter
@Setter
public final class EconomyAccount extends Account {
    private double interestRate;

    public EconomyAccount(final String ownerEmail, final String currency,
                          final String accountType, final double interestRate) {
        super(ownerEmail, currency, accountType);
        this.interestRate = interestRate;
    }


    @Override
    public String addInterest() {
        setInterestRate(getInterestRate() + interestRate);
        return null;
    }

    @Override
    public String changeInterest(double interestRate) {
        this.interestRate = interestRate;
        return null;
    }

    @Override
    public ArrayNode generateReport(int startTimestamp, int endTimestamp) {
        ArrayNode reportArray = Utils.mapper.createArrayNode();
        for (Transaction transaction : getTransactions()) {
            if (transaction.getTimestamp() >= startTimestamp && transaction.getTimestamp() <= endTimestamp) {
                reportArray.add(transaction.toJson());
            } else if (transaction.getTimestamp() > endTimestamp)
                break;
        }

        return reportArray;
    }

    @Override
    public ObjectNode spendingsReport(int startTimestamp, int endTimestamp) {
        ObjectNode errorNode = Utils.mapper.createObjectNode();
        errorNode.put("error", Account.NOT_FOR_SAVINGS_ACCOUNT);
        return errorNode;
    }


}
