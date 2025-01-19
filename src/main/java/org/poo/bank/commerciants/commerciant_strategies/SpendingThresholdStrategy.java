package org.poo.bank.commerciants.commerciant_strategies;

import org.poo.bank.accounts.Account;
import org.poo.bank.commerciants.ClothesCommerciant;
import org.poo.bank.commerciants.FoodCommerciant;
import org.poo.bank.commerciants.TechCommerciant;

public class SpendingThresholdStrategy implements CashBackStrategy {


    @Override
    public void payCommerciant(Account account, TechCommerciant commerciant, double amount) {

    }

    @Override
    public void payCommerciant(Account account, FoodCommerciant commerciant, double amount) {

    }

    @Override
    public void payCommerciant(Account account, ClothesCommerciant commerciant, double amount) {

    }
}
