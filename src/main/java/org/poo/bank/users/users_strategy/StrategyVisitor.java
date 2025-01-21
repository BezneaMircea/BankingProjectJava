package org.poo.bank.users.users_strategy;

public interface StrategyVisitor {
    /**
     * Method used to get visited
     * @param strategy the strategy that is visiting (Gold)
     * @return an output String
     */
    String visit(GoldStrategy strategy);

    /**
     * Method used to get visited
     * @param strategy the strategy that is visiting (Silver)
     * @return an output String
     */
    String visit(SilverStrategy strategy);

    /**
     * Method used to get visited
     * @param strategy the strategy that is visiting (Standard)
     * @return an output String
     */
    String visit(StandardStrategy strategy);

    /**
     * Method used to get visited
     * @param strategy the strategy that is visiting (Student)
     * @return an output String
     */
    String visit(StudentStrategy strategy);
}
