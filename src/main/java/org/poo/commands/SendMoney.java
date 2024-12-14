package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;

public class SendMoney implements Command {
    private final Bank bank;
    private final String command;
    private final String account;
    private final double amount;
    private final String receiver;
    private final int timestamp;
    private final String description;

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


    @Override
    public void execute() {
        Account senderAccount = bank.getIbanToAccount().get(account);
        Account receiverAccount = bank.getIbanToAccount().get(receiver);

        if (senderAccount == null || receiverAccount == null)
            return;

        if (senderAccount.getBalance() < amount) {
            /// TODO: add logic here
            return;
        }

        double convertRate = bank.getExchangeRates().getRate(senderAccount.getCurrency(), receiverAccount.getCurrency());
        double receivedSum = amount * convertRate;

        senderAccount.transfer(receiverAccount, amount, receivedSum);
    }
}
