package org.poo.commands;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.utils.Utils;

public class AddInterest implements Command {
    private final Bank bank;
    private final String command;
    private final String account;
    private final int timestamp;

    public AddInterest(final Bank bank, final String command,
                       final String account, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.account = account;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        Account accountToAddInterest = bank.getIbanToAccount().get(account);
        if (accountToAddInterest == null)
            return;

        String error = accountToAddInterest.addInterest();
        if (error != null) {
            ObjectNode outputNode = Utils.mapper.createObjectNode();
            outputNode.put("timestamp", timestamp);
            outputNode.put("description", error);

            ObjectNode errorNode = Utils.mapper.createObjectNode();
            errorNode.put("command", command);
            errorNode.set("output", outputNode);
            errorNode.put("timestamp", timestamp);

            bank.getOutput().add(errorNode);
        }
    }
}
