package org.poo.bank.commands;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.utils.Utils;

/**
 * Class used to represent the report command
 */
public final class Report implements Command {
    private final Bank bank;
    private final String command;
    private final int startTimestamp;
    private final int endTimestamp;
    private final String account;
    private final int timestamp;

    /**
     * Constructor for the report command
     * @param bank the receiver bank of the command
     * @param command the command name
     * @param startTimestamp starting timestamp for the report
     * @param endTimestamp ending timestamp for the report
     * @param account IBAN of the account to generate report to
     * @param timestamp timestamp of the command
     */
    public Report(final Bank bank, final String command, final int startTimestamp,
                  final int endTimestamp, final String account, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.account = account;
        this.timestamp = timestamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        Account accountToCreateReport = bank.getAccount(account);
        if (accountToCreateReport == null) {
            bank.errorOccured(timestamp, command, Account.NOT_FOUND);
            return;
        }

        ObjectNode reportNode = Utils.MAPPER.createObjectNode();
        reportNode.put("command", command);
        reportNode.set("output", accountToCreateReport.generateReport(startTimestamp, endTimestamp));
        reportNode.put("timestamp", timestamp);

        bank.getOutput().add(reportNode);
    }
}
