package org.poo.bank.transactions.transaction_factory;

import org.poo.bank.transactions.Transaction;

/**
 * Interface implemented by specific Transaction Factories
 */
public interface TransactionFactory {
    /**
     * Method used to create a transaction
     * @return The transaction
     */
    Transaction createTransaction();
}
