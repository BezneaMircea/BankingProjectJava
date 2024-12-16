package org.poo.commands.transactions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.utils.Utils;

public class SendMoneyTransaction implements Transaction {
    public static String SENT = "sent";
    public static String RECEIVED = "received";
    public static String INSUFFICIENT_FUNDS = "Insufficient funds";

    private final int timestamp;
    private final String description;
    private final String senderIBAN;
    private final String receiverIBAN;
    private final double amount;
    private final String currency;
    private final String transferType;
    private final String error;

    public SendMoneyTransaction(int timestamp, String description,
                                String senderIBAN, String receiverIBAN,
                                double amount, String currency,
                                String transferType, String error) {
        this.timestamp = timestamp;
        this.description = description;
        this.senderIBAN = senderIBAN;
        this.receiverIBAN = receiverIBAN;
        this.amount = amount;
        this.currency = currency;
        this.transferType = transferType;
        this.error = error;
    }

    @Override
    public ObjectNode toJson() {
        ObjectNode jsonNode = Utils.mapper.createObjectNode();

        if (error == null) {
            jsonNode.put("timestamp", timestamp);
            jsonNode.put("description", description);
            jsonNode.put("senderIBAN", senderIBAN);
            jsonNode.put("receiverIBAN", receiverIBAN);
            jsonNode.put("amount", amount + " " + currency);
            jsonNode.put("transferType", transferType);
        } else {
            jsonNode.put("timestamp", timestamp);
            jsonNode.put("description", error);
        }

        return jsonNode;
    }
}
