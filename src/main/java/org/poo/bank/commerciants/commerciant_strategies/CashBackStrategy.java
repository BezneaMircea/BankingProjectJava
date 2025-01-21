package org.poo.bank.commerciants.commerciant_strategies;

import org.poo.bank.accounts.Account;
import org.poo.bank.commerciants.ClothesCommerciant;
import org.poo.bank.commerciants.FoodCommerciant;
import org.poo.bank.commerciants.TechCommerciant;
import org.poo.bank.users.users_strategy.UserStrategy;

public interface CashBackStrategy {

    enum StrategyName {
        NR_TRANSACTIONS("nrOfTransactions"),
        SPENDING_THRESHOLD("spendingThreshold");

        private final String value;

        StrategyName(final String value) {
            this.value = value;
        }

        /**
         * Method to get the corresponding of input string
         * @param input the input
         * @return the corresponding enum
         */
        public static StrategyName fromString(final String input) {
            for (StrategyName type : StrategyName.values()) {
                if (type.value.equalsIgnoreCase(input)) {
                    return type;
                }
            }

            throw new IllegalArgumentException("Not a valid strategy type: " + input);
        }
    }


    /**
     * Method used to compute the cashback
     * @param ownerStrategy the owner of the account strategy
     * @param account the account
     * @param commerciant the commerciant (Tech)
     * @param amount the amount of the sum
     * @param conversionRate the conversion rate in MAIN_CURRENCY
     */
    void cashBack(UserStrategy ownerStrategy, Account account,
                  TechCommerciant commerciant, double amount, double conversionRate);

    /**
     * Method used to compute the cashback
     * @param ownerStrategy the owner of the account strategy
     * @param account the account
     * @param commerciant the commerciant (Food)
     * @param amount the amount of the sum
     * @param conversionRate the conversion rate in MAIN_CURRENCY
     */
    void cashBack(UserStrategy ownerStrategy, Account account,
                  FoodCommerciant commerciant, double amount, double conversionRate);

    /**
     * Method used to compute the cashback
     * @param ownerStrategy the owner of the account strategy
     * @param account the account
     * @param commerciant the commerciant (Clothes)
     * @param amount the amount of the sum
     * @param conversionRate the conversion rate in MAIN_CURRENCY
     */
    void cashBack(UserStrategy ownerStrategy, Account account,
                  ClothesCommerciant commerciant, double amount, double conversionRate);
}
