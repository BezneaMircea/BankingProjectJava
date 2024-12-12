package org.poo.commands;

import lombok.Getter;
import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.users.User;

@Getter
public class AddAccount implements Command {
    private final Bank bank;
    private final String command;
    private final String email;
    private final String currency;
    private final String accountType;
    private final int timestamp;
    private final double interestRate;

    public AddAccount(final Bank bank, final String command,
                      final String email, final String currency,
                      final String accountType, int timestamp, double interestRate) {
        this.bank = bank;
        this.command = command;
        this.email = email;
        this.currency = currency;
        this.accountType = accountType;
        this.timestamp = timestamp;
        this.interestRate = interestRate;
    }


    @Override
    public void execute() {
        User user = bank.getEmailToUser().get(email);
        if (user == null) {
            System.out.println("User not found");
        }

        Account accountToAdd = bank.createAccount(email, currency, accountType, interestRate);
        bank.getIbanToAccount().put(accountToAdd.getIban(), accountToAdd);

        user.addAccount(accountToAdd);
    }
}
