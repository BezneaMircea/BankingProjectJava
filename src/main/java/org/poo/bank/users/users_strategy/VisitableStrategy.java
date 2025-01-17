package org.poo.bank.users.users_strategy;

public interface VisitableStrategy {
    /**
     * @param visitor the strategy visitor
     * @return an error String or a String representing the sum
     * required to upgrade the strategy.
     */
    String accept(StrategyVisitor visitor);
}
