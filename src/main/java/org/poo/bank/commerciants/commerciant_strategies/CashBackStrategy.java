package org.poo.bank.commerciants.commerciant_strategies;

import org.poo.bank.accounts.Account;
import org.poo.bank.commerciants.ClothesCommerciant;
import org.poo.bank.commerciants.Commerciant;
import org.poo.bank.commerciants.FoodCommerciant;
import org.poo.bank.commerciants.TechCommerciant;

public interface CashBackStrategy {

    enum StrategyName {
        NR_TRANSACTIONS("nrOfTransactions"),
        SPENDING_THRESHOLD("spendingThreshold");

        private final String value;

        StrategyName(String value) {
            this.value = value;
        }

        public static StrategyName fromString(final String input) {
            for (StrategyName type : StrategyName.values()) {
                if (type.value.equalsIgnoreCase(input)) {
                    return type;
                }
            }

            throw new IllegalArgumentException("Not a valid strategy type: " + input);
        }
    }

    void payCommerciant(Account account, TechCommerciant commerciant, double amount);
    void payCommerciant(Account account, FoodCommerciant commerciant, double amount);
    void payCommerciant(Account account, ClothesCommerciant commerciant, double amount);
}
