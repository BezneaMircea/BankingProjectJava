package org.poo.commands;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.utils.Utils;

/**
 * Class used to represend the addInterest command
 */
public class AddInterest implements Command {
    private final Bank bank;
    private final String command;
    private final String account;
    private final int timestamp;

    /**
     * Constructor for the addInterest command
     * @param bank the receiver bank of the command
     * @param command the command name
     * @param account the account that receives the interest
     * @param timestamp the timestamp of the command
     */
    public AddInterest(final Bank bank, final String command,
                       final String account, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.account = account;
        this.timestamp = timestamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        Account accountToAddInterest = bank.getIbanToAccount().get(account);
        if (accountToAddInterest == null)
            return;

        bank.errorOccured(timestamp, command, accountToAddInterest.addInterest());
    }


}
