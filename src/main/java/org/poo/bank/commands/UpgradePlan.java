package org.poo.bank.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;

import org.poo.bank.commerciants.Commerciant;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.transactions.TransactionInput;
import org.poo.bank.transactions.UpgradePlanTransaction;
import org.poo.bank.users.User;
import org.poo.bank.users.users_strategy.UserStrategy;
import org.poo.bank.users.users_strategy.UserStrategyFactory;

public final class UpgradePlan implements Command, Transactionable {
    /// These constants are in the "RON" currency
    public static final int MIN_PAYMENT_FOR_UPGRADE = 300;
    public static final int MIN_NR_OF_PAYMENTS_FOR_UPGRADE = 5;

    private final Bank bank;
    private final String command;
    private final String newPlanType;
    private final String accountIban;
    private final int timestamp;

    public UpgradePlan(final Bank bank, final String command, final String newPlanType,
                       final String accountIban, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.newPlanType = newPlanType;
        this.accountIban = accountIban;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        Account account = bank.getAccount(accountIban);
        if (account == null) {
            bank.errorOccured(timestamp, command, Account.NOT_FOUND);
            return;
        }

        User associatedUser = bank.getUser(account.getOwnerEmail());
        UserStrategy currentStrategy = associatedUser.getStrategy();

        UserStrategy.Type wantedType = UserStrategy.Type.fromString(newPlanType);
        UserStrategy targetedStrategy = UserStrategyFactory.createStrategy(wantedType);

        String error = null;
        String neededSumString = currentStrategy.accept(targetedStrategy);
        try {
            double neededSum = Double.parseDouble(neededSumString);
            double convertedSum = neededSum * bank.getRate(Commerciant.MAIN_CURRENCY,
                                                           account.getCurrency());
            if (account.getBalance() < convertedSum) {
                error = Account.INSUFFICIENT_FUNDS;
            } else {
                account.setBalance(account.getBalance() - convertedSum);
                associatedUser.setStrategy(UserStrategy.Type.fromString(newPlanType));
            }
        } catch (NumberFormatException e) {
            error = neededSumString;
        }



        TransactionInput transactionInput = new TransactionInput
                .Builder(Transaction.Type.UPGRADE_PLAN,
                         timestamp, UpgradePlanTransaction.UPGRADE_PLAN)
                .account(accountIban)
                .newPlanType(newPlanType)
                .error(error)
                .build();
        addTransaction(transactionInput, associatedUser, account);
    }

    @Override
    public void addTransaction(final TransactionInput input,
                               final User user,
                               final Account associatedAccount) {
        bank.generateTransaction(input).addTransaction(user, associatedAccount);
    }
}
