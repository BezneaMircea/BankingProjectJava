package org.poo.users;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import org.poo.bank.accounts.Account;
import org.poo.commands.transactions.Transaction;
import org.poo.fileio.UserInput;
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
    public static String NOT_FOUND = "User not found";

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
     * @param firstName first name of the user
     * @param lastName last name of the user
     * @param email email of the user
     */
    public User(final String firstName, final String lastName, final String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

        accounts = new ArrayList<>();
        transactions = new ArrayList<>();
        aliases = new HashMap<>();
    }


    public void addAccount(Account account) {
        accounts.add(account);
    }

    public boolean hasAccount(Account account) {
        return accounts.contains(account);
    }

    public void addAlias(Account account, String alias) {
        aliases.put(alias, account);
    }

    public boolean hasAlias(String alias) {
        return aliases.containsKey(alias);
    }

    public Account getAccountFromAlias(String alias) {
        return aliases.get(alias);
    }


    public ObjectNode userToObjectNode() {
        ObjectNode userNode = Utils.mapper.createObjectNode();

        userNode.put("firstName", firstName);
        userNode.put("lastName", lastName);
        userNode.put("email", email);
        userNode.set("accounts", writeAccounts());

        return userNode;
    }

    public ArrayNode transactionsToObjectNode() {
        ArrayNode transactionsArray = Utils.mapper.createArrayNode();
        for (Transaction transaction : transactions) {
            transactionsArray.add(transaction.toJson());
        }

        return transactionsArray;
    }

    private ArrayNode writeAccounts() {
        ArrayNode accountsNode = Utils.mapper.createArrayNode();
        for (Account account : accounts) {
            accountsNode.add(account.accountToObjectNode());
        }

        return accountsNode;
    }

}

