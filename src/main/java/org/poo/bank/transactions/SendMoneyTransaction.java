package org.poo.bank.transactions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import org.poo.bank.accounts.Account;
import org.poo.bank.commerciants.Commerciant;
import org.poo.utils.Utils;

@Getter
public final class SendMoneyTransaction extends Transaction {
    public static final String SENT = "sent";
    public static final String RECEIVED = "received";

    private final String senderIBAN;
    private final String receiverIBAN;
    private final double amount;
    private final String currency;
    private final String transferType;
    private final String error;
    private final Commerciant commerciant;

    public SendMoneyTransaction(final Type transactionType, final int timestamp,
                                final String description, final String senderIBAN,
                                final String receiverIBAN, final double amount,
                                final String currency, final String transferType,
                                final String error, final Commerciant commerciant) {
        super(transactionType, timestamp, description);
        this.senderIBAN = senderIBAN;
        this.receiverIBAN = receiverIBAN;
        this.amount = amount;
        this.currency = currency;
        this.transferType = transferType;
        this.error = error;
        this.commerciant = commerciant;
    }

    @Override
    public ObjectNode toJson() {
        ObjectNode jsonNode = Utils.MAPPER.createObjectNode();

        if (error == null) {
            jsonNode.put("timestamp", getTimestamp());
            jsonNode.put("description", getDescription());
            jsonNode.put("senderIBAN", senderIBAN);
            jsonNode.put("receiverIBAN", receiverIBAN);
            jsonNode.put("amount", Utils.approximateToFourthDecimal(amount) + " " + currency);

            jsonNode.put("transferType", transferType);
        } else {
            jsonNode.put("timestamp", getTimestamp());
            jsonNode.put("description", error);
        }

        return jsonNode;
    }

    @Override
    public void addTransactionToAccount(final Account account) {
        account.addTransaction(this);
    }
}
