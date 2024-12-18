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

/**
 * Command used to represent the changeInterestRate command
 */
public class ChangeInterestRate implements Command {
    private final Bank bank;
    private final String command;
    private final String account;
    private final double interestRate;
    private final int timestamp;

    /**
     *
     * @param bank the receiver bank of the command
     * @param command the command name
     * @param account the account to change the interest rate to
     * @param interestRate the new interest rate
     * @param timestamp the timestamp of the command
     */
    public ChangeInterestRate(final Bank bank, final String command, final String account,
                              final double interestRate, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.account = account;
        this.interestRate = interestRate;
        this.timestamp = timestamp;
    }

    /**
     * {@inheritDoc}
     */
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
            bank.errorOccured(timestamp, command, error);
            return;
        }

        String description = String.format(ChangeIntRateTransaction.IRATE_CHANGED, interestRate);
        TransactionInput input = new TransactionInput.Builder(Transaction.Type.CHANGE_INT_RATE, timestamp, description)
                .build();

        bank.generateTransaction(input).addTransaction(owner, accountToChangeInterest);
    }


}
