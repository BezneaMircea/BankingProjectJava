package org.poo.bank.transactions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import org.poo.bank.accounts.Account;
import org.poo.bank.commerciants.Commerciant;
import org.poo.utils.Utils;


@Getter
public final class PayOnlineTransaction extends Transaction {
    private final double amount;
    private final Commerciant commerciant;
    private final String error;

    public PayOnlineTransaction(final Type transactionType, final int timestamp,
                                final String description, final double amount,
                                final Commerciant commerciant, final String error) {
        super(transactionType, timestamp, description);
        this.amount = amount;
        this.commerciant = commerciant;
        this.error = error;
    }


    @Override
    public ObjectNode toJson() {
        ObjectNode jsonNode = Utils.MAPPER.createObjectNode();
        if (error == null) {
            jsonNode.put("timestamp", getTimestamp());
            jsonNode.put("description", getDescription());
            jsonNode.put("amount", Utils.approximateToFourthDecimal(amount));
            jsonNode.put("commerciant", commerciant.getName());
        } else {
            jsonNode.put("timestamp", getTimestamp());
            jsonNode.put("description", error);
        }

        return jsonNode;
    }

    @Override
    public void addTransactionToAccount(final Account account) {
        account.addTransaction(this);
    }
}
