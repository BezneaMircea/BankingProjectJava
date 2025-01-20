package org.poo.bank.commands.command_factory;

import org.poo.bank.Bank;
import org.poo.bank.commands.CashWithdrawal;
import org.poo.bank.commands.Command;
import org.poo.fileio.CommandInput;

public class CashWithdrawalFactory implements CommandFactory {
    private final Bank bank;
    private final String command;
    private final String cardNumber;
    private final double amount;
    private final String email;
    private final String location;
    private final int timestamp;

    public CashWithdrawalFactory(final Bank bank, final CommandInput input) {
        this.bank = bank;
        command = input.getCommand();
        cardNumber = input.getCardNumber();
        amount = input.getAmount();
        email = input.getEmail();
        location = input.getLocation();
        timestamp = input.getTimestamp();
    }

    @Override
    public Command createCommand() {
        return new CashWithdrawal(bank, command, cardNumber, amount, email, location, timestamp);
    }
}
