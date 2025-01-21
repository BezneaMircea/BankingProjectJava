package org.poo.bank.commands;

import lombok.Getter;
import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.commands.command_factory.SplitPaymentFactory;
import org.poo.bank.transactions.SplitPaymentTranscation;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.transactions.TransactionInput;
import org.poo.bank.users.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to represent splitPayment command
 */
@Getter
public class SplitPayment implements Command, Transactionable {
    public static final String REJECTED = "One user rejected the payment.";

    protected final Bank bank;
    protected final String command;
    protected final List<String> accountsForSplit;
    protected final int timestamp;
    protected final SplitPaymentFactory.Type type;
    protected final String currency;
    protected final double amount;

    protected int accountsThatAccepted;
    protected List<User> users;
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
                        final int timestamp, final String currency, final double amount,
                        final SplitPaymentFactory.Type type) {
        this.bank = bank;
        this.command = command;
        this.accountsForSplit = accountsForSplit;
        this.timestamp = timestamp;
        this.currency = currency;
        this.amount = amount;
        this.type = type;

        accountsThatAccepted = 0;
        users = null;
    }

    @Override
    public void execute() {
        /// It s the first time this method is called
        if (users == null) {
            users = getUsers();
            if (users == null) {
                ///TODO:
                return;
            }
            addPaymentToUsers(users);
        }

        /// Someone declines the payment
        if (accountsThatAccepted == -1) {
            TransactionInput input = createTransactionRejected();
            addTransactionToAll(input);
            removePaymentFromUsers();
            return;
        }

        /// Not everyone accepted yet
        if (accountsThatAccepted != accountsForSplit.size())
            return;

        everyoneAccepted();
    }

    @Override
    public void addTransaction(final TransactionInput input, final User user, final Account account) {
        bank.generateTransaction(input).addTransaction(user, account);
    }

    private List<User> getUsers() {
        List<User> users = new ArrayList<>();

        for (String iban : accountsForSplit) {
            Account account = bank.getAccount(iban);
            if (account == null) {
                return null;
            }

            User ownerOfAccount = bank.getUser(account.getOwnerEmail());
            if (ownerOfAccount == null) {
                return null;
            }

            users.add(ownerOfAccount);
        }
        return users;
    }

    protected void addPaymentToUsers(List<User> users) {
        for (User user : users) {
            user.getAllSplitPayments().add(this);
        }
    }

    protected void removePaymentFromUsers() {
        for (User user : users) {
            user.getAllSplitPayments().remove(this);
        }
    }

    protected TransactionInput createTransactionRejected() {
        String description = String.format(SplitPaymentTranscation.SPLIT_PAYMENT_DESCRIPTION,
                amount, currency);

        return new TransactionInput.Builder(Transaction.Type.SPLIT_PAYMENT,
                timestamp, description)
                .involvedAccounts(accountsForSplit)
                .setSplitPaymentType(type.getString())
                .amount(amount)
                .currency(currency)
                .error(REJECTED)
                .build();
    }

    private void addTransactionToAll(TransactionInput input) {
        for (String iban : accountsForSplit) {
            Account account = bank.getAccount(iban);
            User owner = bank.getUser(account.getOwnerEmail());
            addTransaction(input, owner, account);
        }
    }

    protected void everyoneAccepted() {
        int nrAccounts = accountsForSplit.size();
        double amountToPay = amount / nrAccounts;

        String error = null;
        for (String account : accountsForSplit) {
            Account currentAccount = bank.getAccount(account);
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
                .setSplitPaymentType(type.getString())
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

    public void incrementAccountsThatAccepted() {
        accountsThatAccepted++;
    }

    public void refusePayment() {
        accountsThatAccepted = -1;
    }


}
