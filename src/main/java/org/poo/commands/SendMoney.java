package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.commands.transactions.SendMoneyTransaction;
import org.poo.commands.transactions.Transaction;
import org.poo.commands.transactions.TransactionInput;
import org.poo.commands.transactions.transactionsfactory.AddAccountTransactionFactory;
import org.poo.commands.transactions.transactionsfactory.CreateCardTransactionFactory;
import org.poo.commands.transactions.transactionsfactory.SendMoneyTransactionFactory;
import org.poo.commands.transactions.transactionsfactory.TransactionFactory;
import org.poo.users.User;

public class SendMoney implements Command, Transactionable {
    private final Bank bank;
    private final String command;
    private final String account;
    private final double amount;
    private final String receiver;
    private final int timestamp;
    private final String description;

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


    @Override
    public void execute() {
        Account senderAccount = bank.getIbanToAccount().get(account);
        if (senderAccount == null)
            return;

        User senderUser = bank.getEmailToUser().get(senderAccount.getOwnerEmail());
        if (senderUser == null)
            return;

        Account receiverAccount = bank.getIbanToAccount().get(receiver);
        if (senderUser.getAliases().containsKey(receiver))
            receiverAccount = senderUser.getAliases().get(receiver);

        if (receiverAccount == null)
            return;

        User receiverUser = bank.getEmailToUser().get(receiverAccount.getOwnerEmail());
        if (receiverUser == null) {
            return;
        }

        String error = null;
        if (senderAccount.getBalance() < amount) {
            TransactionInput input = new TransactionInput.Builder(Transaction.Type.SEND_MONEY, timestamp, description)
                    .error(SendMoneyTransaction.INSUFFICIENT_FUNDS)
                    .build();
            bank.generateTransaction(input).addTransaction(senderUser, senderAccount);
            return;
        }

        double convertRate = bank.getExchangeRates().getRate(senderAccount.getCurrency(), receiverAccount.getCurrency());
        double receivedSum = amount * convertRate;

        senderAccount.transfer(receiverAccount, amount, receivedSum);

        TransactionInput transactionSent = new TransactionInput.Builder(Transaction.Type.SEND_MONEY, timestamp, description)
                .senderIBAN(account)
                .receiverIBAN(receiver)
                .amount(amount)
                .currency(senderAccount.getCurrency())
                .transferType(SendMoneyTransaction.SENT)
                .error(null)
                .build();

        TransactionInput transactionReceived = new TransactionInput.Builder(Transaction.Type.SEND_MONEY, timestamp, description)
                .senderIBAN(account)
                .receiverIBAN(receiver)
                .amount(receivedSum)
                .currency(receiverAccount.getCurrency())
                .transferType(SendMoneyTransaction.RECEIVED)
                .error(null)
                .build();

        generateTransaction(transactionSent).addTransaction(senderUser, senderAccount);
        generateTransaction(transactionReceived).addTransaction(receiverUser, receiverAccount);

    }

    @Override
    public Transaction generateTransaction(TransactionInput input) {
        TransactionFactory factory = new SendMoneyTransactionFactory(input);
        return factory.createTransaction();
    }
}
