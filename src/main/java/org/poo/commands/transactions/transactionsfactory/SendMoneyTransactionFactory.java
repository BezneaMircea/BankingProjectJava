package org.poo.commands.transactions.transactionsfactory;

import org.poo.commands.transactions.SendMoneyTransaction;
import org.poo.commands.transactions.Transaction;
import org.poo.commands.transactions.TransactionInput;

public class SendMoneyTransactionFactory implements TransactionFactory {
    private final int timestamp;
    private final String description;
    private final String senderIBAN;
    private final String receiverIBAN;
    private final double amount;
    private final String currency;
    private final String transferType;
    private final String error;

    public SendMoneyTransactionFactory(TransactionInput input) {
        timestamp = input.getTimestamp();
        description = input.getDescription();
        senderIBAN = input.getSenderIBAN();
        receiverIBAN = input.getReceiverIBAN();
        amount = input.getAmount();
        currency = input.getCurrency();
        transferType = input.getTransferType();
        error = input.getError();
    }
    @Override
    public Transaction createTransaction() {
        return new SendMoneyTransaction(timestamp, description,
                                        senderIBAN, receiverIBAN, amount,
                                        currency, transferType, error);
    }
}
