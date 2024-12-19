package org.poo.bank;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.poo.bank.accounts.Account;
import org.poo.bank.accounts.AccountInput;
import org.poo.bank.accounts.cards.Card;
import org.poo.bank.accounts.cards.CardInput;
import org.poo.bank.accounts.cards.card_factory.CardFactory;
import org.poo.bank.accounts.cards.card_factory.OneTimeCardFactory;
import org.poo.bank.accounts.cards.card_factory.StandardCardFactory;
import org.poo.bank.accounts.account_factory.AccountFactory;
import org.poo.bank.accounts.account_factory.EconomyAccountFactory;
import org.poo.bank.accounts.account_factory.StandardAccountFactory;
import org.poo.bank.commands.transactions.Transaction;
import org.poo.bank.commands.transactions.TransactionInput;
import org.poo.bank.commands.transactions.transaction_factory.*;
import org.poo.bank.users.User;
import org.poo.utils.CustomFloydWarshallPaths;
import org.poo.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class used to represent a Bank
 */
@Getter
public final class Bank {
    private final Map<String, User> emailToUser;
    private final Map<String, Account> ibanToAccount;
    private final Map<String, Card> cardNrToCard;
    private final CustomFloydWarshallPaths<String, DefaultWeightedEdge> exchangeRates;
    private final List<User> users;
    private final ArrayNode output;

    /**
     * Constructor for the bank class
     * @param users the users list
     * @param exchangeRates the exangeRates graph
     * @param output the output node
     */
    public Bank(final List<User> users,
                final Graph<String, DefaultWeightedEdge> exchangeRates,
                final ArrayNode output) {
        this.users = users;
        this.output = output;

        this.exchangeRates = initializeExchangeRates(exchangeRates);
        ibanToAccount = new HashMap<>();
        cardNrToCard = new HashMap<>();
        emailToUser = new HashMap<>();
        mapEmailToUser();
    }

    private CustomFloydWarshallPaths<String, DefaultWeightedEdge>
    initializeExchangeRates(final Graph<String, DefaultWeightedEdge> rates) {
        if (rates == null) {
            return null;
        }

        return new CustomFloydWarshallPaths<>(rates);
    }


    private void mapEmailToUser() {
        for (User user : users) {
            emailToUser.put(user.getEmail(), user);
        }
    }

    /**
     * Method used to create a specific transaction based on a transaction input
     *
     * @param input the transaction input
     * @return the transaction.
     * @throws IllegalArgumentException if the input doesn't correspond to any transaction
     */
    public Transaction generateTransaction(final TransactionInput input) {
        if (input == null) {
            return null;
        }

        TransactionFactory factory;
        switch (input.getTransactionType()) {
            case ADD_ACCOUNT -> factory = new AddAccountTransactionFactory(input);
            case CHANGE_INT_RATE -> factory = new ChangeIntRateTransactionFactory(input);
            case CHECK_CARD_STAT -> factory = new CheckCardStatusTransactionFactory(input);
            case CREATE_CARD -> factory = new CreateCardTransactionFactory(input);
            case DELETE_ACCOUNT -> factory = new DeleteAccountTransactionFactory(input);
            case DELETE_CARD -> factory = new DeleteCardTransactionFactory(input);
            case PAY_ONLINE -> factory = new PayOnlineTransactionFactory(input);
            case SEND_MONEY -> factory = new SendMoneyTransactionFactory(input);
            case SPLIT_PAYMENT -> factory = new SplitPaymenTransactionFactory(input);
            default -> throw new IllegalArgumentException("Invalid transaction type");
        }

        return factory.createTransaction();
    }

    /**
     * Method used to check and perform an action if an error occurs.
     * It adds an ObjectNode to the output array containing the command,
     * the timestamp of the command and the error that occured
     *
     * @param timestamp the timestamp of the command
     * @param command   the command name
     * @param error     the error. Nothing happens if null
     */
    public void errorOccured(final int timestamp, final String command, final String error) {
        if (error == null) {
            return;
        }

        ObjectNode outputNode = Utils.MAPPER.createObjectNode();
        outputNode.put("timestamp", timestamp);
        outputNode.put("description", error);

        ObjectNode errorNode = Utils.MAPPER.createObjectNode();
        errorNode.put("command", command);
        errorNode.set("output", outputNode);
        errorNode.put("timestamp", timestamp);

        output.add(errorNode);
    }

