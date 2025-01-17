package org.poo.bank.users.users_strategy;

public interface StrategyVisitor {
    String visit(final GoldStrategy strategy);
    String visit(final SilverStrategy strategy);
    String visit(final StandardStrategy strategy);
    String visit(final StudentStrategy strategy);
}
