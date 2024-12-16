package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.commands.transactions.Transaction;
import org.poo.commands.transactions.TransactionInput;
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
        for (String account : accountsForSplit) {
            Account currentAccout = bank.getIbanToAccount().get(account);
            if (currentAccout == null)
                return;

            User owner = bank.getEmailToUser().get(currentAccout.getOwnerEmail());
            if (owner == null)
                return;

            double exchangeRate = bank.getExchangeRates().getRate(currentAccout.getCurrency(), currency);
        }
    }

    @Override
    public Transaction generateTransaction(TransactionInput input) {
        return null;
    }
}
