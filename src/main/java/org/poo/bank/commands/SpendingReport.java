package org.poo.bank.commands;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.utils.Utils;

/**
 * Class used to represent the spendingReport command
 */
public final class SpendingReport implements Command {
    private final Bank bank;
    private final String command;
    private final int startTimestamp;
    private final int endTimestamp;
    private final String account;
    private final int timestamp;

    /**
     * Constructor for the spendingReport command
     *
     * @param bank           the receiver bank of the command
     * @param command        the command name
     * @param startTimestamp starting timestamp for the report
     * @param endTimestamp   ending timestamp for the report
     * @param account        IBAN of the account to generate report to
     * @param timestamp      timestamp of the command
     */
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
        Account accountToCreateReport = bank.getAccount(account);
        if (accountToCreateReport == null) {
            bank.errorOccured(timestamp, command, Account.NOT_FOUND);
            return;
        }

        ObjectNode spendingReportNode = Utils.MAPPER.createObjectNode();
        spendingReportNode.put("command", command);
        spendingReportNode.set("output", accountToCreateReport.spendingsReport(startTimestamp,
                                                                                endTimestamp));
        spendingReportNode.put("timestamp", timestamp);

        bank.getOutput().add(spendingReportNode);
    }
}
