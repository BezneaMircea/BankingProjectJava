package org.poo.bank.commerciants;


import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.poo.bank.accounts.Account;
import org.poo.bank.commerciants.commerciant_strategies.CashBackStrategy;
import org.poo.bank.users.users_strategy.UserStrategy;
import org.poo.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Class used to represent a Commerciant
 */
@Getter
@Setter
public abstract class Commerciant implements Comparable<Commerciant> {
    public static final String MAIN_CURRENCY = "RON";


    private final String name;
    private final int id;
    private final String account;
    private final Type type;
    private final CashBackStrategy cashBackStrategy;
    private final Map<Account, Integer> nrTransactionsByAccount;
    private final Map<Account, List<Payment>> receivedPaymentsFromAccount;


    @Getter
    public enum Type {
        CLOTHES("clothes"),
        TECH("tech"),
        FOOD("food");

        private final String value;

        Type(final String value) {
            this.value = value;
        }

        public String getString() {
            return value;
        }

        /**
         * returns the associated Type of input string;
         *
         * @param input the input string
         * @return the associated Type
         */
        public static Type fromString(final String input) {
            for (Type type : Type.values()) {
                if (type.value.equalsIgnoreCase(input)) {
                    return type;
                }
            }

            throw new IllegalArgumentException("Not a valid commerciant type: " + input);
        }
    }
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
     * @param name the name of the commerciant
     * @param id the id of the commerciant
     * @param type the type of the commerciant
     * @see Type
     * @param cashBackStrategy the strategy of the commerciant
     * @see CashBackStrategy
     */
    public Commerciant(final String name, final int id, final String account,
                       final Type type, final CashBackStrategy cashBackStrategy) {
        this.name = name;
        this.id = id;
        this.account = account;
        this.type = type;
        this.cashBackStrategy = cashBackStrategy;
        nrTransactionsByAccount = new HashMap<>();
        receivedPaymentsFromAccount = new HashMap<>();
    }

    public abstract void acceptCashback(UserStrategy ownerStrategy,
                                        Account account, double amount, double conversionRate);

    public void incrementAccountTransactions(Account account) {
        nrTransactionsByAccount.merge(account, 1, Integer::sum);
    }

    public int getNrAccountTransactions(Account account) {
        Integer nrTransactions = nrTransactionsByAccount.get(account);
        if (nrTransactions == null)
            return 0;

        return nrTransactions;
    }

    @Override
    public int compareTo(final Commerciant o) {
        return name.compareTo(o.getName());
    }

    /**
     * Method used to write the payments by account towards this commerciant in a
     * time interval
     *
     * @param account the account that made the payments
     * @param startingTimestamp start of the time interval
     * @param endingTimestamp   end o the time interval
     * @return null if no sum was paid to this commerciant, an appropriate ObjectNode otherwise
     */
    public ObjectNode commerciantToJson(Account account, final int startingTimestamp, final int endingTimestamp) {
        List<Payment> payments = receivedPaymentsFromAccount.get(account);
        double totalSumPayed = sumPayments(payments, startingTimestamp, endingTimestamp);

        if (totalSumPayed == 0) {
            return null;
        }

        ObjectNode nodeToReturn = Utils.MAPPER.createObjectNode();
        nodeToReturn.put("commerciant", name);
        nodeToReturn.put("total", Utils.approximateToFourthDecimal(totalSumPayed));

        return nodeToReturn;
    }

    private double sumPayments(final List<Payment> payments, final int startingTimestamp, final int endingTimestamp) {
        double sum = 0;
        for (Payment payment : payments) {
            if (payment.timestamp >= startingTimestamp && payment.timestamp <= endingTimestamp) {
                sum += payment.amount;
            } else if (payment.timestamp > endingTimestamp) {
                break;
            }
        }

        return sum;
    }

}

