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
        ObjectNode errorNode = Utils.mapper.createObjectNode();
        errorNode.put("error", Account.NOT_FOR_SAVINGS_ACCOUNT);
        return errorNode;
    }


}
