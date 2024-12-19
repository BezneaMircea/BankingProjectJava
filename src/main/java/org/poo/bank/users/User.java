package org.poo.bank.users;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import org.poo.bank.accounts.Account;
import org.poo.bank.transactions.Transaction;
import org.poo.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class used to represent a user of our banking application
 */
@Data
public abstract class User {
    public static final String NOT_FOUND;

    static {
        NOT_FOUND = "User not found";
    }

    enum Type {
        BASIC
    }

    private final String firstName;
    private final String lastName;
    private final String email;
    private final List<Account> accounts;
    private final List<Transaction> transactions;
    private final Map<String, Account> aliases;

    /**
     * Constructor of the User class
     *
     * @param firstName first name of the user
     * @param lastName  last name of the user
     * @param email     email of the user
     */
    public User(final String firstName, final String lastName, final String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

        accounts = new ArrayList<>();
        transactions = new ArrayList<>();
        aliases = new HashMap<>();
    }

    /**
     * Method used to write and user to an ObjectNode
     * @return the corresponding ObjectNode
     */
    public ObjectNode userToObjectNode() {
        ObjectNode userNode = Utils.MAPPER.createObjectNode();

        userNode.put("firstName", firstName);
        userNode.put("lastName", lastName);
        userNode.put("email", email);
        userNode.set("accounts", writeAccounts());

        return userNode;
    }

    private ArrayNode writeAccounts() {
        ArrayNode accountsNode = Utils.MAPPER.createArrayNode();
        for (Account account : accounts) {
            accountsNode.add(account.accountToObjectNode());
        }

        return accountsNode;
    }

    /**
     * Method used to write the users transactions to an ArrayNode
     * @return the ArrayNode
     */
    public ArrayNode transactionsToObjectNode() {
        ArrayNode transactionsArray = Utils.MAPPER.createArrayNode();
        for (Transaction transaction : transactions) {
            transactionsArray.add(transaction.toJson());
        }

        return transactionsArray;
    }

    /**
     * Method used to add an account to the users account list
     * @param account account to add
     */
    public void addAccount(final Account account) {
        accounts.add(account);
    }

    /**
     * Method used to check if the User owns the account
     * @param account account to check if exists
     * @return true if it exists, false otherwise
     */
    public boolean hasAccount(final Account account) {
        return accounts.contains(account);
    }

    /**
     * Method used to add an alias
     * @param account account
     * @param alias alias that links to the account
     */
    public void addAlias(final Account account, final String alias) {
        aliases.put(alias, account);
    }

    /**
     * Method used to check if the user has an alias
     * @param alias the alias to check
     * @return true if the user has the alias, false otherwise
     */
    public boolean hasAlias(final String alias) {
        return aliases.containsKey(alias);
    }

    /**
     * Method used to get the account associated with the alias
     * @param alias the alias
     * @return the account if it exists, null otherwise
     */
    public Account getAccountFromAlias(final String alias) {
        return aliases.get(alias);
    }


}

