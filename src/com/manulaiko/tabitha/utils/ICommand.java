package com.manulaiko.tabitha.utils;

/**
 * Command interface.
 * ==================
 *
 * All commands from the Command Prompt must
 * implement this interface.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public interface ICommand {
    /**
     * Executes the command.
     *
     * @param command Command arguments.
     */
    void handle(String[] command);

    /**
     * Checks whether this command can execute `name` command.
     *
     * @param name Command name to check.
     *
     * @return Whether this command can execute `name`.
     */
    boolean canHandle(String name);

    /**
     * Returns command name.
     *
     * @return Command name.
     */
    String name();

    /**
     * Returns command description.
     *
     * @return Command description.
     */
    String description();
}
