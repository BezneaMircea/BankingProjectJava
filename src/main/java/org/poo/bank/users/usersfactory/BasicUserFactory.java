package org.poo.bank.users.usersfactory;

import org.poo.bank.users.users_strategy.UserStrategy;
import org.poo.bank.users.users_strategy.UserStrategyFactory;
import org.poo.fileio.UserInput;
import org.poo.bank.users.BasicUser;
import org.poo.bank.users.User;

public final class BasicUserFactory implements UserFactory {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String birthDate;
    private final String occupation;
    private final UserStrategy strategy;

    public BasicUserFactory(final UserInput input) {
        firstName = input.getFirstName();
        lastName = input.getLastName();
        email = input.getEmail();
        birthDate = input.getBirthDate();
        occupation = input.getOccupation();
        strategy = UserStrategyFactory.createStrategy(UserStrategy.Type.fromString(occupation));
    }

    @Override
    public User createUser() {
        return new BasicUser(firstName, lastName, email, birthDate, occupation, strategy);
    }
}
