package org.poo.bank.users.users_strategy;

import lombok.Getter;

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

    double calculateSumWithComision(double sum);
    Type getStrategy();
}
