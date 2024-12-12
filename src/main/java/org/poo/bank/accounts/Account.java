package org.poo.bank.accounts;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.poo.bank.accounts.cards.Card;
import org.poo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class Account {
    private final String ownerEmail;
    private final String currency;
    private final String accountType;
    private final String iban;
    private double balance;
    private final List<Card> cards;

    public Account(String ownerEmail, String currency, String accountType) {
        this.ownerEmail = ownerEmail;
        this.currency = currency;
        this.accountType = accountType;
        iban = Utils.generateIBAN();
        cards = new ArrayList<>();
        balance = 0;
    }

    public ObjectNode accountToObjectNode() {
        ObjectNode accountNode = Utils.mapper.createObjectNode();
        accountNode.put("IBAN", iban);
        accountNode.put("balance", balance);
        accountNode.put("currency", currency);
        accountNode.put("type", accountType);
        accountNode.set("cards", writeCards());

        return accountNode;
    }


    private ArrayNode writeCards() {
        ArrayNode cardsNode = Utils.mapper.createArrayNode();
        for (Card card : cards) {
            cardsNode.add(card.cardToObjectNode());
        }

        return cardsNode;
    }


}
