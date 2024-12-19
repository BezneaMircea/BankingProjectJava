package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.users.User;

/**
 * Class used to represent the setAlias command
 */
public final class SetAlias implements Command {
    private final Bank bank;
    private final String command;
    private final String email;
    private final String alias;
    private final String account;
    private final int timestamp;

    /**
     * Constructor for the setAlias command
     * @param bank the receiver bank of the command
     * @param command the command name
     * @param email email of the user that creates an alias
     * @param alias the alias
     * @param account IBAN of the account that alias is associated with
     * @param timestamp timestamp of the command
     */
    public SetAlias(final Bank bank, final String command,
                    final String email, final String alias,
                    final String account, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.email = email;
        this.alias = alias;
        this.account = account;
        this.timestamp = timestamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        Account associatedAccount = bank.getAccount(account);
        User userToSetAlias = bank.getUser(email);

        if (associatedAccount == null || userToSetAlias == null)
            return;

        userToSetAlias.addAlias(associatedAccount, alias);
    }
}
