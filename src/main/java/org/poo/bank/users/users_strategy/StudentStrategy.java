package org.poo.bank.users.users_strategy;

import org.poo.bank.accounts.Account;
import org.poo.bank.commerciants.commerciant_strategies.SpendingThresholdStrategy;
import org.poo.bank.users.User;

public final class StudentStrategy implements UserStrategy {
    public static final double STUDENT_COMMISSION = 0;
    public static final double FIRST_THRESHOLD_COMMISION = 0.001;
    public static final double SECOND_THRESHOLD_COMMISION = 0.002;
    public static final double THIRD_THRESHOLD_COMMISION = 0.0025;


    @Override
    public double calculateCashBack(double sum, Account account) {
        Double amountSpent = account.getSpendingThresholdAmount();

        System.out.println("amount spent: " + amountSpent);
        System.out.println("amount spent now: " + sum);

        if (amountSpent == null)
            throw new IllegalArgumentException("You can t have cashBack on this account");

        double commision = 0;

        if (amountSpent + sum >= SpendingThresholdStrategy.FIRST_THRESHOLD)
            commision = FIRST_THRESHOLD_COMMISION;

        if (amountSpent + sum >= SpendingThresholdStrategy.SECOND_THRESHOLD)
            commision = SECOND_THRESHOLD_COMMISION;

        if (amountSpent + sum >= SpendingThresholdStrategy.THIRD_THRESHOLD)
            commision = THIRD_THRESHOLD_COMMISION;

        System.out.println("commision is:" + commision);
        System.out.println("\n");
        return commision * sum;
    }

    @Override
    public double calculateSumWithComision(double sum, double conversionRate) {
        return sum + STUDENT_COMMISSION * sum;
    }

    @Override
    public String accept(StrategyVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public Type getStrategy() {
        return Type.STUDENT;
    }

    @Override
    public String visit(GoldStrategy strategy) {
        return User.CANT_DOWNGRADE;
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
        return String.format(User.HAS_PLAN, strategy.getStrategy().getString());
    }
}
