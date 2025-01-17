package org.poo.bank.users.users_strategy;

public final class UserStrategyFactory {
    /**
     * This method returns a Strategy object of the wanted type strategyType
     * @param strategyType the enum representing the wanted strategy
     * @return null if an error occured, the wanted strategy object otherwise
     */
    public static UserStrategy
    createStrategy(final UserStrategy.Type strategyType) {
        UserStrategy wantedStrategy = null;

        switch (strategyType) {
            case GOLD -> wantedStrategy = new GoldStrategy();
            case SILVER -> wantedStrategy = new SilverStrategy();
            case STUDENT -> wantedStrategy = new StudentStrategy();
            case STANDARD -> wantedStrategy = new StandardStrategy();
        }

        return wantedStrategy;
    }
}
