package org.poo.bank.commands;

import lombok.Getter;
import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.accounts.AccountInput;
import org.poo.bank.commands.transactions.Transaction;
import org.poo.bank.commands.transactions.TransactionInput;
import org.poo.bank.users.User;

/**
 * Class used to represent the addAccount command
 */
@Getter
public final class AddAccount implements Command, Transactionable {
    private final Bank bank;
    private final String command;
    private final String email;
    private final String currency;
    private final Account.Type accountType;
    private final int timestamp;
    private final double interestRate;

    /**
     * Constructor for the AddAccount class
     *
     * @param bank         the receiver bank of the command
     * @param command      the command name
     * @param email        the email of the user that will own the account
     * @param currency     currency of the account
     * @param accountType  the type of the account ("savings", "classic")
     * @param timestamp    the timestamp of the command
     * @param interestRate the interest rate (optional, used for "savings" account)
     */
    public AddAccount(final Bank bank, final String command,
                      final String email, final String currency,
                      final Account.Type accountType, final int timestamp, final double interestRate) {
        this.bank = bank;
        this.command = command;
        this.email = email;
        this.currency = currency;
        this.accountType = accountType;
        this.timestamp = timestamp;
        this.interestRate = interestRate;
    }


    @Override
    public void execute() {
        AccountInput newAccountInput = new AccountInput.Builder(email, currency, accountType)
                .interestRate(interestRate)
                .build();

        Account accountToAdd = bank.createAccount(newAccountInput);
        String error = bank.addAccount(accountToAdd);

        /// Logic could be added here to print the error (if the given email wasn't valid)
        if (error != null) {
            return;
        }

        addTransaction(null, bank.getEmailToUser().get(accountToAdd.getOwnerEmail()), accountToAdd);
    }


    @Override
    public void addTransaction(TransactionInput input, final User user, final Account account) {
        input = new TransactionInput.Builder(Transaction.Type.ADD_ACCOUNT, timestamp, Account.ACCOUNT_CREATED)
                .build();

        bank.generateTransaction(input).addTransaction(user, account);
    }
}

