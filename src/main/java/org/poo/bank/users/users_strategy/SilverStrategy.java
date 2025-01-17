package org.poo.bank.users.users_strategy;

import org.poo.bank.users.User;

public final class SilverStrategy implements UserStrategy {
    public static final double SILVER_COMMISSION = 0.1;
    public static final double SILVER_COMMISSION_THRESHOLD = 500;
    public static final String SILVER_TO_GOLD = "250";

    @Override
    public double calculateSumWithComision(double sum) {
        return sum < SILVER_COMMISSION_THRESHOLD ? sum : sum + SILVER_COMMISSION * sum;
    }

    @Override
    public String accept(StrategyVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public Type getStrategy() {
        return Type.SILVER;
    }

    @Override
    public String visit(GoldStrategy strategy) {
        return SILVER_TO_GOLD;
    }

    @Override
    public String visit(SilverStrategy strategy) {
        return String.format(User.HAS_PLAN, strategy.getStrategy().getString());
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
