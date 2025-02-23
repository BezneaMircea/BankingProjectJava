package org.poo.bank.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.commerciants.Commerciant;
import org.poo.bank.transactions.SendMoneyTransaction;
import org.poo.bank.transactions.Transaction;
import org.poo.bank.transactions.TransactionInput;
import org.poo.bank.users.User;
import org.poo.utils.Utils;

/**
 * Class used to represent the sendMoney command
 */
public final class SendMoney implements Command, Transactionable {
    private final Bank bank;
    private final String command;
    private final String account;
    private final double amount;
    private final String receiver;
    private final int timestamp;
    private final String description;

    /**
     * Constructor for the sendMoney command
     *
     * @param bank        the receiver bank of the command
     * @param command     the command name
     * @param account     IBAN of the sender account
     * @param amount      amount to transfer
     * @param receiver    IBAN of the receiver account
     * @param timestamp   timestamp of the command
     * @param description description of the transfer
     */
    public SendMoney(final Bank bank, final String command,
                     final String account, final double amount,
                     final String receiver, final int timestamp, final String description) {
        this.bank = bank;
        this.command = command;
        this.account = account;
        this.amount = amount;
        this.receiver = receiver;
        this.timestamp = timestamp;
        this.description = description;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        Account senderAccount = bank.getAccount(account);
        if (senderAccount == null) {
            return;
        }

        User senderUser = bank.getUser(senderAccount.getOwnerEmail());
        if (senderUser == null) {
            bank.errorOccured(timestamp, command, User.NOT_FOUND);
            return;
        }

        double conversionRate = bank.getRate(senderAccount.getCurrency(),
                                             Commerciant.MAIN_CURRENCY);
        if (errorCheck(senderAccount, senderUser, conversionRate)) {
            return;
        }

        Account receiverAccount = bank.getAccount(receiver);
        if (senderUser.hasAlias(receiver)) {
            receiverAccount = senderUser.getAccountFromAlias(receiver);
        }

        Commerciant commerciant = null;
        User receiverUser = null;
        if (receiverAccount == null) {
            commerciant = bank.getCommerciant(receiver);
            if (commerciant == null) {
                bank.errorOccured(timestamp, command, User.NOT_FOUND);
                return;
            }
        } else {
            receiverUser = bank.getUser(receiverAccount.getOwnerEmail());
        }


        if (commerciant != null) {
            senderAccount.transferToCommerciant(bank, amount, timestamp, commerciant);
            TransactionInput transactionSent = createSendInput(senderAccount, commerciant);
            addTransaction(transactionSent, senderUser, senderAccount);
            return;
        }


        double convertRate = bank.getRate(senderAccount.getCurrency(),
                                          receiverAccount.getCurrency());
        double receivedSum = amount * convertRate;
        double totalSumToPay = senderUser.getStrategy()
                .calculateSumWithCommission(amount, conversionRate);
        senderAccount.transfer(receiverAccount, totalSumToPay, receivedSum);

        TransactionInput transactionSent = createSendInput(senderAccount, null);
        TransactionInput transactionReceived = createReceiveInput(receiverAccount, receivedSum);

        addTransaction(transactionSent, senderUser, senderAccount);
        addTransaction(transactionReceived, receiverUser, receiverAccount);

        /// Increment nr of transactions for automatic upgrade if needed.
        senderUser.tryIncrementAutomaticUpgradePayments(conversionRate * amount);
        /// Try to upgrade the owner plan in case he has enough transactions
        senderUser.tryAutomaticUpgrade(bank, senderAccount, timestamp);
    }

    @Override
    public void
    addTransaction(final TransactionInput input, final User user, final Account acc) {
        bank.generateTransaction(input).addTransaction(user, acc);
    }

    private TransactionInput
    createSendInput(final Account senderAccount, final Commerciant commerciant) {
        return new TransactionInput.Builder(Transaction.Type.SEND_MONEY, timestamp, description)
                .senderIBAN(account)
                .receiverIBAN(receiver)
                .amount(Utils.approximateToFourthDecimal(amount))
                .currency(senderAccount.getCurrency())
                .commerciant(commerciant)
                .transferType(SendMoneyTransaction.SENT)
                .error(null)
                .build();
    }

    private TransactionInput
    createReceiveInput(final Account receiverAccount, final double receivedSum) {
        return new TransactionInput.Builder(Transaction.Type.SEND_MONEY, timestamp, description)
                .senderIBAN(account)
                .receiverIBAN(receiver)
                .amount(Utils.approximateToFourthDecimal(receivedSum))
                .currency(receiverAccount.getCurrency())
                .transferType(SendMoneyTransaction.RECEIVED)
                .error(null)
                .build();
    }

    /**
     * Checks for errors and adds a corresponding transaction
     * @param senderAccount the sending account
     * @param senderUser the sending user
     * @return true if an error occured, false otherwise
     */
    private boolean errorCheck(final Account senderAccount,
                               final User senderUser,
                               final double conversionRate) {
        double totalAmount = senderUser.getStrategy()
                .calculateSumWithCommission(amount, conversionRate);

        if (senderAccount.getBalance() < totalAmount) {
            TransactionInput input = new TransactionInput.Builder(Transaction.Type.SEND_MONEY,
                    timestamp, description)
                    .error(Account.INSUFFICIENT_FUNDS)
                    .build();

            addTransaction(input, senderUser, senderAccount);
            return true;
        }

        return false;
    }

}
