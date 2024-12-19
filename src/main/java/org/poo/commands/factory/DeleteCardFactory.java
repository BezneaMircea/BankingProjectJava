package org.poo.commands.factory;

import org.poo.bank.Bank;
import org.poo.commands.Command;
import org.poo.commands.DeleteCard;
import org.poo.fileio.CommandInput;

public class DeleteCardFactory implements CommandFactory {
    private final Bank bank;
    private final String command;
    private final String cardNumber;
    private final int timestamp;

    public DeleteCardFactory(final Bank bank, final CommandInput input) {
        this.bank = bank;
        command = input.getCommand();
        cardNumber = input.getCardNumber();
        timestamp = input.getTimestamp();
    }

    @Override
    public Command createCommand() {
        return new DeleteCard(bank, command, cardNumber, timestamp);
    }
}
