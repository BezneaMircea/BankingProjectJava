package org.poo.bank.transactions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.accounts.Account;
import org.poo.utils.Utils;

public final class WithdrawSavingsTransaction extends Transaction {
    public WithdrawSavingsTransaction(final Type transactionType,
                                      final int timestamp,
                                      final String description) {
        super(transactionType, timestamp, description);
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

        return toJson;
    }
}
