package org.poo.bank.commerciants;

import org.poo.bank.accounts.Account;
import org.poo.bank.commerciants.commerciant_strategies.CashBackStrategy;
import org.poo.bank.users.users_strategy.UserStrategy;

public final class ClothesCommerciant extends Commerciant {

    public ClothesCommerciant(final String name, final int id, final String account,
                              final Type type, final CashBackStrategy cashBackStrategy) {
        super(name, id, account, type, cashBackStrategy);
    }

    @Override
    public void acceptCashback(final UserStrategy ownerStrategy, final Account account,
                               final double amount, final double conversionRate) {
        getCashBackStrategy().cashBack(ownerStrategy, account, this, amount, conversionRate);
    }
}
