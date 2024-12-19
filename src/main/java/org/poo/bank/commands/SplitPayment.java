package org.poo.bank.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.transactions.SplitPaymentTranscation;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.transactions.TransactionInput;
import org.poo.bank.users.User;

import java.util.List;

/**
 * Class used to represent splitPayment command
 */
public final class SplitPayment implements Command, Transactionable {
    private final Bank bank;
    private final String command;
    private final List<String> accountsForSplit;
    private final int timestamp;
    private final String currency;
    private final double amount;

    /**
     * Constructor for the spendingReport command
     *
     * @param bank             the receiver bank of the command
     * @param command          the command name
     * @param accountsForSplit list of IBANs corresponding to the accounts involved
     *                         in the split payment
     * @param timestamp        timestamp of the command
     * @param currency         currency in which the payment is performed
     * @param amount           the amount that is to be paid
     */
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
        int nrAccounts = accountsForSplit.size();
        double amountToPay = amount / nrAccounts;

        String error = null;
        for (String account : accountsForSplit.reversed()) {
            Account currentAccount = bank.getAccount(account);
            if (currentAccount == null) {
                return;
            }

            User owner = bank.getUser(currentAccount.getOwnerEmail());
            if (owner == null) {
                return;
            }

            double exchangeRate = bank.getRate(currency, currentAccount.getCurrency());
            double totalSumToPay = exchangeRate * amountToPay;
            if (currentAccount.getBalance() < totalSumToPay) {
                error = String.format(Account.SPLIT_PAYMENT_ERROR, currentAccount.getIban());
                break;
            }
        }

        String description = String.format(SplitPaymentTranscation.SPLIT_PAYMENT_DESCRIPTION,
                amount, currency);

        TransactionInput input = new TransactionInput.Builder(Transaction.Type.SPLIT_PAYMENT,
                timestamp, description)
                .currency(currency)
                .amount(amountToPay)
                .involvedAccounts(accountsForSplit)
                .error(error)
                .build();

        for (String account : accountsForSplit) {
            Account currentAccount = bank.getAccount(account);
            User owner = bank.getUser(currentAccount.getOwnerEmail());
            if (error == null) {
                double exchangeRate = bank.getRate(currency, currentAccount.getCurrency());
                double totalSumToPay = exchangeRate * amountToPay;
                currentAccount.setBalance(currentAccount.getBalance() - totalSumToPay);
            }
            addTransaction(input, owner, currentAccount);
        }
    }

    @Override
    public void
    addTransaction(final TransactionInput input, final User user, final Account account) {
        bank.generateTransaction(input).addTransaction(user, account);
    }
}