    /**
     * Method used to create an account
     *
     * @param input input for the new account to create
     * @return the created Account
     * @throws IllegalArgumentException if the account type doesn't correspond to the account types
     */
    public Account createAccount(final AccountInput input) {
        AccountFactory factory;

        switch (input.getAccountType()) {
            case Account.Type.SAVINGS -> factory = new EconomyAccountFactory(input);
            case Account.Type.CLASSIC -> factory = new StandardAccountFactory(input);
            default -> throw new IllegalArgumentException("Invalid account type");
        }

        return factory.createAccount();
    }

    /**
     * Method used to add an account to the bank. It adds the account both in the
     * bank account database (Map ibanToAccount) and in the users accounts list
     *
     * @param account the account to be added
     * @return null if no error occurs, an appropriate error String otherwise
     */
    public String addAccount(final Account account) {
        User owner = emailToUser.get(account.getOwnerEmail());
        if (owner == null) {
            return User.NOT_FOUND;
        }

        ibanToAccount.put(account.getIban(), account);
        owner.addAccount(account);

        return null;
    }

    /**
     * Method used to delete an account
     *
     * @param accountToDelete account to be deleted
     * @return a String stating that if was successfully deleted
     * or a String saying why it couldn't be deleted (e.g. funds remaining)
     */
    public String deleteAccount(final Account accountToDelete) {
        if (accountToDelete.getBalance() == 0) {
            ibanToAccount.remove(accountToDelete.getIban());

            for (Card card : accountToDelete.getCards()) {
                cardNrToCard.remove(card.getCardNumber());
            }
            accountToDelete.getCards().clear();

            User owner = emailToUser.get(accountToDelete.getOwnerEmail());
            owner.getAccounts().remove(accountToDelete);

            return Account.DELETED;
        } else {
            return Account.CANT_DELETE;
        }
    }

    /**
     * Method used to create a card
     *
     * @param input the card input
     * @return the created Card
     */
    public Card createCard(final CardInput input) {
        CardFactory factory;
        switch (input.getCardType()) {
            case Card.Type.ONE_TIME -> factory = new OneTimeCardFactory(input);
            case Card.Type.STANDARD -> factory = new StandardCardFactory(input);
            default -> throw new IllegalArgumentException("Invalid card type");
        }

        return factory.createCard();
    }

    /**
     * Method used to add a card to the bank
     * @param card the card to be added
     * @return null if no error occurs or an appropriate String otherwise
     */
    public String addCard(final Card card) {
        Account associatedAccount = card.getAccount();
        if (!ibanToAccount.containsValue(associatedAccount)) {
            return Account.NOT_FOUND;
        }

        User owner = emailToUser.get(card.getAccount().getOwnerEmail());
        if (owner == null) {
            return User.NOT_FOUND;
        }

        associatedAccount.getCards().add(card);
        cardNrToCard.put(card.getCardNumber(), card);

        return null;
    }

    /**
     * Method used to delete a card from a bank and of course an account.
     *
     * @param cardToDelete the card that needs to be deleted
     */
    public void deleteCard(final Card cardToDelete) {
        if (cardToDelete == null) {
            return;
        }

        cardNrToCard.remove(cardToDelete.getCardNumber());

        Account associatedAccount = cardToDelete.getAccount();
        if (associatedAccount == null) {
            return;
        }

        associatedAccount.removeCard(cardToDelete);
    }

    /**
     * Method used to write the Users in an arrayNode. Internaly it calls a user
     * method that writes a single user into an ObjectNode
     *
     * @return ArrayNode of users
     */
    public ArrayNode usersToArrayNode() {
        ArrayNode usersArray = Utils.MAPPER.createArrayNode();
        for (User user : users) {
            usersArray.add(user.userToObjectNode());
        }

        return usersArray;
    }

    /**
     * for coding style
     */
    public Card getCard(final String cardNr) {
        return cardNrToCard.get(cardNr);
    }

    /**
     * for coding style
     */
    public User getUser(final String email) {
        return emailToUser.get(email);
    }

    /**
     * for coding style
     */
    public Account getAccount(final String iban) {
        return ibanToAccount.get(iban);
    }

    /**
     * Method used to get the conversion rate from a currency to another
     * @param from from currency
     * @param to to currency
     * @return the conversion rate
     */
    public double getRate(final String from, final String to) {
        return exchangeRates.getRate(from, to);
    }

}
