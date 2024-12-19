package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.commands.transactions.ChangeIntRateTransaction;
import org.poo.commands.transactions.Transaction;
import org.poo.commands.transactions.TransactionInput;
import org.poo.users.User;


/**
 * Command used to represent the changeInterestRate command
 */
public final class ChangeInterestRate implements Command, Transactionable {
    private final Bank bank;
    private final String command;
    private final String account;
    private final double interestRate;
    private final int timestamp;

    /**
     * Constructor for the changeInterestRate command
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
        Account accountToChangeInterest = bank.getAccount(account);
        if (accountToChangeInterest == null)
            return;

        User owner = bank.getUser(accountToChangeInterest.getOwnerEmail());
        if (owner == null)
            return;

        String error = accountToChangeInterest.changeInterest(interestRate);
        if (error != null) {
            bank.errorOccured(timestamp, command, error);
            return;
        }

        addTransaction(null, owner, accountToChangeInterest);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void addTransaction(TransactionInput input, User user, Account account) {
        String description = String.format(ChangeIntRateTransaction.IRATE_CHANGED, interestRate);
        input = new TransactionInput.Builder(Transaction.Type.CHANGE_INT_RATE, timestamp, description)
                .build();

        bank.generateTransaction(input).addTransaction(user, account);
    }
}
