package org.poo.bank.commands;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.transactions.TransactionInput;
import org.poo.bank.users.User;
import org.poo.utils.Utils;


/**
 * Class used to represent the deleteAccount command
 */
public final class DeleteAccount implements Command, Transactionable {
    private final Bank bank;
    private final String command;
    private final String account;
    private final String email;
    private final int timestamp;

    /**
     * Constructor for the deleteAccount command
     *
     * @param bank      the receiver bank of the command
     * @param command   the command name
     * @param account   the IBAN of the account to be deleted
     * @param email     the email of the owner
     * @param timestamp timestamp of the command
     */
    public DeleteAccount(final Bank bank, final String command,
                         final String account, final String email, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.account = account;
        this.email = email;
        this.timestamp = timestamp;
    }


    @Override
    public void execute() {
        Account accountToDelete = bank.getAccount(account);
        User ownerOfAccount = bank.getUser(email);

        if (accountToDelete == null || ownerOfAccount == null) {
            return;
        }


        String ret = bank.deleteAccount(accountToDelete);

        ObjectNode outputNode = Utils.MAPPER.createObjectNode();
        if (ret.equals(Account.DELETED)) {
            outputNode.put("success", ret);
            outputNode.put("timestamp", timestamp);
        } else if (ret.equals(Account.CANT_DELETE)) {
            outputNode.put("error", ret);
            outputNode.put("timestamp", timestamp);

            addTransaction(null, ownerOfAccount, accountToDelete);
        }

        ObjectNode deleteAccountNode = Utils.MAPPER.createObjectNode();
        deleteAccountNode.put("command", command);
        deleteAccountNode.set("output", outputNode);
        deleteAccountNode.put("timestamp", timestamp);

        bank.getOutput().add(deleteAccountNode);
    }

    @Override
    public void addTransaction(TransactionInput input, final User user, final Account acc) {
        input = new TransactionInput.Builder(Transaction.Type.DELETE_ACCOUNT,
                                             timestamp, Account.FUNDS_REMAINING).build();

        bank.generateTransaction(input).addTransaction(user, acc);
    }
}
