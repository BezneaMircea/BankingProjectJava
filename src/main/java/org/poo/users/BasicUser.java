package org.poo.users;

/**
 * Class used to represent a BasicUser
 */
public final class BasicUser extends User {
    /**
     * Constructor for the BasicUser, just calls the superClass constructor
     * @see User
     */
    public BasicUser(final String firstName, final String lastName, final String email) {
        super(firstName, lastName, email);
    }
}
