package org.poo.bank.accounts;

import lombok.Getter;

@Getter
public final class AccountInput {
    private final String ownerEmail;
    private final String currency;
    private final Account.Type accountType;
    private final double interestRate;

    private AccountInput(Builder builder) {
        ownerEmail = builder.ownerEmail;
        currency = builder.currency;
        accountType = builder.accountType;
        interestRate = builder.interestRate;
    }

    public static class Builder {
        private final String ownerEmail;
        private final String currency;
        private final Account.Type accountType;

        private double interestRate = 0.0d;

        public Builder(String ownerEmail, String currency, Account.Type accountType) {
            this.ownerEmail = ownerEmail;
            this.currency = currency;
            this.accountType = accountType;
        }

        public Builder interestRate(double interestRate) {
            this.interestRate = interestRate;
            return this;
        }

        public AccountInput build() {
            return new AccountInput(this);
        }
    }

}
