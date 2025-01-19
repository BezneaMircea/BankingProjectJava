package org.poo.bank;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.poo.bank.commands.Command;

import org.poo.bank.commands.command_factory.*;

import org.poo.bank.commerciants.Commerciant;
import org.poo.bank.commerciants.commerciant_factory.ClothesCommerciantFactory;
import org.poo.bank.commerciants.commerciant_factory.CommerciantFactory;
import org.poo.bank.commerciants.commerciant_factory.FoodCommerciantFactory;
import org.poo.bank.commerciants.commerciant_factory.TechCommerciantFactory;
import org.poo.bank.commerciants.commerciant_strategies.CashBackStrategy;
import org.poo.bank.commerciants.commerciant_strategies.CashBackStrategyFactory;
import org.poo.bank.commerciants.commerciant_strategies.NrTransactionsStrategy;
import org.poo.bank.commerciants.commerciant_strategies.SpendingThresholdStrategy;
import org.poo.bank.users.User;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bank.users.usersfactory.BasicUserFactory;
import org.poo.bank.users.usersfactory.UserFactory;
import org.poo.fileio.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Class used for setting up the bank, it also is the
 * invoker for the Bank class (Creates commands and calls them on bank)
 */
public class BankSetup {
    private final UserInput[] users;
    private final ExchangeInput[] exchangeRates;
    private final CommandInput[] commands;
    private final CommerciantInput[] commerciants;
    private final ArrayNode output;

    /**
     * Constructor for the BankSettup (Invoker)
     *
     * @param inputData the input data
     * @param output    the output ArrayNode where we will put the commands output
     */
    public BankSetup(final ObjectInput inputData, final ArrayNode output) {
        users = inputData.getUsers();
        exchangeRates = inputData.getExchangeRates();
        commands = inputData.getCommands();
        commerciants = inputData.getCommerciants();
        this.output = output;
    }

    /**
     * Method used to execute the commands given in input.
     * Internally, it calls two private methods that create the bank (Receiver) and
     * commands and thencalls command.execute(). If the given command is not in the
     * commands list nothing happens
     */
    public void executeCommands() {
        Bank bank = createBank();

        for (CommandInput command : commands) {
            Command commandToExecute = createCommand(bank, command);
            if (commandToExecute != null) {
                commandToExecute.execute();
            }
        }
    }

    private Command createCommand(final Bank bank, final CommandInput command) {
        CommandFactory factory;

        switch (command.getCommand()) {
            case "addAccount" -> factory = new AddAccountFactory(bank, command);
            case "printUsers" -> factory = new PrintUsersFactory(bank, command);
            case "addFunds" -> factory = new AddFundsFactory(bank, command);
            case "deleteAccount" -> factory = new DeleteAccountFactory(bank, command);
            case "deleteCard" -> factory = new DeleteCardFactory(bank, command);
            case "setMinimumBalance" -> factory = new SetMinBalanceFactory(bank, command);
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
            case "upgradePlan" -> factory = new UpgradePlanFactory(bank, command);
            case "withdrawSavings" -> factory = new WithdrawSavingsFactory(bank, command);
            case "createCard", "createOneTimeCard" -> factory = new CreateCardFactory(bank,
                                                                                      command);
            default -> {
                return null;
            }
            ///default -> throw new IllegalArgumentException("Invalid command");

        }

        return factory.createCommand();
    }

    private Bank createBank() {
        return new Bank(createUsers(), createExchange(), createCommerciants(), output);
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
        if (exchangeRates == null || exchangeRates.length == 0) {
            return null;
        }

        Graph<String, DefaultWeightedEdge>
                myExchange = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
        for (ExchangeInput exchange : exchangeRates) {
            myExchange.addVertex(exchange.getFrom());
            myExchange.addVertex(exchange.getTo());

            DefaultWeightedEdge edgeFromTo = myExchange.addEdge(exchange.getFrom(),
                    exchange.getTo());
            myExchange.setEdgeWeight(edgeFromTo, exchange.getRate());

            DefaultWeightedEdge edgeToFrom = myExchange.addEdge(exchange.getTo(),
                    exchange.getFrom());
            myExchange.setEdgeWeight(edgeToFrom, 1 / exchange.getRate());
        }

        return myExchange;
    }

    private List<Commerciant> createCommerciants() {
        List<Commerciant> bankCommerciants = new ArrayList<>();

        for (CommerciantInput commerciant : commerciants) {
            CommerciantFactory factory;
            Commerciant.Type commerciantType = Commerciant.Type.fromString(commerciant.getType());

            switch (commerciantType) {
                case TECH -> factory = new TechCommerciantFactory(commerciant);
                case CLOTHES -> factory = new ClothesCommerciantFactory(commerciant);
                case FOOD -> factory = new FoodCommerciantFactory(commerciant);

                default -> throw new IllegalStateException("Unexpected value: " + commerciantType);
            }
            bankCommerciants.add(factory.createCommerciant());
        }

        return bankCommerciants;
    }

}
