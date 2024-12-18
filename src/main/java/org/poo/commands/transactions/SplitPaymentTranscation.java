package org.poo.commands.transactions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.accounts.Account;
import org.poo.utils.Utils;

import java.util.List;

public class SplitPaymentTranscation extends Transaction {
    public static String SPLIT_PAYMENT_ERROR = "Account %s has insufficient funds for a split payment.";
    public static String SPLIT_PAYMENT_DESCRIPTION = "Split payment of %.2f %s";

    private final String currency;
    private final double amount;
    private final List<String> involvedAccounts;
    private final String error;

    public SplitPaymentTranscation(final int timestamp, final String description,
                                   final String currency, final double amount,
                                   final List<String> involvedAccounts, final String error) {
        super(timestamp, description);
        this.currency = currency;
        this.amount = amount;
        this.involvedAccounts = involvedAccounts;
        this.error = error;
    }


    @Override
    public ObjectNode toJson() {
        ObjectNode toJson = Utils.mapper.createObjectNode();

        toJson.put("timestamp", getTimestamp());
        toJson.put("description", getDescription());
        toJson.put("currency", currency);
        toJson.put("amount", amount);

        ArrayNode involvedAccountsNode = Utils.mapper.createArrayNode();
        for (String account : involvedAccounts) {
            involvedAccountsNode.add(account);
        }
        toJson.set("involvedAccounts", involvedAccountsNode);

        if (error != null)
            toJson.put("error", error);

        return toJson;
    }

    public void addTransactionToAccount(final Account account) {
        account.addTransaction(this);
    }
}
