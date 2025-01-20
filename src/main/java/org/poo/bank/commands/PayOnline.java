package org.poo.bank.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.cards.Card;
import org.poo.bank.commerciants.Commerciant;
import org.poo.bank.transactions.TransactionInput;
import org.poo.bank.users.User;


/**
 * Class used to represent the payOnline command
 */
public final class PayOnline implements Command, Transactionable {
    private final Bank bank;
    private final String command;
    private final String cardNumber;
    private final double amount;
    private final String currency;
    private final int timestamp;
    private final String description;
    private final String commerciantName;
    private final String email;

    /**
     * Constructor for the payOnline command
     *
     * @param bank        the receiver bank of the command
     * @param command     the command name
     * @param cardNumber  the nr of the card used for payment
     * @param amount      the amount to pay
     * @param currency    the currency in which the payment will be proceeded
     * @param timestamp   timestamp of the command
     * @param description description of the payment
     * @param commerciantName the commerciant to which the payment is done
     * @param email       email of the owner that has the account where the card is linked
     */
    public PayOnline(final Bank bank, final String command, final String cardNumber,
                     final double amount, final String currency, final int timestamp,
                     final String description, final String commerciantName, final String email) {
        this.bank = bank;
        this.command = command;
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.currency = currency;
        this.timestamp = timestamp;
        this.description = description;
        this.commerciantName = commerciantName;
        this.email = email;
    }

    @Override
    public void execute() {
        if (amount == 0)
            return;

        Card usedCard = bank.getCard(cardNumber);
        if (usedCard == null) {
            bank.errorOccured(timestamp, command, Card.NOT_FOUND);
            return;
        }

        Account associatedAccount = usedCard.getAccount();
        User cardOwner = bank.getUser(email);
        if (cardOwner == null || associatedAccount == null
                || !(cardOwner.hasAccount(associatedAccount)
                     && associatedAccount.hasCard(usedCard))) {
            return;
        }

        Commerciant commerciant = bank.getCommerciant(commerciantName);
        if (commerciant == null) {
            System.out.println("Commerciant " + commerciantName + " not found");
            return;
        }

        double exchangeRate = bank.getExchangeRates().getRate(currency,
                                                              associatedAccount.getCurrency());
        double totalSumToPay = amount * exchangeRate;

        /* The transactions are handled here, in pay method due to the fact that
         * different types of card generate different and multiple transactions */
        usedCard.pay(bank, totalSumToPay, timestamp, commerciant);
    }

    @Override
    public void addTransaction(final TransactionInput input, final User user,
                               final Account account) {
    }
}
