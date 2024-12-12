package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.Bank;
import org.poo.utils.Utils;

public class PrintUsers implements Command {
    private final Bank bank;
    private final String command;
    private final int timestamp;

    public PrintUsers(Bank bank, String command, int timestamp) {
        this.bank = bank;
        this.command = command;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        ObjectNode commandNode = Utils.mapper.createObjectNode();
        commandNode.put("command", command);
        commandNode.set("output", bank.usersToArrayNode());
        commandNode.put("timestamp", timestamp);

        bank.getOutput().add(commandNode);
    }
}
