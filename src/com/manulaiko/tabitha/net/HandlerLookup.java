package com.manulaiko.tabitha.net;

import com.manulaiko.tabitha.log.Console;
import com.manulaiko.tabitha.log.ConsoleManager;

import java.util.Map;

/**
 * Handler lookup.
 * ===============
 *
 * Stores the handlers for their associated commands.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public abstract class HandlerLookup {
    /**
     * Console instance.
     */
    public static final Console console = ConsoleManager.forClass(HandlerLookup.class);

    /**
     * Handlers for the available commands.
     */
    private Map<Short, Class<Handler>> _handlers = this._getHandlers();

    /**
     * Finds and returns a handler for the given command.
     *
     * @param connection Connection that received the command.
     * @param command    Received command.
     *
     * @return Handler for `command`.
     */
    public Handler handler(Connection connection, Command command) {
        Class<Handler> handlerClass = this.handlers().get(command.id());

        if (handlerClass == null) {
            return null;
        }

        Handler handler = null;
        try {
            handler = handlerClass.getConstructor(Connection.class, Command.class)
                    .newInstance(connection, command);
        } catch (Exception e) {
            HandlerLookup.console.exception("Couldn't instance handler class!", e);
        }

        return handler;
    }

    /**
     * Builds and returns the handlers map.
     *
     * @return Handlers map.
     */
    public abstract Map<Short, Class<Handler>> _getHandlers();

    /**
     * Sets `handlers`.
     *
     * @param handlers New value for `handlers`.
     */
    public void handlers(Map<Short, Class<Handler>> handlers) {
        this._handlers = handlers;
    }

    /**
     * Returns `handlers`.
     *
     * @return Current value of `handlers`.
     */
    public Map<Short, Class<Handler>> handlers() {
        return this._handlers;
    }
}
