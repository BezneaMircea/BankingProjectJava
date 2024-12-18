package org.poo.commands;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.utils.Utils;

public class SpendingReport implements Command {
    private final Bank bank;
    private final String command;
    private final int startTimestamp;
    private final int endTimestamp;
    private final String account;
    private final int timestamp;

    public SpendingReport(final Bank bank, final String command, final int startTimestamp,
                  final int endTimestamp, final String account, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.account = account;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        Account accountToCreateReport = bank.getIbanToAccount().get(account);
        if (accountToCreateReport == null) {
            ObjectNode outputNode = Utils.mapper.createObjectNode();
            outputNode.put("description", Account.NOT_FOUND);
            outputNode.put("timestamp", timestamp);

            ObjectNode commandNode = Utils.mapper.createObjectNode();
            commandNode.put("command", command);
            commandNode.set("output", outputNode);
            commandNode.put("timestamp", timestamp);

            bank.getOutput().add(commandNode);

            return;
        }

        ObjectNode spendingReportNode = Utils.mapper.createObjectNode();
        spendingReportNode.put("command", command);
        spendingReportNode.set("output", accountToCreateReport.spendingsReport(startTimestamp, endTimestamp));
        spendingReportNode.put("timestamp", timestamp);

        bank.getOutput().add(spendingReportNode);
    }
}
