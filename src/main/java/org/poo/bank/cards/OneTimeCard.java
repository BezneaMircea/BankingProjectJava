package org.poo.bank.cards;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.commerciants.Commerciant;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.transactions.TransactionInput;
import org.poo.bank.users.User;
import org.poo.utils.Utils;

/**
 * Class used to represent a one time card
 */
public final class OneTimeCard extends Card {
    /**
     * Constructor for the OneTimeCard, just calls the superclass constructor with
     * the given params
     *
     * @param status  status of the card
     * @param account account to which the card is linked
     */
    public OneTimeCard(final String status, final Account account, final Type cardType) {
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
        double conversionRate = bank.getRate(associatedAccount.getCurrency(), Commerciant.MAIN_CURRENCY);
        double totalAmount = owner.getStrategy().calculateSumWithComision(amount, conversionRate);

        if (associatedAccount.getBalance() < totalAmount && error == null) {
            error = Account.INSUFFICIENT_FUNDS;
        }

        TransactionInput payOnline = new TransactionInput.Builder(Transaction.Type.PAY_ONLINE,
                timestamp, Card.CARD_PAYMENT)
                .amount(Utils.approximateToFourthDecimal(amount))
                .commerciant(commerciant)
                .error(error)
                .build();

        if (error != null) {
            bank.generateTransaction(payOnline).addTransaction(owner, associatedAccount);
            return;
        }

        associatedAccount.setBalance(associatedAccount.getBalance() - totalAmount);
        commerciant.acceptCashback(owner.getStrategy(), associatedAccount, amount, conversionRate);

        associatedAccount.removeCard(this);
        bank.getCardNrToCard().remove(getCardNumber());

        CardInput newCardInput = new CardInput(getStatus(), getAccount(), Type.ONE_TIME);
        Card cardToAdd = bank.createCard(newCardInput);
        associatedAccount.addCard(cardToAdd);
        bank.getCardNrToCard().put(cardToAdd.getCardNumber(), cardToAdd);


        TransactionInput deleteCard = new TransactionInput.Builder(Transaction.Type.DELETE_CARD,
                timestamp, Card.DESTROYED)
                .card(getCardNumber())
                .cardHolder(getAccount().getOwnerEmail())
                .account(getAccount().getIban())
                .build();

        TransactionInput createCard = new TransactionInput.Builder(Transaction.Type.CREATE_CARD,
                timestamp, Card.CARD_CREATED)
                .card(cardToAdd.getCardNumber())
                .cardHolder(getAccount().getOwnerEmail())
                .account(getAccount().getIban())
                .build();


        bank.generateTransaction(payOnline).addTransaction(owner, associatedAccount);
        bank.generateTransaction(deleteCard).addTransaction(owner, associatedAccount);
        bank.generateTransaction(createCard).addTransaction(owner, associatedAccount);
    }
}
