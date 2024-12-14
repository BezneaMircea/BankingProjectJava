package org.poo.bank;

import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Getter;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.FloydWarshallShortestPaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.poo.bank.accounts.Account;
import org.poo.bank.accounts.cards.Card;
import org.poo.bank.accounts.cards.cardfactory.CardFactory;
import org.poo.bank.accounts.cards.cardfactory.OneTimeCardFactory;
import org.poo.bank.accounts.cards.cardfactory.StandardCardFactory;
import org.poo.bank.accounts.factory.AccountFactory;
import org.poo.bank.accounts.factory.EconomyAccountFactory;
import org.poo.bank.accounts.factory.StandardAccountFactory;
import org.poo.users.User;
import org.poo.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Bank {
    private final Map<String, User> emailToUser;
    private final Map<String, Account> ibanToAccount;
    private final Map<String, Card> cardNrToCard;

    private final List<User> users;
    private final CustomFloydWarshallShortestPaths<String, DefaultWeightedEdge> exchangeRates;

    private final ArrayNode output;

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

    private CustomFloydWarshallShortestPaths<String, DefaultWeightedEdge>
    initializeExchangeRates(final Graph<String, DefaultWeightedEdge> exchangeRates) {
        if (exchangeRates == null)
            return null;

        return new CustomFloydWarshallShortestPaths<>(exchangeRates);
    }

    private void mapEmailToUser() {
        for (User user : users) {
            emailToUser.put(user.getEmail(), user);
        }
    }


    public ArrayNode usersToArrayNode() {
        ArrayNode usersArray = Utils.mapper.createArrayNode();
        for (User user : users) {
            usersArray.add(user.userToObjectNode());
        }

        return usersArray;
    }

    public Account createAccount(String email, String currency, String accountType, double interestRate) {
        AccountFactory factory;

        switch (accountType) {
            case "savings" -> factory = new EconomyAccountFactory(email, currency, accountType, interestRate);
            case "classic" -> factory = new StandardAccountFactory(email, currency, accountType);
            default -> throw new IllegalArgumentException("Invalid account type");
        }

        return factory.createAccount();
    }

    public Card createCard(String status, Account account, String type) {
        CardFactory factory;
        switch (type) {
            case "createOneTimeCard" -> factory = new OneTimeCardFactory(status, account);
            case "createCard" -> factory = new StandardCardFactory(status, account);
            default -> throw new IllegalArgumentException("Invalid card type");
        }

        return factory.createCard();
    }



}
