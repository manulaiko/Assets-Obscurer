package com.manulaiko.tabitha.net;

/**
 * Handler class.
 * ==============
 *
 * Base class for command handlers.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public abstract class Handler<T extends Command> {
    /**
     * Received command.
     */
    private T _command;

    /**
     * Connection that received the command.
     */
    private Connection _connection;

    /**
     * Constructor.
     *
     * @param command    Received command.
     * @param connection Connection that received the command.
     */
    public Handler(T command, Connection connection) {
        this.command(command);
        this.connection(connection);
    }

    /**
     * Handles the command.
     */
    public abstract void handle();

    /**
     * Sets `command`.
     *
     * @param command New value for `command`.
     */
    public void command(T command) {
        this._command = command;
    }

    /**
     * Returns `command`.
     *
     * @return Current value of `command`.
     */
    public T command() {
        return this._command;
    }

    /**
     * Sets `connection`.
     *
     * @param connection New value for `connection`.
     */
    public void connection(Connection connection) {
        this._connection = connection;
    }

    /**
     * Returns `connection`.
     *
     * @return Current value of `connection`.
     */
    public Connection connection() {
        return this._connection;
    }
}
