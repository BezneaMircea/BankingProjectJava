package org.poo.commands.transactions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.utils.Utils;

public class DeleteCardTransaction implements Transaction {
    public static String DELETED_CARD = "The card has been destroyed";

    private final int timestamp;
    private final String description;
    private final String card;
    private final String cardHolder;
    private final String account;

    public DeleteCardTransaction(final int timestamp, final String description, final String card,
                                 final String cardHolder, final String account) {
        this.timestamp = timestamp;
        this.description = description;
        this.card = card;
        this.cardHolder = cardHolder;
        this.account = account;
    }


    @Override
    public ObjectNode toJson() {
        ObjectNode jsonNode = Utils.mapper.createObjectNode();
        jsonNode.put("timestamp", timestamp);
        jsonNode.put("description", description);
        jsonNode.put("card", card);
        jsonNode.put("cardHolder", cardHolder);
        jsonNode.put("account", account);

        return jsonNode;
    }
}
