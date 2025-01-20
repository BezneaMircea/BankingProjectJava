package org.poo.bank.transactions;

import lombok.Getter;
import org.poo.bank.commerciants.Commerciant;

import java.util.List;

@Getter
public final class TransactionInput {
    private final Transaction.Type transactionType;
    private final int timestamp;
    private final String description;
    private final String currency;
    private final double amount;
    private final List<String> involvedAccounts;
    private final String error;
    private final String senderIBAN;
    private final String receiverIBAN;
    private final String transferType;
    private final String card;
    private final String cardHolder;
    private final String account;
    private final Commerciant commerciant;
    private final String newPlanType;
    private final String splitPaymentType;
    private final List<Double> amountForUsers;

    private TransactionInput(final Builder builder) {
        transactionType = builder.transactionType;
        timestamp = builder.timestamp;
        description = builder.description;
        currency = builder.currency;
        amount = builder.amount;
        involvedAccounts = builder.involvedAccounts;
        error = builder.error;
        senderIBAN = builder.senderIBAN;
        receiverIBAN = builder.receiverIBAN;
        transferType = builder.transferType;
        card = builder.card;
        cardHolder = builder.cardHolder;
        account = builder.account;
        commerciant = builder.commerciant;
        newPlanType = builder.newPlanType;
        splitPaymentType = builder.splitPaymentType;
        amountForUsers = builder.amountForUsers;
    }

    public static final class Builder {
        private final Transaction.Type transactionType;
        private final int timestamp;
        private final String description;

        private String currency = null;
        private double amount = 0.0d;
        private List<String> involvedAccounts = null;
        private String error = null;
        private String senderIBAN = null;
        private String receiverIBAN = null;
        private String transferType = null;
        private String card = null;
        private String cardHolder = null;
        private String account = null;
        private Commerciant commerciant = null;
        private String newPlanType = null;
        private String splitPaymentType = null;
        private List<Double> amountForUsers = null;

        public Builder(final Transaction.Type transactionType, final int timestamp,
                       final String description) {
            this.transactionType = transactionType;
            this.timestamp = timestamp;
            this.description = description;
        }

        /**
         * for coding style
         */
        public Builder currency(final String setCurrency) {
            currency = setCurrency;
            return this;
        }

        /**
         * for coding style
         */
        public Builder amount(final double setAmount) {
            amount = setAmount;
            return this;
        }

        /**
         * for coding style
         */
        public Builder involvedAccounts(final List<String> setInvolvedAccounts) {
            involvedAccounts = setInvolvedAccounts;
            return this;
        }

        /**
         * for coding style
         */
        public Builder error(final String setError) {
            error = setError;
            return this;
        }

        /**
         * for coding style
         */
        public Builder senderIBAN(final String setSenderIBAN) {
            senderIBAN = setSenderIBAN;
            return this;
        }

        /**
         * for coding style
         */
        public Builder receiverIBAN(final String setReceiverIBAN) {
            receiverIBAN = setReceiverIBAN;
            return this;
        }

        /**
         * for coding style
         */
        public Builder transferType(final String setTransferType) {
            transferType = setTransferType;
            return this;
        }

        /**
         * for coding style
         */
        public Builder card(final String setCard) {
            card = setCard;
            return this;
        }

        /**
         * for coding style
         */
        public Builder cardHolder(final String setCardHolder) {
            cardHolder = setCardHolder;
            return this;
        }

        /**
         * for coding style
         */
        public Builder account(final String setAccount) {
            account = setAccount;
            return this;
        }

        /**
         * for coding style
         */
        public Builder commerciant(final Commerciant setCommerciant) {
            commerciant = setCommerciant;
            return this;
        }

        /**
         * for coding style
         */
        public Builder newPlanType(final String setNewPlanType) {
            newPlanType = setNewPlanType;
            return this;
        }

        public Builder setSplitPaymentType(final String setSplitPaymentType) {
            splitPaymentType = setSplitPaymentType;
            return this;
        }

        public Builder setAmountForUsers(final List<Double> setAmountForUsers) {
            amountForUsers = setAmountForUsers;
            return this;
        }

        /**
         * for coding style
         */
        public TransactionInput build() {
            return new TransactionInput(this);
        }
    }


}
