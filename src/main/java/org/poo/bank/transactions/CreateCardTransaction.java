package org.poo.bank.transactions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.accounts.Account;
import org.poo.utils.Utils;

public final class CreateCardTransaction extends Transaction {
    private final String card;
    private final String cardHolder;
    private final String account;
    private final String error;

    public CreateCardTransaction(final Type transactionType, final int timestamp,
                                 final String description, final String card,
                                 final String cardHolder, final String account,
                                 final String error) {
        super(transactionType, timestamp, description);
        this.card = card;
        this.cardHolder = cardHolder;
        this.account = account;
        this.error = error;
    }

    @Override
    public ObjectNode toJson() {
        ObjectNode jsonNode = Utils.MAPPER.createObjectNode();

        if (error == null) {
            jsonNode.put("timestamp", getTimestamp());
            jsonNode.put("description", getDescription());
            jsonNode.put("card", card);
            jsonNode.put("cardHolder", cardHolder);
            jsonNode.put("account", account);
        }

        return jsonNode;
    }

    @Override
    public void addTransactionToAccount(final Account acc) {
        acc.addTransaction(this);
    }
}
