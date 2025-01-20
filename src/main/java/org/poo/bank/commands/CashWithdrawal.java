package org.poo.bank.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.cards.Card;
import org.poo.bank.commerciants.Commerciant;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.transactions.TransactionInput;
import org.poo.bank.users.User;

public final class CashWithdrawal implements Command, Transactionable {
    public static final String CURRENCY = "RON";
    public static final String CASH_WITHDRAWAL = "Cash withdrawal of %s";

    private final Bank bank;
    private final String command;
    private final String cardNumber;
    private final double amount;
    private final String email;
    private final String location;
    private final int timestamp;


    public CashWithdrawal(final Bank bank, final String command, final String cardNumber,
                          final double amount, final String email,
                          final String location, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.email = email;
        this.location = location;
        this.timestamp = timestamp;
    }


    @Override
    public void execute() {
        String error = null;
        User owner = null;
        Account account = null;
        Card card = bank.getCard(cardNumber);
        if (card == null) {
            error = Card.NOT_FOUND;
        } else if (card.getStatus().equals(Card.FROZEN)) {
            error = Card.IS_FROZEN;
        } else {
            owner = bank.getUser(email);
            if (owner == null) {
                error = User.NOT_FOUND;
            } else {
                account = card.getAccount();
                double convertedAmount = bank.getRate(CURRENCY, account.getCurrency()) * amount;
                double convertedAmountWithCommission = owner.getStrategy().calculateSumWithComision(convertedAmount,
                        bank.getRate(account.getCurrency(), Commerciant.MAIN_CURRENCY));

                if (account.getBalance() < convertedAmountWithCommission) {
                    error = Account.INSUFFICIENT_FUNDS;
                } else {
                    account.setBalance(account.getBalance() - convertedAmountWithCommission);
                }
            }
        }

        if (error != null) {
            bank.errorOccured(timestamp, command, error);
        } else {
            TransactionInput transactionInput = new TransactionInput.Builder(Transaction.Type.CASH_WIDRAWAL,
                    timestamp, String.format(CASH_WITHDRAWAL, amount))
                    .amount(amount)
                    .error(null)
                    .build();

            addTransaction(transactionInput, owner, account);
        }
    }

    @Override
    public void addTransaction(TransactionInput input, User user, Account account) {
        bank.generateTransaction(input).addTransaction(user, account);
    }
}
