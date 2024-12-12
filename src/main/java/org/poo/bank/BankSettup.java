package org.poo.bank;

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

    private List<ExchangeRate> createExchange() {
        List<ExchangeRate> myExchange = new ArrayList<>();
        for (ExchangeInput exchange : exchangeRates) {
            myExchange.add(new ExchangeRate(exchange));
        }

        return myExchange;
    }

    private Command CreateCommand(Bank bank, CommandInput command) {
        CommandFactory factory;

        switch (command.getCommand()) {
            case "addAccount" -> factory = new AddAccountFactory(bank, command);
            case "printUsers" -> factory = new PrintUsersFactory(bank, command);
            case "createCard" -> factory = new CreateCardFactory(bank, command);
            case "addFunds" -> factory = new AddFundsFactory(bank, command);
            case "deleteAccount" -> factory = new DeleteAccountFactory(bank, command);
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
