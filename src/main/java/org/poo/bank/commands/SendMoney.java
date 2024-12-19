package org.poo.bank.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.commands.transactions.SendMoneyTransaction;
import org.poo.bank.commands.transactions.Transaction;
import org.poo.bank.commands.transactions.TransactionInput;
import org.poo.bank.users.User;

/**
 * Class used to represent the sendMoney command
 */
public final class SendMoney implements Command, Transactionable {
    private final Bank bank;
    private final String command;
    private final String account;
    private final double amount;
    private final String receiver;
    private final int timestamp;
    private final String description;

    /**
     * Constructor for the sendMoney command
     * @param bank the receiver bank of the command
     * @param command the command name
     * @param account IBAN of the sender account
     * @param amount amount to transfer
     * @param receiver IBAN of the receiver account
     * @param timestamp timestamp of the command
     * @param description description of the transfer
     */
    public SendMoney(final Bank bank, final String command,
                     final String account, final double amount,
                     final String receiver, final int timestamp, final String description) {
        this.bank = bank;
        this.command = command;
        this.account = account;
        this.amount = amount;
        this.receiver = receiver;
        this.timestamp = timestamp;
        this.description = description;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        Account senderAccount = bank.getAccount(account);
        if (senderAccount == null)
            return;

        User senderUser = bank.getUser(senderAccount.getOwnerEmail());
        if (senderUser == null)
            return;

        Account receiverAccount = bank.getAccount(receiver);
        if (senderUser.hasAlias(receiver))
            receiverAccount = senderUser.getAccountFromAlias(receiver);

        if (receiverAccount == null)
            return;

        User receiverUser = bank.getUser(receiverAccount.getOwnerEmail());
        if (receiverUser == null) {
            return;
        }

        if (senderAccount.getBalance() < amount) {
            TransactionInput input = new TransactionInput.Builder(Transaction.Type.SEND_MONEY, timestamp, description)
                    .error(Account.INSUFFICIENT_FUNDS)
                    .build();

            addTransaction(input, senderUser, senderAccount);
            return;
        }

        double convertRate = bank.getExchangeRates().getRate(senderAccount.getCurrency(), receiverAccount.getCurrency());
        double receivedSum = amount * convertRate;
        senderAccount.transfer(receiverAccount, amount, receivedSum);

        TransactionInput transactionSent = createSendInput(senderAccount);
        TransactionInput transactionReceived = createReceiveInput(receiverAccount, receivedSum);

        addTransaction(transactionSent, senderUser, senderAccount);
        addTransaction(transactionReceived, receiverUser, receiverAccount);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void addTransaction(TransactionInput input, User user, Account account) {
        bank.generateTransaction(input).addTransaction(user, account);
    }


    private TransactionInput
    createSendInput(final Account senderAccount) {
        return new TransactionInput.Builder(Transaction.Type.SEND_MONEY, timestamp, description)
                .senderIBAN(account)
                .receiverIBAN(receiver)
                .amount(amount)
                .currency(senderAccount.getCurrency())
                .transferType(SendMoneyTransaction.SENT)
                .error(null)
                .build();
    }

    private TransactionInput
    createReceiveInput(Account receiverAccount, final double receivedSum) {
        return new TransactionInput.Builder(Transaction.Type.SEND_MONEY, timestamp, description)
                .senderIBAN(account)
                .receiverIBAN(receiver)
                .amount(receivedSum)
                .currency(receiverAccount.getCurrency())
                .transferType(SendMoneyTransaction.RECEIVED)
                .error(null)
                .build();
    }

}
