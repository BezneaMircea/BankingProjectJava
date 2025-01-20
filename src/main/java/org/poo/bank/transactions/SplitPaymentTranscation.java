package org.poo.bank.transactions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.accounts.Account;
import org.poo.utils.Utils;

import java.util.List;


public final class SplitPaymentTranscation extends Transaction {
    public static final String SPLIT_PAYMENT_DESCRIPTION = "Split payment of %.2f %s";

    private final String currency;
    private final double amount;
    private final String paymentType;
    private final List<String> involvedAccounts;
    private final String error;

    public SplitPaymentTranscation(final Type transactionType, final int timestamp,
                                   final String description, final String currency,
                                   final double amount, final List<String> involvedAccounts,
                                   final String paymentType, final String error) {
        super(transactionType, timestamp, description);
        this.currency = currency;
        this.amount = amount;
        this.paymentType = paymentType;
        this.involvedAccounts = involvedAccounts;
        this.error = error;
    }


    @Override
    public ObjectNode toJson() {
        ObjectNode toJson = Utils.MAPPER.createObjectNode();

        toJson.put("timestamp", getTimestamp());
        toJson.put("description", getDescription());
        toJson.put("currency", currency);
        toJson.put("amount", Utils.approximateToFourthDecimal(amount));

        ArrayNode involvedAccountsNode = Utils.MAPPER.createArrayNode();
        for (String account : involvedAccounts) {
            involvedAccountsNode.add(account);
        }
        toJson.set("involvedAccounts", involvedAccountsNode);
        toJson.put("splitPaymentType", paymentType);

        if (error != null) {
            toJson.put("error", error);
        }

        return toJson;
    }

    @Override
    public void addTransactionToAccount(final Account account) {
        account.addTransaction(this);
    }
}
