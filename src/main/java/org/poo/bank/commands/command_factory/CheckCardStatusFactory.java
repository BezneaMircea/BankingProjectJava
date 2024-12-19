package org.poo.bank.commands.command_factory;

import org.poo.bank.Bank;
import org.poo.bank.commands.CheckCardStatus;
import org.poo.bank.commands.Command;
import org.poo.fileio.CommandInput;

public class CheckCardStatusFactory implements CommandFactory {
    private final Bank bank;
    private final String command;
    private final String cardNumber;
    private final int timestamp;

    public CheckCardStatusFactory(final Bank bank, final CommandInput input) {
        this.bank = bank;
        this.command = input.getCommand();
        this.cardNumber = input.getCardNumber();
        this.timestamp = input.getTimestamp();
    }

    @Override
    public Command createCommand() {
        return new CheckCardStatus(bank, command, cardNumber, timestamp);
    }
}
