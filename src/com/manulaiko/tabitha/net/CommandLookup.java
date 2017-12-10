package com.manulaiko.tabitha.net;

import com.manulaiko.tabitha.log.Console;
import com.manulaiko.tabitha.log.ConsoleManager;

import java.util.Map;

/**
 * Command lookup.
 * ===============
 *
 * Stores the available commands.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public abstract class CommandLookup {
    /**
     * Console instance.
     */
    public static final Console console = ConsoleManager.forClass(CommandLookup.class);

    /**
     * Available commands.
     */
    private Map<Short, Class<Command>> _commands = this._getCommands();

    /**
     * Finds and instances a command for given ID.
     *
     * @param id Command ID.
     *
     * @return Command for `id` or null.
     */
    public Command findCommandFor(short id) {
        Class<Command> commandClass = this.commands().get(id);
        if (commandClass == null) {
            CommandLookup.console.warning("Unknown command with ID " + id + "!");

            return null;
        }

        Command command;
        try {
            command = commandClass.newInstance();
        } catch (Exception e) {
            CommandLookup.console.exception("Couldn't instance command class!", e);

            return null;
        }

        command.id(id);

        return command;
    }

    /**
     * Builds and returns the commands map.
     *
     * @return Commands map.
     */
    protected abstract Map<Short, Class<Command>> _getCommands();

    /**
     * Sets `commands`.
     *
     * @param commands New value for `commands`.
     */
    public void commands(Map<Short, Class<Command>> commands) {
        this._commands = commands;
    }

    /**
     * Returns `commands`.
     *
     * @return Current value of `commands`.
     */
    public Map<Short, Class<Command>> commands() {
        return this._commands;
    }
}
