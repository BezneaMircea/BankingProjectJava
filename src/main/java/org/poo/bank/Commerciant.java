package org.poo.bank;


import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.poo.bank.accounts.Account;
import org.poo.commands.transactions.Transaction;
import org.poo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public final class Commerciant implements Comparable<Commerciant> {
    private final Account belongsToAccount;
    private final String name;
    private final List<Payment> receivedPayments;

    public static class Payment {
        private final double amount;
        private final int timestamp;

        public Payment(final double amount, final int timestamp) {
            this.amount = amount;
            this.timestamp = timestamp;
        }
    }


    public Commerciant(final Account belongsToAccount, final String name) {
        this.belongsToAccount = belongsToAccount;
        this.name = name;
        receivedPayments = new ArrayList<>();
    }


    @Override
    public int compareTo(Commerciant o) {
        return name.compareTo(o.getName());
    }

    public ObjectNode commerciantToJson(int startingTimestamp, int endingTimestamp) {
        double totalSumPayed = sumTransactions(startingTimestamp, endingTimestamp);

        if (totalSumPayed == 0)
            return null;

        ObjectNode nodeToReturn = Utils.mapper.createObjectNode();
        nodeToReturn.put("commerciant", name);
        nodeToReturn.put("total", totalSumPayed);

        return nodeToReturn;
    }

    private double sumTransactions(int startingTimestamp, int endingTimestamp) {
        double sum = 0;
        for (Payment payment : receivedPayments) {
            if (payment.timestamp >= startingTimestamp && payment.timestamp <= endingTimestamp) {
                sum += payment.amount;
            } else if (payment.timestamp > endingTimestamp) {
                break;
            }
        }

        return sum;
    }

}

