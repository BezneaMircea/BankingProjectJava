package org.poo.bank.accounts;

import lombok.Getter;

@Getter
public final class AccountInput {
    private final String ownerEmail;
    private final String currency;
    private final Account.Type accountType;
    private final double interestRate;

    private AccountInput(final Builder builder) {
        ownerEmail = builder.ownerEmail;
        currency = builder.currency;
        accountType = builder.accountType;
        interestRate = builder.interestRate;
    }

    public static final class Builder {
        private final String ownerEmail;
        private final String currency;
        private final Account.Type accountType;

        private double interestRate = 0.0d;

        public Builder(final String ownerEmail, final String currency,
                       final Account.Type accountType) {
            this.ownerEmail = ownerEmail;
            this.currency = currency;
            this.accountType = accountType;
        }

        /**
         * Method used to set interestRate withing the builder
         */
        public Builder interestRate(final double rate) {
            interestRate = rate;
            return this;
        }

        /**
         * Method used to build the AccountInput object
         */
        public AccountInput build() {
            return new AccountInput(this);
        }
    }

}
