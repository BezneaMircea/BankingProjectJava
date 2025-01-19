package org.poo.bank.commerciants.commerciant_strategies;

import org.poo.bank.accounts.Account;
import org.poo.bank.commerciants.AccountBonuses;
import org.poo.bank.commerciants.ClothesCommerciant;
import org.poo.bank.commerciants.FoodCommerciant;
import org.poo.bank.commerciants.TechCommerciant;

public class NrTransactionsStrategy implements CashBackStrategy{
    public static final double FOOD_CASHBACK = 0.02;
    public static final int FOOD_THRESHOLD = 2;

    public static final double CLOTHES_CASHBACK = 0.05;
    public static final int CLOTHES_THRESHOLD = 5;

    public static final double TECH_CASHBACK = 0.1;
    public static final int TECH_THRESHOLD = 10;

    @Override
    public void payCommerciant(Account account, TechCommerciant commerciant, double amount) {
        double sumToPay = amount;
        if (account.getBonuses().hasBonus(AccountBonuses.BonusType.TECH)) {
            sumToPay -= TECH_CASHBACK * amount;
            account.getBonuses().setBonusUsed(AccountBonuses.BonusType.TECH);
        }

        account.setBalance(account.getBalance() - sumToPay);
        commerciant.incrementAccountTransactions(account);

        tryGiveBonus(account, commerciant.getNrAccountTransactions(account));
    }

    @Override
    public void payCommerciant(Account account, FoodCommerciant commerciant, double amount) {
        double sumToPay = amount;
        if (account.getBonuses().hasBonus(AccountBonuses.BonusType.FOOD)) {
            sumToPay -= FOOD_CASHBACK * amount;
            account.getBonuses().setBonusUsed(AccountBonuses.BonusType.FOOD);
        }

        account.setBalance(account.getBalance() - sumToPay);
        commerciant.incrementAccountTransactions(account);

        tryGiveBonus(account, commerciant.getNrAccountTransactions(account));
    }

    @Override
    public void payCommerciant(Account account, ClothesCommerciant commerciant, double amount) {
        double sumToPay = amount;
        if (account.getBonuses().hasBonus(AccountBonuses.BonusType.CLOTHES)) {
            sumToPay -= CLOTHES_CASHBACK * amount;
            account.getBonuses().setBonusUsed(AccountBonuses.BonusType.CLOTHES);
        }

        account.setBalance(account.getBalance() - sumToPay);
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
