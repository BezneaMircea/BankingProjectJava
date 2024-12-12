package org.poo.bank.accounts;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class EconomyAccount extends Account {
    private double interestRate;

    public EconomyAccount(final String ownerEmail, final String currency,
                          final String accountType, final double interestRate) {
        super(ownerEmail, currency, accountType);
        this.interestRate = interestRate;
    }


}
