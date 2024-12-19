package org.poo.commands;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.Bank;
import org.poo.users.User;
import org.poo.utils.Utils;

/**
 * Class used to represent the printTransactions command
 */
public final class PrintTransactions implements Command {
    private final Bank bank;
    private String command;
    private final String email;
    private final int timestamp;

    /**
     * Constructor for the printTransactions command
     * @param bank the receiver bank of the command
     * @param command the command name
     * @param email the email of the user to print transactions for
     * @param timestamp timestamp of the command
     */
    public PrintTransactions(final Bank bank, final String command,
                             final String email, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.email = email;
        this.timestamp = timestamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        User user = bank.getUser(email);
        if (user == null)
            return;

        ObjectNode transactionsNode = Utils.mapper.createObjectNode();
        transactionsNode.put("command", command);
        transactionsNode.set("output", user.transactionsToObjectNode());
        transactionsNode.put("timestamp", timestamp);

        bank.getOutput().add(transactionsNode);
    }
}
