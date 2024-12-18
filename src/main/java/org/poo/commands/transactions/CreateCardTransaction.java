package org.poo.commands.transactions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.accounts.Account;
import org.poo.utils.Utils;

public class CreateCardTransaction extends Transaction {
    public static String CARD_CREATED = "New card created";

    private final String card;
    private final String cardHolder;
    private final String account;
    private final String error;

    public CreateCardTransaction(int timestamp, String description, String card,
                                 String cardHolder, String account, String error) {
        super(timestamp, description);
        this.card = card;
        this.cardHolder = cardHolder;
        this.account = account;
        this.error = error;
    }

    @Override
    public ObjectNode toJson() {
        ObjectNode jsonNode = Utils.mapper.createObjectNode();

        if (error == null) {
            jsonNode.put("timestamp", getTimestamp());
            jsonNode.put("description", getDescription());
            jsonNode.put("card", card);
            jsonNode.put("cardHolder", cardHolder);
            jsonNode.put("account", account);
        }

        return jsonNode;
    }

    public void addTransactionToAccount(final Account account) {
        account.addTransaction(this);
    }
}
