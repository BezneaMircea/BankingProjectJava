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
    public static final String ACTIVE;
    public static final String FROZEN;
    public static final String NOT_FOUND;
    public static final String DESTROYED;
    public static final String LIMIT_REACHED;
    public static final String CARD_CREATED;
    public static final String IS_FROZEN;
    public static final String CARD_PAYMENT;

    static {
        ACTIVE = "active";
        FROZEN = "frozen";
        NOT_FOUND = "Card not found";
        DESTROYED = "The card has been destroyed";
        LIMIT_REACHED = "You have reached the minimum amount of funds, the card will be frozen";
        CARD_CREATED = "New card created";
        IS_FROZEN = "The card is frozen";
        CARD_PAYMENT = "Card payment";
    }

    public enum Type {
        ONE_TIME("oneTimeCard"),
        STANDARD("standardCard");

        private final String value;

        Type(final String value) {
            this.value = value;
        }

        /**
         * returns the associated Type of input string;
         *
         * @param input the input string
         * @return the associated Type
         */
        public static Type fromString(final String input) {
            for (Type type : Type.values()) {
                if (type.value.equalsIgnoreCase(input)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Not a valid card type: " + input);
        }
    }

    private final String cardNumber;
    private String status;
    private final Account account;
    private final Type cardType;

    /**
     * Constructor for the Card class
     *
     * @param status  the status of the card (should be active)
     * @param account the account to which the card is linked
     */
    public Card(final String status, final Account account, final Type cardType) {
        cardNumber = Utils.generateCardNumber();
        this.account = account;
        this.status = status;
        this.cardType = cardType;
    }

    /**
     * Method used to write a card as an objectNode
     *
     * @return the objectNode corresponding to the card
     */
    public ObjectNode cardToObjectNode() {
        ObjectNode cardNode = Utils.MAPPER.createObjectNode();
        cardNode.put("cardNumber", cardNumber);
        cardNode.put("status", status);

        return cardNode;
    }

    /**
     * Method used to pay with a card. This should generate transactions towards the account
     * and the owner of the account.
     *
     * @param bank        the bank where the card is linked
     * @param amount      amount to pay
     * @param timestamp   timestamp of the payment
     * @param commerciant commerciant to pay
     */
    public abstract void pay(Bank bank, double amount, int timestamp, String commerciant);
}
