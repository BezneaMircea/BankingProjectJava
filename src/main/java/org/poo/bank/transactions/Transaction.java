package org.poo.bank.transactions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import org.poo.bank.accounts.Account;
import org.poo.bank.users.User;

@Getter
public abstract class Transaction {

    public enum Type {
        ADD_ACCOUNT, CHANGE_INT_RATE, CHECK_CARD_STAT, CREATE_CARD,
        DELETE_ACCOUNT, DELETE_CARD, PAY_ONLINE, SEND_MONEY, SPLIT_PAYMENT,
        UPGRADE_PLAN, WITHDRAW_SAVINGS
    }

    private final Type transactionType;
    private final int timestamp;
    private final String description;

    public Transaction(final Type transactionType, final int timestamp, final String description) {
        this.transactionType = transactionType;
        this.timestamp = timestamp;
        this.description = description;
    }

    /**
     * Method used to add a Transaction to and user and an account.
     * Note that all types of users will add any type of transaction
     * @param user user to add transaction to
     * @param account account to add transaction to
     */
    public void addTransaction(final User user, final Account account) {
        user.getTransactions().add(this);
        addTransactionToAccount(account);
    }

    /**
     * Method used to add a transaction to an account. Different transactions
     * might interact differently with different types of accounts (double dispatch)
     * @param account the account that should receive the transaction
     */
    public abstract void addTransactionToAccount(Account account);

    /**
     * Method used to write a Transaction to an ObjectNode
     * @return the corresponding ObjectNode
     */
    public abstract ObjectNode toJson();
}
