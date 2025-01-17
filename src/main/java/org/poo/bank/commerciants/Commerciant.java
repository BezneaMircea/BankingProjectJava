package org.poo.bank.commerciants;


import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.poo.bank.accounts.Account;
import org.poo.utils.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * Class used to represent a Commerciant
 */
@Getter
@Setter
public final class Commerciant implements Comparable<Commerciant> {
    private final Account belongsToAccount;
    private final String name;
    private final List<Payment> receivedPayments;
    /**
     * Class used to represent a payment
     */
    public static class Payment {
        private final double amount;
        private final int timestamp;

        /**
         * Constructor for the payment class
         *
         * @param amount    the paid sum
         * @param timestamp the timestamp when the payment occurred
         */
        public Payment(final double amount, final int timestamp) {
            this.amount = amount;
            this.timestamp = timestamp;
        }
    }


    /**
     * Constructor for a commerciant
     *
     * @param belongsToAccount the account that this commerciant belongs to
     * @param name             the name of the commerciant
     */
    public Commerciant(final Account belongsToAccount, final String name) {
        this.belongsToAccount = belongsToAccount;
        this.name = name;
        receivedPayments = new ArrayList<>();
    }


    @Override
    public int compareTo(final Commerciant o) {
        return name.compareTo(o.getName());
    }

    /**
     * Method used to write the payments towards this commerciant in a
     * time interval
     *
     * @param startingTimestamp start of the time interval
     * @param endingTimestamp   end o the time interval
     * @return null if no sum was paid to this commerciant, an appropriate ObjectNode otherwise
     */
    public ObjectNode commerciantToJson(final int startingTimestamp, final int endingTimestamp) {
        double totalSumPayed = sumTransactions(startingTimestamp, endingTimestamp);

        if (totalSumPayed == 0) {
            return null;
        }

        ObjectNode nodeToReturn = Utils.MAPPER.createObjectNode();
        nodeToReturn.put("commerciant", name);
        nodeToReturn.put("total", Utils.approximateToFourthDecimal(totalSumPayed));

        return nodeToReturn;
    }

    private double sumTransactions(final int startingTimestamp, final int endingTimestamp) {
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

