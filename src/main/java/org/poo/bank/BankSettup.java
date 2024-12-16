package org.poo.bank;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.poo.commands.CheckCardStatus;
import org.poo.commands.Command;
import org.poo.commands.factory.*;
import org.poo.fileio.*;
import org.poo.users.User;
import org.poo.users.UserFactory;

import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.ArrayList;
import java.util.List;


public class BankSettup {
    private final UserInput[] users;
    private final ExchangeInput[] exchangeRates;
    private final CommandInput[] commands;
    private final ArrayNode output;

    public BankSettup(final ObjectInput inputData, final ArrayNode output) {
        users = inputData.getUsers();
        exchangeRates = inputData.getExchangeRates();
        commands = inputData.getCommands();
        this.output = output;
    }

    private Bank createBank() {
        return new Bank(createUsers(), createExchange(), output);
    }

    private List<User> createUsers() {
        List<User> bankUsers = new ArrayList<>();
        for (UserInput user : users) {
            bankUsers.add(UserFactory.createUser(user));
        }

        return bankUsers;
    }

    private Graph<String, DefaultWeightedEdge> createExchange() {
        if (exchangeRates == null || exchangeRates.length == 0)
            return null;

        Graph<String, DefaultWeightedEdge> myExchange = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
        for (ExchangeInput exchange : exchangeRates) {
            myExchange.addVertex(exchange.getFrom());
            myExchange.addVertex(exchange.getTo());

            DefaultWeightedEdge edgeFromTo = myExchange.addEdge(exchange.getFrom(), exchange.getTo());
            myExchange.setEdgeWeight(edgeFromTo, exchange.getRate());

            DefaultWeightedEdge edgeToFrom = myExchange.addEdge(exchange.getTo(), exchange.getFrom());
            myExchange.setEdgeWeight(edgeToFrom, 1 / exchange.getRate());
        }

        return myExchange;
    }

    private Command CreateCommand(Bank bank, CommandInput command) {
        CommandFactory factory;

        switch (command.getCommand()) {
            case "addAccount" -> factory = new AddAccountFactory(bank, command);
            case "printUsers" -> factory = new PrintUsersFactory(bank, command);
            case "createCard", "createOneTimeCard" -> factory = new CreateCardFactory(bank, command);
            case "addFunds" -> factory = new AddFundsFactory(bank, command);
            case "deleteAccount" -> factory = new DeleteAccountFactory(bank, command);
            case "deleteCard" -> factory = new DeleteCardFactory(bank, command);
            case "setMinBalance" -> factory = new SetMinBalanceFactory(bank, command);
            case "checkCardStatus" -> factory = new CheckCardStatusFactory(bank, command);
            case "payOnline" -> factory = new PayOnlineFactory(bank, command);
            case "sendMoney" -> factory = new SendMoneyFactory(bank, command);
            case "printTransactions" -> factory = new PrintTransactionsFactory(bank, command);
            case "setAlias" -> factory = new SetAliasFactory(bank, command);
            case "splitPayment" -> factory = new SplitPaymentFactory(bank, command);
            ///default -> throw new IllegalArgumentException("Invalid command");
            default -> {
                return null;
            }
        }

        return factory.createCommand();
    }

    public void ExecuteCommands() {
        Bank bank = createBank();

        for (CommandInput command : commands) {
            Command commandToExecute = CreateCommand(bank, command);
            if (commandToExecute != null) {
                commandToExecute.execute();
            }
        }
    }

}
