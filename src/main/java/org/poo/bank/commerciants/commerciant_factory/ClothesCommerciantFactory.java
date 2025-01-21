package org.poo.bank.commerciants.commerciant_factory;

import org.poo.bank.commerciants.ClothesCommerciant;
import org.poo.bank.commerciants.Commerciant;
import org.poo.bank.commerciants.commerciant_strategies.CashBackStrategy;
import org.poo.bank.commerciants.commerciant_strategies.CashBackStrategyFactory;
import org.poo.fileio.CommerciantInput;

public final class ClothesCommerciantFactory implements CommerciantFactory {
    private final String name;
    private final int id;
    private final String account;
    private final Commerciant.Type type;
    private final CashBackStrategy cashBackStrategy;

    public ClothesCommerciantFactory(final CommerciantInput input) {
        name = input.getCommerciant();
        id = input.getId();
        account = input.getAccount();
        type = Commerciant.Type.fromString(input.getType());
        cashBackStrategy = CashBackStrategyFactory
                .createStrategy(CashBackStrategy.StrategyName
                                .fromString(input.getCashbackStrategy()));
    }

    @Override
    public Commerciant createCommerciant() {
        return new ClothesCommerciant(name, id, account, type, cashBackStrategy);
    }
}
