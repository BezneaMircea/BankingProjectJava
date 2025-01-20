package org.poo.bank.commands;


import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.commands.command_factory.SplitPaymentFactory;
import org.poo.bank.transactions.SplitPaymentTranscation;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.transactions.TransactionInput;
import org.poo.bank.users.User;


import java.util.List;

public final class CoustomSplitPayment extends SplitPayment {
    private final List<Double> amountForUsers;


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
    public CoustomSplitPayment(final Bank bank, final String command,
                               final List<String> accountsForSplit, final int timestamp,
                               final String currency, final double amount,
                               final SplitPaymentFactory.Type type,
                               final List<Double> amountForUsers) {
        super(bank, command, accountsForSplit, timestamp, currency, amount, type);
        this.amountForUsers = amountForUsers;
    }


    @Override
    protected void everyoneAccepted() {
        int nrAccounts = accountsForSplit.size();

        String error = null;
        for (int i = 0; i < nrAccounts; i++) {
            Account currentAccount = bank.getAccount(accountsForSplit.get(i));
            double currentAmount = amountForUsers.get(i);
            double exchangeRate = bank.getRate(currency, currentAccount.getCurrency());
            double totalSumToPay = currentAmount * exchangeRate;

            if (currentAccount.getBalance() < totalSumToPay) {
                error = String.format(Account.SPLIT_PAYMENT_ERROR, currentAccount.getIban());
                break;
            }
        }

        String description = String.format(SplitPaymentTranscation.SPLIT_PAYMENT_DESCRIPTION,
                amount, currency);
        TransactionInput input = new TransactionInput.Builder(Transaction.Type.SPLIT_PAYMENT_COUSTOM,
                timestamp, description)
                .currency(currency)
                .setAmountForUsers(amountForUsers)
                .involvedAccounts(accountsForSplit)
                .setSplitPaymentType(type.getString())
                .error(error)
                .build();


        for (int i = 0; i < nrAccounts; i++) {
            Account currentAccount = bank.getAccount(accountsForSplit.get(i));
            User owner = bank.getUser(currentAccount.getOwnerEmail());
            if (error == null) {
                double currentAmount = amountForUsers.get(i);
                double exchangeRate = bank.getRate(currency, currentAccount.getCurrency());
                double totalSumToPay = currentAmount * exchangeRate;
                currentAccount.setBalance(currentAccount.getBalance() - totalSumToPay);
            }
            addTransaction(input, owner, currentAccount);
        }
    }

    @Override
    protected TransactionInput createTransactionRejected() {
        String description = String.format(SplitPaymentTranscation.SPLIT_PAYMENT_DESCRIPTION,
                amount, currency);

        return new TransactionInput.Builder(Transaction.Type.SPLIT_PAYMENT_COUSTOM,
                timestamp, description)
                .involvedAccounts(accountsForSplit)
                .setSplitPaymentType(type.getString())
                .currency(currency)
                .setAmountForUsers(amountForUsers)
                .error(REJECTED)
                .build();
    }
}
