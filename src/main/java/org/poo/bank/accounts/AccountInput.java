package org.poo.bank.accounts;

import lombok.Getter;

@Getter
public final class AccountInput {
    private final String ownerEmail;
    private final String currency;
    private final Account.Type accountType;
    private final double interestRate;
    private final double spendingLimit;
    private final double depositLimit;

    private AccountInput(final Builder builder) {
        ownerEmail = builder.ownerEmail;
        currency = builder.currency;
        accountType = builder.accountType;
        interestRate = builder.interestRate;
        spendingLimit = builder.spendingLimit;
        depositLimit = builder.depositLimit;
    }

    public static final class Builder {
        private final String ownerEmail;
        private final String currency;
        private final Account.Type accountType;

        private double depositLimit = 0.0d;
        private double spendingLimit = 0.0d;
        private double interestRate = 0.0d;

        public Builder(final String ownerEmail, final String currency,
                       final Account.Type accountType) {
            this.ownerEmail = ownerEmail;
            this.currency = currency;
            this.accountType = accountType;
        }

        /**
         * Method used to set interestRate within the builder
         */
        public Builder interestRate(final double rate) {
            interestRate = rate;
            return this;
        }

        /**
         * Method used to set spendingLimit within the builder
         */
        public Builder spendingLimit(final double limit) {
            spendingLimit = limit;
            return this;
        }

        /**
         * Method used to set depositLimit within the builder
         */
        public Builder depositLimit(final double limit) {
            depositLimit = limit;
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
