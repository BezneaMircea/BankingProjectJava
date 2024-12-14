package org.poo.commands;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.bank.accounts.cards.Card;
import org.poo.users.User;
import org.poo.utils.Utils;

public class DeleteAccount implements Command {
    private final Bank bank;
    private final String command;
    private final String account;
    private final String email;
    private final int timestamp;

    public DeleteAccount(final Bank bank, final String command,
                         final String account, final String email, final int timestamp) {
        this.bank = bank;
        this.command = command;
        this.account = account;
        this.email = email;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        Account accountToDelete = bank.getIbanToAccount().get(account);
        User ownerOfAccount = bank.getEmailToUser().get(email);


        ObjectNode deleteAccountNode = Utils.mapper.createObjectNode();
        deleteAccountNode.put("command", command);

        ObjectNode outputNode = Utils.mapper.createObjectNode();
        if (accountToDelete != null && accountToDelete.getBalance() == 0) {
            bank.getIbanToAccount().remove(account);

            for (Card card : accountToDelete.getCards())
                bank.getCardNrToCard().remove(card.getCardNumber());
            accountToDelete.getCards().clear();

            ownerOfAccount.getAccounts().remove(accountToDelete);

            outputNode.put("success", "Account deleted");
            outputNode.put("timestamp", timestamp);
        } else {
            outputNode.put("error", "Account couldn't be deleted - see org.poo.transactions for details");
            outputNode.put("timestamp", timestamp);
        }

        deleteAccountNode.set("output", outputNode);
        deleteAccountNode.put("timestamp", timestamp);

        bank.getOutput().add(deleteAccountNode);
    }
}
