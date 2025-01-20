package org.poo.bank.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.transactions.AddInterestTransaction;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.transactions.TransactionInput;
import org.poo.bank.users.User;

/**
 * Class used to represend the addInterest command
 */
public final class AddInterest implements Command, Transactionable {
    private final Bank bank;
    private final String command;
    private final String account;
    private final int timestamp;

    /**
     * Constructor for the addInterest command
     *
     * @param bank      the receiver bank of the command
     * @param command   the command name
     * @param account   the account that receives the interest
     * @param timestamp the timestamp of the command
     */
    public AddInterest(final Bank bank, final String command,
                       final String account, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.account = account;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        Account accountToAddInterest = bank.getAccount(account);
        if (accountToAddInterest == null) {
            return;
        }
        User owner = bank.getUser(accountToAddInterest.getOwnerEmail());
        String returnValue = accountToAddInterest.addInterest();
        try {
            double amount = Double.parseDouble(returnValue);
            TransactionInput input = new TransactionInput.Builder(Transaction.Type.ADD_INTEREST,
                    timestamp, AddInterestTransaction.INTEREST_INCOME)
                    .currency(accountToAddInterest.getCurrency())
                    .amount(amount)
                    .build();
            addTransaction(input, owner, accountToAddInterest);
        } catch (NumberFormatException e) {
            bank.errorOccured(timestamp, command, returnValue);
        }
    }


    @Override
    public void addTransaction(TransactionInput input, User user, Account account) {
        bank.generateTransaction(input).addTransaction(user, account);
    }
}
