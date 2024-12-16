package org.poo.commands.transactions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.utils.Utils;

public class AddAccountTransaction implements Transaction {
    public static String ACCOUNT_CREATED = "New account created";

    private final int timestamp;
    private final String description;

    public AddAccountTransaction(final int timestamp, final String description) {
        this.timestamp = timestamp;
        this.description = description;
    }

    @Override
    public ObjectNode toJson() {
        ObjectNode jsonNode = Utils.mapper.createObjectNode();
        jsonNode.put("timestamp", timestamp);
        jsonNode.put("description", description);

        return jsonNode;
    }
}
