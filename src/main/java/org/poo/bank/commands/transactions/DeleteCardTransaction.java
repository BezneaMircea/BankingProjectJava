package org.poo.bank.commands.transactions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.accounts.Account;
import org.poo.utils.Utils;

public final class DeleteCardTransaction extends Transaction {
    private final String card;
    private final String cardHolder;
    private final String account;

    public DeleteCardTransaction(final Type transactionType, final int timestamp, final String description, final String card,
                                 final String cardHolder, final String account) {
        super(transactionType, timestamp, description);
        this.card = card;
        this.cardHolder = cardHolder;
        this.account = account;
    }


    @Override
    public ObjectNode toJson() {
        ObjectNode jsonNode = Utils.MAPPER.createObjectNode();
        jsonNode.put("timestamp", getTimestamp());
        jsonNode.put("description", getDescription());
        jsonNode.put("card", card);
        jsonNode.put("cardHolder", cardHolder);
        jsonNode.put("account", account);

        return jsonNode;
    }

    public void addTransactionToAccount(final Account account) {
        account.addTransaction(this);
    }
}
