package org.poo.bank.commands.command_factory;

import org.poo.bank.Bank;
import org.poo.bank.commands.Command;
import org.poo.bank.commands.CoustomSplitPayment;
import org.poo.bank.commands.SplitPayment;
import org.poo.bank.users.users_strategy.UserStrategy;
import org.poo.fileio.CommandInput;

import java.util.List;

public final class SplitPaymentFactory implements CommandFactory {
    public enum Type {
        CUSTOM("custom"),
        EQUAL("equal");

        private final String value;

        Type(final String value) {
            this.value = value;
        }

        public String getString() {
            return value;
        }

        /**
         * returns the associated Type of input string;
         * @param input the input string
         * @return the associated Type
         */
        public static Type fromString(final String input) {
            for (Type type : Type.values()) {
                if (type.value.equalsIgnoreCase(input)) {
                    return type;
                }
            }

            throw new IllegalArgumentException("Unknown payment type: " + input);
        }
    }


    private final Bank bank;
    private final String command;
    private final List<String> accountsForSplit;
    private final  List<Double> amountForUsers;
    private final int timestamp;
    private final String currency;
    private final Type splitPaymentType;
    private final double amount;

    public SplitPaymentFactory(final Bank bank, final CommandInput input) {
        this.bank = bank;
        command = input.getCommand();
        accountsForSplit = input.getAccounts();
        amountForUsers = input.getAmountForUsers();
        timestamp = input.getTimestamp();
        currency = input.getCurrency();
        splitPaymentType = Type.fromString(input.getSplitPaymentType());
        amount = input.getAmount();
    }

    @Override
    public Command createCommand() {
        Command commandToReturn = null;
        switch (splitPaymentType) {
            case EQUAL -> commandToReturn = new SplitPayment(bank, command, accountsForSplit,
                                                             timestamp, currency, amount, splitPaymentType);
            case CUSTOM -> commandToReturn = new CoustomSplitPayment(bank, command,
                                                                     accountsForSplit, timestamp,
                                                                     currency, amount, splitPaymentType,
                                                                     amountForUsers);

        }

        return commandToReturn;
    }
}
