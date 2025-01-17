package org.poo.bank.users;

import org.poo.bank.users.users_strategy.UserStrategy;

/**
 * Class used to represent a BasicUser
 */
public final class BasicUser extends User {
    /**
     * Constructor for the BasicUser, just calls the superClass constructor
     *
     * @see User
     */
    public BasicUser(final String firstName, final String lastName, final String email,
                     final String birthDate, final String occupation, final UserStrategy strategy) {
        super(firstName, lastName, email, birthDate, occupation, strategy);
    }
}
