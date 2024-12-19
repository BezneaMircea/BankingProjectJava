package org.poo.bank.commands;

import org.poo.bank.accounts.Account;
import org.poo.bank.commands.transactions.TransactionInput;
import org.poo.bank.users.User;

/**
 * Interface implemented by commands that should generate transactions
 */
public interface Transactionable {
    /**
     * Method used to add a transaction to a user and an account (e.g. AddAccount)
     * @param input The transaction input. This should be null if
     *              you want to create the TransactionInput object in this method
     * @param user The user where the transaction will be added
     * @param account The account where the transaction will be added
     */
    void addTransaction(TransactionInput input, User user, Account account);
}
