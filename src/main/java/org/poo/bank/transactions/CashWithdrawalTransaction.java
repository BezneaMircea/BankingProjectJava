package org.poo.bank.transactions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.accounts.Account;
import org.poo.utils.Utils;

import java.util.Objects;

public class CashWithdrawalTransaction extends Transaction {
    private final String error;
    private final double amount;

    public CashWithdrawalTransaction(final Type transactionType, final int timestamp,
                                     final String description, final String error,
                                     final double amount) {
        super(transactionType, timestamp, description);
        this.error = error;
        this.amount = amount;
    }


    @Override
    public void addTransactionToAccount(Account account) {
        account.addTransaction(this);
    }

    @Override
    public ObjectNode toJson() {
        ObjectNode toJson = Utils.MAPPER.createObjectNode();
        if (error != null) {
            toJson.put("description", error);
            toJson.put("timestamp", getTimestamp());
        } else {
            toJson.put("timestamp", getTimestamp());
            toJson.put("description", getDescription());
            toJson.put("amount", amount);
        }

        return toJson;
    }
}
