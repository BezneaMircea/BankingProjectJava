package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.Bank;
import org.poo.commands.transactions.Transaction;
import org.poo.users.User;
import org.poo.utils.Utils;

public class PrintTransactions implements Command {
    private final Bank bank;
    private String command;
    private final String email;
    private final int timestamp;

    public PrintTransactions(final Bank bank, final String command,
                             final String email, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.email = email;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        User user = bank.getEmailToUser().get(email);

        ObjectNode transactionsNode = Utils.mapper.createObjectNode();
        transactionsNode.put("command", command);

        ArrayNode transactionsArray = Utils.mapper.createArrayNode();
        for (Transaction transaction : user.getTransactions()) {
            transactionsArray.add(transaction.toJson());
        }

        transactionsNode.set("output", transactionsArray);
        transactionsNode.put("timestamp", timestamp);

        bank.getOutput().add(transactionsNode);
    }
}
