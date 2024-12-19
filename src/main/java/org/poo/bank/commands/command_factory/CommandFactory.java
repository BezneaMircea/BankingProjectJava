package org.poo.bank.commands.command_factory;

import org.poo.bank.commands.Command;

/**
 * Interface implemented by specific command factories
 */
public interface CommandFactory {
    /**
     * Method used to create a command
     */
    Command createCommand();
}
