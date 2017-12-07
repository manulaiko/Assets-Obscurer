package com.manulaiko.tabitha.net;

/**
 * Connection interface.
 * =====================
 *
 * Represents a socket connection.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public interface Connection {
    /**
     * Sends a command through the socket.
     *
     * @param command Command to send.
     */
    void send(Command command);
}
