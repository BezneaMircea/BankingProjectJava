package org.poo.bank.users.users_strategy;

import org.poo.bank.users.User;

public final class GoldStrategy implements UserStrategy {
    public static final double GOLD_STRATEGY_COMMISSION = 0;

    @Override
    public double calculateSumWithComision(double sum) {
        return sum + GOLD_STRATEGY_COMMISSION * sum;
    }

    @Override
    public String accept(StrategyVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public Type getStrategy() {
        return Type.GOLD;
    }


    @Override
    public String visit(GoldStrategy strategy) {
        return String.format(User.HAS_PLAN, strategy.getStrategy().getString());
    }

    @Override
    public String visit(SilverStrategy strategy) {
        return User.CANT_DOWNGRADE;
    }

    @Override
    public String visit(StandardStrategy strategy) {
        return User.CANT_DOWNGRADE;
    }

    @Override
    public String visit(StudentStrategy strategy) {
        return User.CANT_DOWNGRADE;
    }
}
