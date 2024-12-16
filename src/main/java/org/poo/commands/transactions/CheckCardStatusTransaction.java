package org.poo.commands.transactions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.utils.Utils;

public class CheckCardStatusTransaction implements Transaction {
    public static String LIMIT_REACHED = "You have reached the minimum amount of funds, the card will be frozen";

    private final int timestamp;
    private final String description;

    public CheckCardStatusTransaction(int timestamp, String description) {
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
