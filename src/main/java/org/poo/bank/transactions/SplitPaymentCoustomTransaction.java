package org.poo.bank.transactions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.accounts.Account;
import org.poo.utils.Utils;
import java.util.List;

public class SplitPaymentCoustomTransaction extends Transaction {
    private final String currency;
    private final List<Double> amounts;
    private final String splitPaymentType;
    private final List<String> involvedAccounts;
    private final String error;


    public SplitPaymentCoustomTransaction(final Type transactionType, final int timestamp,
                                          final String description, final String currency,
                                          final List<Double> amounts, final String splitPaymentType,
                                          final List<String> involvedAccounts, final String error) {
        super(transactionType, timestamp, description);
        this.currency = currency;
        this.amounts = amounts;
        this.splitPaymentType = splitPaymentType;
        this.involvedAccounts = involvedAccounts;
        this.error = error;
    }

    @Override
    public void addTransactionToAccount(Account account) {
        account.addTransaction(this);
    }

    @Override
    public ObjectNode toJson() {
        ObjectNode toJson = Utils.MAPPER.createObjectNode();

        toJson.put("timestamp", getTimestamp());
        toJson.put("description", getDescription());
        toJson.put("splitPaymentType", splitPaymentType);
        toJson.put("currency", currency);

        ArrayNode amountForUsers = Utils.MAPPER.createArrayNode();
        for (Double amount : amounts) {
            amountForUsers.add(amount);
        }
        toJson.set("amountForUsers", amountForUsers);

        ArrayNode involvedAccountsForUsers = Utils.MAPPER.createArrayNode();
        for (String involvedAccount : involvedAccounts) {
            involvedAccountsForUsers.add(involvedAccount);
        }
        toJson.set("involvedAccounts", involvedAccountsForUsers);
        if (error != null) {
            toJson.put("error", error);
        }

        return toJson;
    }
}
