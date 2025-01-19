package org.poo.bank.commerciants.commerciant_strategies;


public final class CashBackStrategyFactory {
    public static CashBackStrategy
    createStrategy(final CashBackStrategy.StrategyName strategyType) {
        CashBackStrategy wantedStrategy = null;

        switch (strategyType) {
            case NR_TRANSACTIONS -> wantedStrategy = new NrTransactionsStrategy();
            case SPENDING_THRESHOLD -> wantedStrategy = new SpendingThresholdStrategy();
        }

        return wantedStrategy;
    }

}
