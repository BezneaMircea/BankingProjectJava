package org.poo.bank.transactions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.accounts.Account;
import org.poo.utils.Utils;

public final class ChangeIntRateTransaction extends Transaction {
    public static final String IRATE_CHANGED = "Interest rate of the account changed to %.2f";


    public ChangeIntRateTransaction(final Type transactionType, final int timestamp,
                                    final String description) {
        super(transactionType, timestamp, description);
    }

    @Override
    public ObjectNode toJson() {
        ObjectNode jsonNode = Utils.MAPPER.createObjectNode();
        jsonNode.put("timestamp", getTimestamp());
        jsonNode.put("description", getDescription());

        return jsonNode;
    }

    @Override
    public void addTransactionToAccount(final Account account) {
        account.addTransaction(this);
    }
}
