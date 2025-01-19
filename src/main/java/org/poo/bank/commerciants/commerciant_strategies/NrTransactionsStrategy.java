package org.poo.bank.commerciants.commerciant_strategies;


import org.poo.bank.accounts.Account;
import org.poo.bank.commerciants.*;
import org.poo.bank.users.users_strategy.UserStrategy;

public class NrTransactionsStrategy implements CashBackStrategy{
    public static final double FOOD_CASHBACK = 0.02;
    public static final int FOOD_THRESHOLD = 2;

    public static final double CLOTHES_CASHBACK = 0.05;
    public static final int CLOTHES_THRESHOLD = 5;

    public static final double TECH_CASHBACK = 0.1;
    public static final int TECH_THRESHOLD = 10;




    @Override
    public void cashBack(final UserStrategy ownerStrategy, final Account account,
                         final TechCommerciant commerciant, final double amount,
                         final double conversionRate) {
        double cashBackSum = 0;
        if (account.getBonuses().hasBonus(AccountBonuses.BonusType.TECH)) {
            cashBackSum = TECH_CASHBACK * amount;
            account.getBonuses().setBonusUsed(AccountBonuses.BonusType.TECH);
        }

        account.setBalance(account.getBalance() + cashBackSum);
        commerciant.incrementAccountTransactions(account);

        tryGiveBonus(account, commerciant.getNrAccountTransactions(account));
    }

    @Override
    public void cashBack(final UserStrategy ownerStrategy, final Account account,
                         final FoodCommerciant commerciant, final double amount,
                         final double conversionRate) {
        double cashBackSum = 0;
        if (account.getBonuses().hasBonus(AccountBonuses.BonusType.FOOD)) {
            cashBackSum = FOOD_CASHBACK * amount;
            account.getBonuses().setBonusUsed(AccountBonuses.BonusType.FOOD);
        }

        account.setBalance(account.getBalance() + cashBackSum);
        commerciant.incrementAccountTransactions(account);

        tryGiveBonus(account, commerciant.getNrAccountTransactions(account));
    }

    @Override
    public void cashBack(final UserStrategy ownerStrategy, final Account account,
                         final ClothesCommerciant commerciant, final double amount,
                         final double conversionRate) {
        double cashBackSum = 0;
        if (account.getBonuses().hasBonus(AccountBonuses.BonusType.CLOTHES)) {
            cashBackSum = CLOTHES_CASHBACK * amount;
            account.getBonuses().setBonusUsed(AccountBonuses.BonusType.CLOTHES);
        }

        account.setBalance(account.getBalance() + cashBackSum);
        commerciant.incrementAccountTransactions(account);

        tryGiveBonus(account, commerciant.getNrAccountTransactions(account));
    }

    private void tryGiveBonus(Account account, int nrOfTransactions) {
        AccountBonuses bonuses = account.getBonuses();

        if (nrOfTransactions == FOOD_THRESHOLD
                && !bonuses.usedBonus(AccountBonuses.BonusType.FOOD)) {
            bonuses.giveBonus(AccountBonuses.BonusType.FOOD);
        }

        if (nrOfTransactions == CLOTHES_THRESHOLD
                && !bonuses.usedBonus(AccountBonuses.BonusType.CLOTHES)) {
            bonuses.giveBonus(AccountBonuses.BonusType.CLOTHES);
        }

        if (nrOfTransactions == TECH_THRESHOLD
                && !bonuses.usedBonus(AccountBonuses.BonusType.TECH)) {
            bonuses.giveBonus(AccountBonuses.BonusType.TECH);
        }
    }

}
