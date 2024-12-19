package org.poo.bank.commands.transactions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.accounts.Account;
import org.poo.utils.Utils;

public final class DeleteAccountTransaction extends Transaction {
    public DeleteAccountTransaction(final Type transactionType, final int timestamp, final String description) {
        super(transactionType, timestamp, description);
    }

    @Override
    public void addTransactionToAccount(Account account) {
        account.addTransaction(this);
    }

    @Override
    public ObjectNode toJson() {
        ObjectNode jsonNode = Utils.MAPPER.createObjectNode();
        jsonNode.put("timestamp", getTimestamp());
        jsonNode.put("description", getDescription());

        return jsonNode;
    }
}
