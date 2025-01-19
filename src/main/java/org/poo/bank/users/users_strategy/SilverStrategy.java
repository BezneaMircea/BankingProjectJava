package org.poo.bank.users.users_strategy;

import org.poo.bank.accounts.Account;
import org.poo.bank.commerciants.commerciant_strategies.SpendingThresholdStrategy;
import org.poo.bank.users.User;

public final class SilverStrategy implements UserStrategy {
    public static final double SILVER_COMMISSION = 0.001;
    public static final double SILVER_COMMISSION_THRESHOLD = 500;
    public static final String STUDENT_TO_SILVER = "100";
    public static final String STANDARD_TO_SILVER = "100";


    public static final double FIRST_THRESHOLD_COMMISION = 0.003;
    public static final double SECOND_THRESHOLD_COMMISION = 0.004;
    public static final double THIRD_THRESHOLD_COMMISION = 0.005;


    @Override
    public double calculateCashBack(double sum, Account account) {
        Double amountSpent = account.getSpendingThresholdAmount();

        if (amountSpent == null)
            throw new IllegalArgumentException("You can t have cashBack on this account");

        double commision = 0;

        if (amountSpent + sum >= SpendingThresholdStrategy.FIRST_THRESHOLD)
            commision = FIRST_THRESHOLD_COMMISION;

        if (amountSpent + sum>= SpendingThresholdStrategy.SECOND_THRESHOLD)
            commision = SECOND_THRESHOLD_COMMISION;

        if (amountSpent + sum >= SpendingThresholdStrategy.THIRD_THRESHOLD)
            commision = THIRD_THRESHOLD_COMMISION;

        return commision * sum;
    }

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
        return User.CANT_DOWNGRADE;
    }

    @Override
    public String visit(SilverStrategy strategy) {
        return String.format(User.HAS_PLAN, strategy.getStrategy().getString());
    }

    @Override
    public String visit(StandardStrategy strategy) {
        return STANDARD_TO_SILVER;
    }

    @Override
    public String visit(StudentStrategy strategy) {
        return STUDENT_TO_SILVER;
    }
}
