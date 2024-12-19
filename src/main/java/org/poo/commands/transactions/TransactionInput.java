package org.poo.commands.transactions;

import lombok.Data;

import java.util.List;

@Data
public final class TransactionInput {
    private Transaction.Type transactionType;
    private int timestamp;
    private String description;
    private String currency;
    private double amount;
    private List<String> involvedAccounts;
    private String error;
    private String senderIBAN;
    private String receiverIBAN;
    private String transferType;
    private String card;
    private String cardHolder;
    private String account;
    private String commerciant;

    private TransactionInput(Builder builder) {
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
    }

    public static class Builder {
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
        private String commerciant = null;

        public Builder(final Transaction.Type transactionType, int timestamp, final String description) {
            this.transactionType = transactionType;
            this.timestamp = timestamp;
            this.description = description;
        }

        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder involvedAccounts(List<String> involvedAccounts) {
            this.involvedAccounts = involvedAccounts;
            return this;
        }

        public Builder error(String error) {
            this.error = error;
            return this;
        }

        public Builder senderIBAN(String senderIBAN) {
            this.senderIBAN = senderIBAN;
            return this;
        }

        public Builder receiverIBAN(String receiverIBAN) {
            this.receiverIBAN = receiverIBAN;
            return this;
        }

        public Builder transferType(String transferType) {
            this.transferType = transferType;
            return this;
        }

        public Builder card(String card) {
            this.card = card;
            return this;
        }

        public Builder cardHolder(String cardHolder) {
            this.cardHolder = cardHolder;
            return this;
        }

        public Builder account(String account) {
            this.account = account;
            return this;
        }

        public Builder commerciant(String commerciant) {
            this.commerciant = commerciant;
            return this;
        }



        public TransactionInput build() {
            return new TransactionInput(this);
        }
    }


}
