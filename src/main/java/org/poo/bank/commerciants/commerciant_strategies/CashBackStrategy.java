package org.poo.bank.commerciants.commerciant_strategies;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.commerciants.ClothesCommerciant;
import org.poo.bank.commerciants.Commerciant;
import org.poo.bank.commerciants.FoodCommerciant;
import org.poo.bank.commerciants.TechCommerciant;
import org.poo.bank.users.users_strategy.UserStrategy;

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



    void cashBack(UserStrategy ownerStrategy, Account account,
                  TechCommerciant commerciant, double amount, double conversionRate);

    void cashBack(UserStrategy ownerStrategy, Account account,
                  FoodCommerciant commerciant, double amount, double conversionRate);

    void cashBack(UserStrategy ownerStrategy, Account account,
                  ClothesCommerciant commerciant, double amount, double conversionRate);
}
