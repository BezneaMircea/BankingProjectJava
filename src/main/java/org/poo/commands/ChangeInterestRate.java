package org.poo.commands;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.commands.transactions.ChangeIntRateTransaction;
import org.poo.commands.transactions.Transaction;
import org.poo.commands.transactions.TransactionInput;
import org.poo.commands.transactions.transactionsfactory.ChangeIntRateTransactionFactory;
import org.poo.commands.transactions.transactionsfactory.TransactionFactory;
import org.poo.users.User;
import org.poo.utils.Utils;

public class ChangeInterestRate implements Command, Transactionable {
    private final Bank bank;
    private final String command;
    private final String account;
    private final double interestRate;
    private final int timestamp;

    public ChangeInterestRate(final Bank bank, final String command, final String account,
                              final double interestRate, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.account = account;
        this.interestRate = interestRate;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        Account accountToChangeInterest = bank.getIbanToAccount().get(account);
        if (accountToChangeInterest == null)
            return;

        User owner = bank.getEmailToUser().get(accountToChangeInterest.getOwnerEmail());
        if (owner == null)
            return;

        String error = accountToChangeInterest.changeInterest(interestRate);

        if (error != null) {
            ObjectNode outputNode = Utils.mapper.createObjectNode();
            outputNode.put("timestamp", timestamp);
            outputNode.put("description", error);

            ObjectNode errorNode = Utils.mapper.createObjectNode();
            errorNode.put("command", command);
            errorNode.set("output", outputNode);
            errorNode.put("timestamp", timestamp);

            bank.getOutput().add(errorNode);

            return;
        }

        String description = String.format(ChangeIntRateTransaction.IRATE_CHANGED, interestRate);
        TransactionInput input = new TransactionInput.Builder(timestamp, description)
                .build();

        Transaction transaction = generateTransaction(input);
        owner.getTransactions().add(transaction);
        accountToChangeInterest.getTransactions().add(transaction);
    }

    @Override
    public Transaction generateTransaction(TransactionInput input) {
        TransactionFactory factory = new ChangeIntRateTransactionFactory(input);
        return factory.createTransaction();
    }
}
