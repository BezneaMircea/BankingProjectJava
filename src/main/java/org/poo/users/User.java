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

@Data
abstract public class User {
    public static String NOT_FOUND = "User not found";

    private String firstName;
    private String lastName;
    private String email;
    private List<Account> accounts;
    private List<Transaction> transactions;
    private Map<String, Account> aliases;
    /* Add the transactions list */

    public User(UserInput userInput) {
        this.firstName = userInput.getFirstName();
        this.lastName = userInput.getLastName();
        this.email = userInput.getEmail();

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

    public ObjectNode userToObjectNode() {
        ObjectNode userNode = Utils.mapper.createObjectNode();

        userNode.put("firstName", firstName);
        userNode.put("lastName", lastName);
        userNode.put("email", email);
        userNode.set("accounts", writeAccounts());

        return userNode;
    }

    private ArrayNode writeAccounts() {
        ArrayNode accountsNode = Utils.mapper.createArrayNode();
        for (Account account : accounts) {
            accountsNode.add(account.accountToObjectNode());
        }

        return accountsNode;
    }

}

