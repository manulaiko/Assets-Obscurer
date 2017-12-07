package com.manulaiko.tabitha.exceptions.database;

/**
 * Connection failed exception
 *
 * Occurs when couldn't connect to Database
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class ConnectionFailed extends Exception {

    /**
     * Constructor
     */
    public ConnectionFailed() {
        super("Couldn't connect to the MySQL server");
    }
}
