package org.poo.bank.commerciants.commerciant_strategies;

import org.poo.bank.accounts.Account;
import org.poo.bank.commerciants.AccountBonuses;
import org.poo.bank.commerciants.ClothesCommerciant;
import org.poo.bank.commerciants.FoodCommerciant;
import org.poo.bank.commerciants.TechCommerciant;
import org.poo.bank.users.users_strategy.UserStrategy;

import static org.poo.bank.commerciants.commerciant_strategies.NrTransactionsStrategy.*;

public final class SpendingThresholdStrategy implements CashBackStrategy {
    public static final double FIRST_THRESHOLD = 100;
    public static final double SECOND_THRESHOLD = 300;
    public static final double THIRD_THRESHOLD = 500;

    @Override
    public void cashBack(final UserStrategy ownerStrategy, final Account account,
                         final TechCommerciant commerciant, final double amount,
                         final double conversionRate) {
        double cashBackSum = 1 / conversionRate * ownerStrategy
                .calculateCashBack(amount * conversionRate, account);
        account.setSpendingThresholdAmount(account.getSpendingThresholdAmount()
                                           + amount * conversionRate);

        if (account.getBonuses().hasBonus(AccountBonuses.BonusType.TECH)) {
            cashBackSum += TECH_CASHBACK * amount;
            account.getBonuses().setBonusUsed(AccountBonuses.BonusType.TECH);
        }

        account.setBalance(account.getBalance() + cashBackSum);
    }

    @Override
    public void cashBack(final UserStrategy ownerStrategy, final Account account,
                         final FoodCommerciant commerciant, final double amount,
                         final double conversionRate) {
            double cashBackSum = 1 / conversionRate * ownerStrategy
                    .calculateCashBack(amount * conversionRate, account);
            account.setSpendingThresholdAmount(account.getSpendingThresholdAmount()
                                               + amount * conversionRate);

        if (account.getBonuses().hasBonus(AccountBonuses.BonusType.FOOD)) {
            cashBackSum += FOOD_CASHBACK * amount;
            account.getBonuses().setBonusUsed(AccountBonuses.BonusType.FOOD);
        }

        account.setBalance(account.getBalance() + cashBackSum);
    }

    @Override
    public void cashBack(final UserStrategy ownerStrategy, final Account account,
                         final ClothesCommerciant commerciant, final double amount,
                         final double conversionRate) {
        double cashBackSum = 1 / conversionRate * ownerStrategy
                .calculateCashBack(amount * conversionRate, account);
        account.setSpendingThresholdAmount(account.getSpendingThresholdAmount()
                                           + amount * conversionRate);

        if (account.getBonuses().hasBonus(AccountBonuses.BonusType.CLOTHES)) {
            cashBackSum += CLOTHES_CASHBACK * amount;
            account.getBonuses().setBonusUsed(AccountBonuses.BonusType.CLOTHES);
        }

        account.setBalance(account.getBalance() + cashBackSum);
    }

}
