package org.poo.bank.accounts.factory;

import org.poo.bank.accounts.Account;

/**
 * Interface implemented by specific banking card factories
 */
public interface AccountFactory {
    /**
     * Method used to create a banking account
     * @return the created account
     */
    Account createAccount();
}
