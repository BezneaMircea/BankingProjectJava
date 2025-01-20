package org.poo.bank.users;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import org.poo.bank.accounts.Account;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.users.users_strategy.*;
import org.poo.utils.Utils;

import java.time.LocalDate;
import java.time.Period;
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
    public static final String HAS_PLAN;
    public static final String CANT_DOWNGRADE;
    public static final String NOT_OLD_ENOUGH;
    public static final String DONT_HAVE_CLASSIC;

    public static final int MINIMUM_AGE = 21;


    static {
        NOT_FOUND = "User not found";
        HAS_PLAN = "The user already has the %s plan.";
        CANT_DOWNGRADE = "You cannot downgrade your plan.";
        NOT_OLD_ENOUGH = "You don't have the minimum age required.";
        DONT_HAVE_CLASSIC = "You do not have a classic account.";
    }

    enum Type {
        BASIC
    }

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String birthDate;
    private final String occupation;
    private final List<Account> accounts;
    private final List<Transaction> transactions;
    private final Map<String, Account> aliases;
    private UserStrategy strategy;

    /**
     * Constructor of the User class
     *
     * @param firstName first name of the user
     * @param lastName  last name of the user
     * @param email     email of the user
     */
    public User(final String firstName, final String lastName, final String email,
                final String birthDate, final String occupation, final UserStrategy strategy) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.occupation = occupation;
        this.strategy = strategy;

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

    public void setStrategy(final UserStrategy.Type strategyType) {
        switch (strategyType) {
            case SILVER -> strategy = new SilverStrategy();
            case GOLD -> strategy = new GoldStrategy();
            case STANDARD -> strategy = new StandardStrategy();
            case STUDENT -> strategy = new StudentStrategy();

            default -> throw new IllegalArgumentException("Invalid strategy type");
        }
    }

    public boolean isOldEnough() {
        LocalDate birthDay = LocalDate.parse(birthDate);
        LocalDate currentTime = LocalDate.now();

        int age = Period.between(birthDay, currentTime).getYears();
        return age >= MINIMUM_AGE;
    }

    /**
     * This method searches for the first classic account having the
     * specified currency
     * @param currency the wanted currency for the searched account
     * @return the account or null if no account was present
     */
    public Account getClassicAccWithCurrency(String currency) {
        for (Account account : accounts) {
            if (account.getAccountType() == Account.Type.CLASSIC
                    && account.getCurrency().equals(currency)) {
                return account;
            }
        }

        return null;
    }
}

