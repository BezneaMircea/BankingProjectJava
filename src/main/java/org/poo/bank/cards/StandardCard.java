package org.poo.bank.cards;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.commerciants.Commerciant;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.transactions.TransactionInput;
import org.poo.bank.users.User;
import org.poo.utils.Utils;

/**
 * Class used to represent a StandardCard
 */
public final class StandardCard extends Card {
    /**
     * Constructor for the StandardCard, just calls the superclass constructor with
     * the given params
     *
     * @param status  status of the card
     * @param account account to which the card is linked
     */
    public StandardCard(final String status, final Account account, final Type cardType) {
        super(status, account, cardType);
    }

    @Override
    public void pay(final Bank bank, final double amount,
                    final int timestamp, final Commerciant commerciant) {
        String error = null;

        if (getStatus().equals(FROZEN)) {
            error = Card.IS_FROZEN;
        }

        Account associatedAccount = getAccount();
        User owner = bank.getUser(associatedAccount.getOwnerEmail());
        double totalAmount = owner.getStrategy().calculateSumWithComision(amount);

        if (associatedAccount.getBalance() < totalAmount && error == null) {
            error = Account.INSUFFICIENT_FUNDS;
        }

        TransactionInput payOnline = new TransactionInput.Builder(Transaction.Type.PAY_ONLINE,
                timestamp, Card.CARD_PAYMENT)
                .amount(Utils.approximateToFourthDecimal(amount))
                .commerciant(commerciant)
                .error(error)
                .build();

        bank.generateTransaction(payOnline).addTransaction(owner, associatedAccount);

        if (error == null) {
            associatedAccount.setBalance(associatedAccount.getBalance() - totalAmount);
            double conversionRate = bank.getRate(associatedAccount.getCurrency(), Commerciant.MAIN_CURRENCY);
            commerciant.acceptCashback(owner.getStrategy(), associatedAccount, amount, conversionRate);
        }

    }
}
