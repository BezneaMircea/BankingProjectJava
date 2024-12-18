package org.poo.commands.transactions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import org.poo.bank.accounts.Account;
import org.poo.utils.Utils;

@Getter
public class PayOnlineTransaction extends Transaction {
    public static String IS_FROZEN = "The card is frozen";

    private final double amount;
    private final String commerciant;
    private final String error;

    public PayOnlineTransaction(final int timestamp, final String description,
                                final double amount, final String commerciant,
                                final String error) {
        super(timestamp, description);
        this.amount = amount;
        this.commerciant = commerciant;
        this.error = error;
    }


    @Override
    public ObjectNode toJson() {
        ObjectNode jsonNode = Utils.mapper.createObjectNode();
        if (error == null) {
            jsonNode.put("timestamp", getTimestamp());
            jsonNode.put("description", getDescription());
            jsonNode.put("amount", amount);
            jsonNode.put("commerciant", commerciant);
        } else {
            jsonNode.put("timestamp", getTimestamp());
            jsonNode.put("description", error);
        }

        return jsonNode;
    }

    public void addTransactionToAccount(final Account account) {
        account.addTransaction(this);
    }
}
