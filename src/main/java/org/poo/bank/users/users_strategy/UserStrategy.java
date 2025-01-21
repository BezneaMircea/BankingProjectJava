package org.poo.bank.users.users_strategy;

import lombok.Getter;
import org.poo.bank.accounts.Account;


public interface UserStrategy extends StrategyVisitor, VisitableStrategy {

    /**
     * These values must be inserted in order of importance.
     * You can't downgrade your plan so we must consider
     * STANDARD < SILVER < GOLD.
     */
    @Getter
    enum Type {
        STUDENT("student"),
        STANDARD("standard"),
        SILVER("silver"),
        GOLD("gold");

        private final String value;

        Type(final String value) {
            this.value = value;
        }

        public String getString() {
            return value;
        }

        /**
         * returns the associated Type of input string;
         * @param input the input string
         * @return the associated Type
         */
        public static Type fromString(final String input) {
            for (Type type : Type.values()) {
                if (type.value.equalsIgnoreCase(input)) {
                    return type;
                }
            }

            return STANDARD;
        }
    }

    /**
     * Method used to calculate the cashBack for the payment with
     * the amount sum
     * @param sum the sum
     * @param account the account
     * @return the cashback value
     */
    double calculateCashBack(double sum, Account account);

    /**
     * Method used to calculate the sum with the added commission
     * @param sum the initial sum in the wanted currency
     * @param conversionRate the conversion rate to the MAIN_CURRENCY
     * @return the sum with the comision
     */
    double calculateSumWithCommission(double sum, double conversionRate);

    /**
     * Method used to get the strategy type
     * @return the strategy type
     */
    Type getStrategy();
}
