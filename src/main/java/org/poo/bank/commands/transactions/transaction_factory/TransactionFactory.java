package org.poo.bank.commands.transactions.transaction_factory;

import org.poo.bank.commands.transactions.Transaction;

public interface TransactionFactory {
    Transaction createTransaction();
}
