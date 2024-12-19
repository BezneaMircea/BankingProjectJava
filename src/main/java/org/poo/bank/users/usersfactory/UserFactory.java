package org.poo.bank.users.usersfactory;

import org.poo.bank.users.User;

/**
 * Interface implemented by specific user factories
 */
public interface UserFactory {
    /**
     * Method used to create a user
     * @return the user
     */
    User createUser();
}
