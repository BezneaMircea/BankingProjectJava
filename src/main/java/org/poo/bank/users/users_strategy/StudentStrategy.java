package org.poo.bank.users.users_strategy;

import org.poo.bank.accounts.Account;
import org.poo.bank.commerciants.commerciant_strategies.SpendingThresholdStrategy;
import org.poo.bank.users.User;

public final class StudentStrategy implements UserStrategy {
    public static final double STUDENT_COMMISSION = 0;
    public static final double FIRST_THRESHOLD_COMMISSION = 0.001;
    public static final double SECOND_THRESHOLD_COMMISSION = 0.002;
    public static final double THIRD_THRESHOLD_COMMISSION = 0.0025;


    @Override
    public double calculateCashBack(final double sum, final Account account) {
        Double amountSpent = account.getSpendingThresholdAmount();

        if (amountSpent == null) {
            throw new IllegalArgumentException("You can t have cashBack on this account");
        }

        double commision = 0;

        if (amountSpent + sum >= SpendingThresholdStrategy.FIRST_THRESHOLD) {
            commision = FIRST_THRESHOLD_COMMISSION;
        }

        if (amountSpent + sum >= SpendingThresholdStrategy.SECOND_THRESHOLD) {
            commision = SECOND_THRESHOLD_COMMISSION;
        }

        if (amountSpent + sum >= SpendingThresholdStrategy.THIRD_THRESHOLD) {
            commision = THIRD_THRESHOLD_COMMISSION;
        }

        return commision * sum;
    }

    @Override
    public double calculateSumWithCommission(final double sum, final double conversionRate) {
        return sum + STUDENT_COMMISSION * sum;
    }

    @Override
    public String accept(final StrategyVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public Type getStrategy() {
        return Type.STUDENT;
    }

    @Override
    public String visit(final GoldStrategy strategy) {
        return User.CANT_DOWNGRADE;
    }

    @Override
    public String visit(final SilverStrategy strategy) {
        return User.CANT_DOWNGRADE;
    }

    @Override
    public String visit(final StandardStrategy strategy) {
        return User.CANT_DOWNGRADE;
    }

    @Override
    public String visit(final StudentStrategy strategy) {
        return String.format(User.HAS_PLAN, strategy.getStrategy().getString());
    }
}
