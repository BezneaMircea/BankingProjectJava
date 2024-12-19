package org.poo.commands.command_factory;

import org.poo.bank.Bank;
import org.poo.commands.Command;
import org.poo.commands.PayOnline;
import org.poo.fileio.CommandInput;

public class PayOnlineFactory implements CommandFactory {
    private final Bank bank;
    private final String command;
    private final String cardNumber;
    private final double amount;
    private final String currency;
    private final int timestamp;
    private final String description;
    private final String commerciant;
    private final String email;

    public PayOnlineFactory(final Bank bank, final CommandInput input) {
        this.bank = bank;
        command = input.getCommand();
        cardNumber = input.getCardNumber();
        amount = input.getAmount();
        currency = input.getCurrency();
        timestamp = input.getTimestamp();
        description = input.getDescription();
        commerciant = input.getCommerciant();
        email = input.getEmail();
    }


    @Override
    public Command createCommand() {
        return new PayOnline(bank, command, cardNumber, amount, currency, timestamp, description, commerciant, email);
    }
}
