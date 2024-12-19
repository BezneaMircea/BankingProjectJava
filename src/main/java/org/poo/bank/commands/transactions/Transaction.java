package org.poo.bank.commands.transactions;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import org.poo.bank.accounts.Account;
import org.poo.bank.users.User;

@Getter
public abstract class Transaction {

    public enum Type {
        ADD_ACCOUNT, CHANGE_INT_RATE, CHECK_CARD_STAT, CREATE_CARD,
        DELETE_ACCOUNT, DELETE_CARD, PAY_ONLINE, SEND_MONEY, SPLIT_PAYMENT
    }

    private final Type transactionType;
    private final int timestamp;
    private final String description;

    public Transaction(final Type transactionType,  int timestamp, final String description) {
        this.transactionType = transactionType;
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
