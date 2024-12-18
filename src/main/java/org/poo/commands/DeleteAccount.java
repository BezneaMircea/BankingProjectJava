package org.poo.commands;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.commands.transactions.DeleteAccountTransaction;
import org.poo.commands.transactions.Transaction;
import org.poo.commands.transactions.TransactionInput;
import org.poo.commands.transactions.transactionsfactory.DeleteAccountTransactionFactory;
import org.poo.commands.transactions.transactionsfactory.TransactionFactory;
import org.poo.users.User;
import org.poo.utils.Utils;

public class DeleteAccount implements Command, Transactionable {
    private final Bank bank;
    private final String command;
    private final String account;
    private final String email;
    private final int timestamp;

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
        Account accountToDelete = bank.getIbanToAccount().get(account);
        User ownerOfAccount = bank.getEmailToUser().get(email);

        if (accountToDelete == null || ownerOfAccount == null)
            return;

        ObjectNode deleteAccountNode = Utils.mapper.createObjectNode();
        deleteAccountNode.put("command", command);

        ObjectNode outputNode = Utils.mapper.createObjectNode();
        String ret = bank.deleteAccount(accountToDelete);

        if (ret.equals(Account.DELETED)) {
            outputNode.put("success", ret);
            outputNode.put("timestamp", timestamp);
        } else if (ret.equals(Account.FUNDS_REMAINING)){
            outputNode.put("error", ret);
            outputNode.put("timestamp", timestamp);

            TransactionInput input = new TransactionInput.Builder(Transaction.Type.DELETE_ACCOUNT, timestamp, DeleteAccountTransaction.FUNDS_REMAINING)
                    .build();

            bank.generateTransaction(input).addTransaction(ownerOfAccount, accountToDelete);
        }

        deleteAccountNode.set("output", outputNode);
        deleteAccountNode.put("timestamp", timestamp);

        bank.getOutput().add(deleteAccountNode);
    }

    @Override
    public Transaction generateTransaction(TransactionInput input) {
        TransactionFactory factory = new DeleteAccountTransactionFactory(input);
        return factory.createTransaction();
    }
}
