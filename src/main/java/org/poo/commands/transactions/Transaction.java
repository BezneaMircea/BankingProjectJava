package org.poo.commands.transactions;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import org.poo.bank.accounts.Account;
import org.poo.users.User;

@Getter
public abstract class Transaction {
    private final int timestamp;
    private final String description;

    public Transaction(final int timestamp, final String description) {
        this.timestamp = timestamp;
        this.description = description;
    }

    public void addTransaction(final User user, final Account account) {
        user.getTransactions().add(this);
        addTransactionToAccount(account);
    }

    public abstract void addTransactionToAccount(final Account account);
    public abstract ObjectNode toJson();
}
