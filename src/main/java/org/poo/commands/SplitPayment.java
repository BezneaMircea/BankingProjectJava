package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.commands.transactions.SplitPaymentTranscation;
import org.poo.commands.transactions.Transaction;
import org.poo.commands.transactions.TransactionInput;
import org.poo.commands.transactions.transactionsfactory.SplitPaymenTransactionFactory;
import org.poo.commands.transactions.transactionsfactory.TransactionFactory;
import org.poo.users.User;

import java.util.List;

public class SplitPayment implements Command, Transactionable {
    private final Bank bank;
    private final String command;
    private final List<String> accountsForSplit;
    private final int timestamp;
    private final String currency;
    private final double amount;

    public SplitPayment(final Bank bank, final String command, final List<String> accountsForSplit,
                        final int timestamp, final String currency, final double amount) {
        this.bank = bank;
        this.command = command;
        this.accountsForSplit = accountsForSplit;
        this.timestamp = timestamp;
        this.currency = currency;
        this.amount = amount;
    }

    @Override
    public void execute() {
        String error = null;

        int nrAccounts = accountsForSplit.size();
        double amountToPay = amount / nrAccounts;
        for (String account : accountsForSplit) {
            Account currentAccount = bank.getIbanToAccount().get(account);
            if (currentAccount == null)
                return;

            User owner = bank.getEmailToUser().get(currentAccount.getOwnerEmail());
            if (owner == null)
                return;

            double exchangeRate = bank.getExchangeRates().getRate(currentAccount.getCurrency(), currency);
            double totalSumToPay = exchangeRate * amountToPay;
            if (error == null && currentAccount.getBalance() < totalSumToPay)
                error = String.format(SplitPaymentTranscation.SPLIT_PAYMENT_ERROR, currentAccount.getIban());


        }

        String description = String.format(SplitPaymentTranscation.SPLIT_PAYMENT_DESCRIPTION, amount, currency);
        TransactionInput input = new TransactionInput.Builder(timestamp, description)
                .currency(currency)
                .amount(amountToPay)
                .involvedAccounts(accountsForSplit)
                .error(error)
                .build();

        Transaction transaction = generateTransaction(input);
        if (error != null) {
            for (String account : accountsForSplit) {
                Account currentAccount = bank.getIbanToAccount().get(account);
                User owner = bank.getEmailToUser().get(currentAccount.getOwnerEmail());
                owner.getTransactions().add(transaction);
                currentAccount.getTransactions().add(transaction);
            }
        } else {
            for (String account : accountsForSplit) {
                Account currentAccount = bank.getIbanToAccount().get(account);
                User owner = bank.getEmailToUser().get(currentAccount.getOwnerEmail());
                double exchangeRate = bank.getExchangeRates().getRate(currency, currentAccount.getCurrency());
                double totalSumToPay = exchangeRate * amountToPay;
                currentAccount.setBalance(currentAccount.getBalance() - totalSumToPay);
                owner.getTransactions().add(transaction);
                currentAccount.getTransactions().add(transaction);
            }
        }
    }

    @Override
    public Transaction generateTransaction(TransactionInput input) {
        TransactionFactory factory = new SplitPaymenTransactionFactory(input);
        return  factory.createTransaction();
    }
}
