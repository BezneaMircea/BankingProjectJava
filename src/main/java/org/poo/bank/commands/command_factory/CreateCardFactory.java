package org.poo.bank.commands.command_factory;

import org.poo.bank.Bank;
import org.poo.bank.accounts.cards.Card;
import org.poo.bank.commands.Command;
import org.poo.bank.commands.CreateCard;
import org.poo.fileio.CommandInput;

public class CreateCardFactory implements CommandFactory {
    private final Bank bank;
    private final String command;
    private final String account;
    private final String email;
    private final Card.Type cardType;
    private final int timestamp;

    public CreateCardFactory(final Bank bank, final CommandInput input) {
        this.bank = bank;
        command = input.getCommand();
        account = input.getAccount();
        email = input.getEmail();
        timestamp = input.getTimestamp();

        switch (command) {
            case "createCard" -> cardType = Card.Type.STANDARD;
            case "createOneTimeCard" -> cardType = Card.Type.ONE_TIME;
            default -> throw new IllegalArgumentException("Invalid card creation command");
        }
    }

    @Override
    public Command createCommand() {
        return new CreateCard(bank, command, account, email, cardType, timestamp);
    }
}
