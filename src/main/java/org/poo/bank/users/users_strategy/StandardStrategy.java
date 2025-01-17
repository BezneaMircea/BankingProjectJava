package org.poo.bank.users.users_strategy;

import org.poo.bank.users.User;

public final class StandardStrategy implements UserStrategy {
    public static final double STANDARD_COMMISSION = 0.2;
    public static final String STANDARD_TO_GOLD = "350";
    public static final String STANDARD_TO_SILVER = "100";

    @Override
    public double calculateSumWithComision(double sum) {
        return sum + STANDARD_COMMISSION * sum;
    }

    @Override
    public String accept(StrategyVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public Type getStrategy() {
        return Type.STANDARD;
    }

    @Override
    public String visit(GoldStrategy strategy) {
        return STANDARD_TO_GOLD;
    }

    @Override
    public String visit(SilverStrategy strategy) {
        return STANDARD_TO_SILVER;
    }

    @Override
    public String visit(StandardStrategy strategy) {
        return String.format(User.HAS_PLAN, strategy.getStrategy().getString());
    }

    @Override
    public String visit(StudentStrategy strategy) {
        return User.CANT_DOWNGRADE;
    }
}
