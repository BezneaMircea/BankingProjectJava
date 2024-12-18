package org.poo.commands.transactions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.accounts.Account;
import org.poo.utils.Utils;

public class CheckCardStatusTransaction extends Transaction {
    public static String LIMIT_REACHED = "You have reached the minimum amount of funds, the card will be frozen";

    public CheckCardStatusTransaction(final Type transactionType, int timestamp, String description) {
        super(transactionType, timestamp, description);
    }

    @Override
    public ObjectNode toJson() {
        ObjectNode jsonNode = Utils.mapper.createObjectNode();
        jsonNode.put("timestamp", getTimestamp());
        jsonNode.put("description", getDescription());

        return jsonNode;
    }

    public void addTransactionToAccount(final Account account) {
        account.addTransaction(this);
    }
}
