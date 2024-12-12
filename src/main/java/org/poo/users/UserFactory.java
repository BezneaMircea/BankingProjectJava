package org.poo.users;

import org.poo.fileio.UserInput;

public class UserFactory {

    public static User createUser(UserInput userInput) {
        return new BasicUser(userInput);
    }
}
