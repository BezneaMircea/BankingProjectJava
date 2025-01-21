package org.poo.bank.commerciants.commerciant_strategies;


public final class CashBackStrategyFactory {

    /**
     * for coding style
     */
    private CashBackStrategyFactory() {
    }

    /**
     * Method used to create a cashBackStrategy object
     * @param strategyType the wanted type (NrTransactions/SpendingThreshold)
     * @return the strategy or null if type is undefined
     */
    public static CashBackStrategy
    createStrategy(final CashBackStrategy.StrategyName strategyType) {
        CashBackStrategy wantedStrategy = null;

        switch (strategyType) {
            case NR_TRANSACTIONS -> wantedStrategy = new NrTransactionsStrategy();
            case SPENDING_THRESHOLD -> wantedStrategy = new SpendingThresholdStrategy();

            default -> {
            }
        }

        return wantedStrategy;
    }

}
