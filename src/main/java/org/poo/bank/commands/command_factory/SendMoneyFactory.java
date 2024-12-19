package org.poo.bank.commands.command_factory;

import org.poo.bank.Bank;
import org.poo.bank.commands.Command;
import org.poo.bank.commands.SendMoney;
import org.poo.fileio.CommandInput;

public class SendMoneyFactory implements CommandFactory {
    private final Bank bank;
    private final String command;
    private final String account;
    private final double amount;
    private final String receiver;
    private final int timestamp;
    private final String description;

    public SendMoneyFactory(final Bank bank, final CommandInput input) {
        this.bank = bank;
        command = input.getCommand();
        account = input.getAccount();
        amount = input.getAmount();
        receiver = input.getReceiver();
        timestamp = input.getTimestamp();
        description = input.getDescription();
    }

    @Override
    public Command createCommand() {
        return new SendMoney(bank, command, account, amount, receiver, timestamp, description);
    }
}
