package org.poo.commands.transactions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.utils.Utils;

public class PayOnlineTransaction implements Transaction {
    public static String IS_FROZEN = "The card is frozen";

    private final int timestamp;
    private final String description;
    private final double amount;
    private final String commerciant;
    private final String error;

    public PayOnlineTransaction(final int timestamp, final String description,
                                final double amount, final String commerciant,
                                final String error) {
        this.timestamp = timestamp;
        this.description = description;
        this.amount = amount;
        this.commerciant = commerciant;
        this.error = error;
    }

    @Override
    public ObjectNode toJson() {
        ObjectNode jsonNode = Utils.mapper.createObjectNode();
        if (error == null) {
            jsonNode.put("timestamp", timestamp);
            jsonNode.put("description", description);
            jsonNode.put("amount", amount);
            jsonNode.put("commerciant", commerciant);
        } else {
            jsonNode.put("timestamp", timestamp);
            jsonNode.put("description", error);
        }

        return jsonNode;
    }
}
