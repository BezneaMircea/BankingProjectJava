package org.poo.bank.transactions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.accounts.Account;
import org.poo.utils.Utils;

public final class AddInterestTransaction extends Transaction {
    public static final String INTEREST_INCOME = "Interest rate income";


    private final double amount;
    private final String currency;

    public AddInterestTransaction(final Type type, final double amount, final String currency,
                                  final String description, final int timestamp) {
        super(type, timestamp, description);
        this.amount = amount;
        this.currency = currency;
    }


    @Override
    public void addTransactionToAccount(final Account account) {
        account.addTransaction(this);
    }

    @Override
    public ObjectNode toJson() {
        ObjectNode toJson = Utils.MAPPER.createObjectNode();

        toJson.put("amount", amount);
        toJson.put("currency", currency);
        toJson.put("description", getDescription());
        toJson.put("timestamp", getTimestamp());

        return toJson;
    }
}
