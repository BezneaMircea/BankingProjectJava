package org.poo.bank.accounts.cards;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.utils.Utils;

@Getter
@Setter
public abstract class Card {
    public final static String ACTIVE = "active";
    public final static String FROZEN = "frozen";
    public final static String WARNING = "will be frozen";
    public final static String INSUFFICIENT_FUNDS = "Insufficient funds";

    private final String cardNumber;
    private String status;
    private final Account account;

    public Card(String status, Account account) {
        cardNumber = Utils.generateCardNumber();
        this.account = account;
        this.status = status;
    }

    public ObjectNode cardToObjectNode() {
        ObjectNode cardNode = Utils.mapper.createObjectNode();
        cardNode.put("cardNumber", cardNumber);
        cardNode.put("status", status);

        return cardNode;
    }

    public abstract String pay(final Bank bank, final double amount);
}
