package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.users.User;

public class SetAlias implements Command {
    private final Bank bank;
    private final String command;
    private final String email;
    private final String alias;
    private final String account;

    public SetAlias(final Bank bank, final String command,
                    final String email, final String alias,
                    final String account) {
        this.bank = bank;
        this.command = command;
        this.email = email;
        this.alias = alias;
        this.account = account;
    }

    @Override
    public void execute() {
        Account associatedAccount = bank.getIbanToAccount().get(account);
        User userToSetAlias = bank.getEmailToUser().get(email);

        if (associatedAccount == null || userToSetAlias == null)
            return;

        userToSetAlias.getAliases().put(alias, associatedAccount);
    }
}
