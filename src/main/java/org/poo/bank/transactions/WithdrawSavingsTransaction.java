package org.poo.bank.transactions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.accounts.Account;
import org.poo.utils.Utils;

public final class WithdrawSavingsTransaction extends Transaction {
    public static final String SAVINGS_WITHDRAWAL = "Savings withdrawal";

    private final String error;
    private final String classicAccountIban;
    private final String savingsAccountIban;
    private final double amount;

    public WithdrawSavingsTransaction(final Type transactionType, final int timestamp,
                                      final String description, final String error,
                                      final String classicAccountIban,
                                      final String savingsAccountIban, final double amount) {
        super(transactionType, timestamp, description);
        this.error = error;
        this.classicAccountIban = classicAccountIban;
        this.savingsAccountIban = savingsAccountIban;
        this.amount = amount;
    }


    @Override
    public void addTransactionToAccount(final Account account) {
        account.addTransaction(this);
    }

    @Override
    public ObjectNode toJson() {
        ObjectNode toJson = Utils.MAPPER.createObjectNode();

        if (error != null) {
            toJson.put("timestamp", getTimestamp());
            toJson.put("description", getDescription());
        } else {
            toJson.put("amount", amount);
            toJson.put("classicAccountIBAN", classicAccountIban);
            toJson.put("description", getDescription());
            toJson.put("savingsAccountIBAN", savingsAccountIban);
            toJson.put("timestamp", getTimestamp());
        }

        return toJson;
    }
}
