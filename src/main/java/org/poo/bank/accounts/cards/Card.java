package org.poo.bank.accounts.cards;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.poo.bank.Bank;
import org.poo.bank.accounts.Account;
import org.poo.utils.Utils;

/**
 * Class used to represent a banking card
 */
@Getter
@Setter
public abstract class Card {
    public final static String ACTIVE = "active";
    public final static String FROZEN = "frozen";
    public final static String NOT_FOUND = "Card not found";
    public final static String DESTROYED = "The card has been destroyed";
    public static String LIMIT_REACHED = "You have reached the minimum amount of funds, the card will be frozen";
    public static String CARD_CREATED = "New card created";
    public static String IS_FROZEN = "The card is frozen";

    private final String cardNumber;
    private String status;
    private final Account account;
    private final Type accountType;


    public enum Type {
        ONE_TIME("oneTimeCard"),
        STANDARD("standardCard");

        private final String value;

        Type(String value) {
            this.value = value;
        }

        public static Type fromString(String input) {
            for (Type type : Type.values()) {
                if (type.value.equalsIgnoreCase(input)) {
                    return type;
                }
            } throw new IllegalArgumentException("Not a valid card type: " + input);
        }
    }

    /**
     * Constructor for the Card class
     * @param status the status of the card (should be active)
     * @param account the account to which the card is linked
     */
    public Card(String status, Account account, Type accountType) {
        cardNumber = Utils.generateCardNumber();
        this.account = account;
        this.status = status;
        this.accountType = accountType;
    }

    /**
     * Method used to write a card as an objectNode
     * @return the objectNode corresponding to the card
     */
    public ObjectNode cardToObjectNode() {
        ObjectNode cardNode = Utils.mapper.createObjectNode();
        cardNode.put("cardNumber", cardNumber);
        cardNode.put("status", status);

        return cardNode;
    }

    /**
     * Method used to pay with a card. This should generate transactions towards the account
     * and the owner of the account.
     * @param bank the bank where the card is linked
     * @param amount amount to pay
     * @param timestamp timestamp of the payment
     * @param commerciant commerciant to pay
     */
    public abstract void pay(final Bank bank, final double amount, final int timestamp, final String commerciant);
}
