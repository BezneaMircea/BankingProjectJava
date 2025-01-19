package org.poo.bank.commerciants;

import org.poo.bank.commerciants.commerciant_strategies.CashBackStrategy;

public final class TechCommerciant extends Commerciant {

    public TechCommerciant(final String name, final int id, final String account,
                           final Type type, final CashBackStrategy cashBackStrategy) {
        super(name, id, account, type, cashBackStrategy);
    }

}
