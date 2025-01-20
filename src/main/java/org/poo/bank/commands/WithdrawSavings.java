package org.poo.bank.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.commerciants.Commerciant;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.transactions.TransactionInput;
import org.poo.bank.transactions.WithdrawSavingsTransaction;
import org.poo.bank.users.User;

public final class WithdrawSavings implements Command, Transactionable {
    private final Bank bank;
    private final String command;
    private final String account;
    private final double amount;
    private final String currency;
    private final int timestamp;

    public WithdrawSavings(final Bank bank, final String command, final String account,
                           final double amount, final String currency, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.account = account;
        this.amount = amount;
        this.currency = currency;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        Account savingsAccount = bank.getAccount(account);
        if (savingsAccount == null) {
            return;
        }

        User owner = bank.getUser(savingsAccount.getOwnerEmail());
        if (owner == null) {
            return;
        }

        if (!owner.isOldEnough()) {
            TransactionInput input = new TransactionInput.Builder(Transaction.Type.WITHDRAW_SAVINGS,
                    timestamp, User.NOT_OLD_ENOUGH)
                    .error(User.NOT_OLD_ENOUGH)
                    .build();
            addTransaction(input, owner, savingsAccount);
            return;
        }

        Account receiverAccount = owner.getClassicAccWithCurrency(currency);
        if (receiverAccount == null) {
            TransactionInput input = new TransactionInput.Builder(Transaction.Type.WITHDRAW_SAVINGS,
                    timestamp, User.DONT_HAVE_CLASSIC)
                    .error(User.DONT_HAVE_CLASSIC)
                    .build();
            addTransaction(input, owner, savingsAccount);
            return;
        }


        double secondRate = bank.getRate(currency, savingsAccount.getCurrency());
        double convertedTotalAmount = secondRate * amount;

        savingsAccount.setBalance(savingsAccount.getBalance() - convertedTotalAmount);
        receiverAccount.setBalance(receiverAccount.getBalance() + amount);

        TransactionInput input = new TransactionInput.Builder(Transaction.Type.WITHDRAW_SAVINGS,
                timestamp, WithdrawSavingsTransaction.SAVINGS_WITHDRAWAL)
                .amount(amount)
                .senderIBAN(account)
                .receiverIBAN(receiverAccount.getIban())
                .build();

        addTransaction(input, owner, receiverAccount);
        addTransaction(input, owner, savingsAccount);
    }

    @Override
    public void addTransaction(TransactionInput input, User user, Account account) {
        bank.generateTransaction(input).addTransaction(user, account);
    }
}
