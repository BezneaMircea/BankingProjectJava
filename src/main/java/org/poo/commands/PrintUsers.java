package org.poo.commands;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.Bank;
import org.poo.utils.Utils;

/**
 * Class used to represent the printUsers command
 */
public final class PrintUsers implements Command {
    private final Bank bank;
    private final String command;
    private final int timestamp;

    /**
     * Constructor for the printUsers command
     * @param bank the receiver bank of the command
     * @param command the command name
     * @param timestamp timestamp of the command
     */
    public PrintUsers(final Bank bank, final String command, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.timestamp = timestamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        ObjectNode commandNode = Utils.mapper.createObjectNode();
        commandNode.put("command", command);
        commandNode.set("output", bank.usersToArrayNode());
        commandNode.put("timestamp", timestamp);

        bank.getOutput().add(commandNode);
    }
}
