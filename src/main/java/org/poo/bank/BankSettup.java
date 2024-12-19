package org.poo.bank;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.poo.commands.Command;
import org.poo.commands.command_factory.*;
import org.poo.fileio.*;
import org.poo.users.User;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.users.usersfactory.BasicUserFactory;
import org.poo.users.usersfactory.UserFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * Class used for setting up the bank, it also is the
 * invoker for the Bank class (Creates commands and calls them on bank)
 */
public class BankSettup {
    private final UserInput[] users;
    private final ExchangeInput[] exchangeRates;
    private final CommandInput[] commands;
    private final ArrayNode output;

    /**
     * Constructor for the BankSettup (Invoker)
     * @param inputData the input data
     * @param output the output ArrayNode where we will put the commands output
     */
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

        /// Switch statement can be added in case of new type of users apear in the project
        for (UserInput user : users) {
            UserFactory factory = new BasicUserFactory(user);
            bankUsers.add(factory.createUser());
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
            case "addInterest" -> factory = new AddInterestFactory(bank, command);
            case "changeInterestRate" -> factory = new ChangeInterestRateFactory(bank, command);
            case "report" -> factory = new ReportFactory(bank, command);
            case "spendingsReport" -> factory = new SpendingReportFactory(bank, command);
            ///default -> throw new IllegalArgumentException("Invalid command");
            default -> {
                return null;
            }
        }

        return factory.createCommand();
    }

    /**
     * Method used to execute the commands given in input.
     * Internally, it calls two private methods that create the bank (Receiver) and
     * commands and thencalls command.execute(). If the given command is not in the
     * commands list nothing happens
     */
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
