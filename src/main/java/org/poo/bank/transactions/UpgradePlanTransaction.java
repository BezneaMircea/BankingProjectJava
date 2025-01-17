package org.poo.bank.transactions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.accounts.Account;
import org.poo.utils.Utils;


public class UpgradePlanTransaction extends Transaction {
    public static final String UPGRADE_PLAN = "Upgrade plan";

    private final String account;
    private final String newPlanType;
    private final String error;

    public UpgradePlanTransaction(final Type transactionType, final int timestamp,
                                  final String description, final String account,
                                  final String newPlanType, final String error) {
        super(transactionType, timestamp, description);
        this.account = account;
        this.newPlanType = newPlanType;
        this.error = error;
    }

    @Override
    public void addTransactionToAccount(final Account account) {
        account.addTransaction(this);
    }

    @Override
    public ObjectNode toJson() {
        ObjectNode toJson = Utils.MAPPER.createObjectNode();

        toJson.put("timestamp", getTimestamp());

        if (error != null) {
            toJson.put("description", error);
        } else {
            toJson.put("description", getDescription());
            toJson.put("accountIBAN", account);
            toJson.put("newPlanType", newPlanType);
        }
        return toJson;
    }
}
