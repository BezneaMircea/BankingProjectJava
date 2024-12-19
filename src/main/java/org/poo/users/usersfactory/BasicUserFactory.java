package org.poo.users.usersfactory;

import org.poo.fileio.UserInput;
import org.poo.users.BasicUser;
import org.poo.users.User;

public class BasicUserFactory implements UserFactory {
    private final String firstName;
    private final String lastName;
    private final String email;

    public BasicUserFactory(UserInput input) {
        firstName = input.getFirstName();
        lastName = input.getLastName();
        email = input.getEmail();
    }


    @Override
    public User createUser() {
        return new BasicUser(firstName, lastName, email);
    }
}
