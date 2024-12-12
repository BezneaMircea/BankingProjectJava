package org.poo.bank.accounts.cards;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import org.poo.bank.accounts.Account;
import org.poo.utils.Utils;

@Getter
public abstract class Card {
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
}
