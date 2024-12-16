package org.poo.commands;

import org.poo.commands.transactions.Transaction;
import org.poo.commands.transactions.TransactionInput;

public interface Transactionable {
    Transaction generateTransaction(TransactionInput input);
}
