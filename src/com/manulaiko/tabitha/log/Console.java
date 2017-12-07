package com.manulaiko.tabitha.log;

/**
 * Console interface.
 * ==================
 *
 * Interface for all consoles.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public interface Console {
    ///////////////////////////////
    // Start Constant definition //
    ///////////////////////////////
    String LINE_EQ = "======================================================";

    String LINE_MINUS = "------------------------------------------------------";

    String ANSI_RESET = "\u001B[0m";

    String ANSI_BLACK = "\u001B[30m";

    String ANSI_RED = "\u001B[31m";

    String ANSI_GREEN = "\u001B[32m";

    String ANSI_YELLOW = "\u001B[33m";

    String ANSI_BLUE = "\u001B[34m";

    String ANSI_PURPLE = "\u001B[35m";

    String ANSI_CYAN = "\u001B[36m";

    String ANSI_WHITE = "\u001B[37m";

    String ANSI_BOLD = "\u001B[1m";

    String ANSI_BOLD_RESET = "\u001B[21m";

    String ANSI_BLINK = "\u001B[5m";
    /////////////////////////////
    // End Constant definition //
    /////////////////////////////

    /**
     * Log a SEVERE message.
     * <p>
     * If the logger is currently enabled for the SEVERE message
     * level then the given message is forwarded to all the
     * registered output Handler objects.
     * <p>
     *
     * @param msg The string message (or a key in the message catalog)
     */
    void severe(String msg);

    /**
     * Log a WARNING message.
     * <p>
     * If the logger is currently enabled for the WARNING message
     * level then the given message is forwarded to all the
     * registered output Handler objects.
     * <p>
     *
     * @param msg The string message (or a key in the message catalog)
     */
    void warning(String msg);

    /**
     * Log an INFO message.
     * <p>
     * If the logger is currently enabled for the INFO message
     * level then the given message is forwarded to all the
     * registered output Handler objects.
     * <p>
     *
     * @param msg The string message (or a key in the message catalog)
     */
    void info(String msg);

    /**
     * Log a CONFIG message.
     * <p>
     * If the logger is currently enabled for the CONFIG message
     * level then the given message is forwarded to all the
     * registered output Handler objects.
     * <p>
     *
     * @param msg The string message (or a key in the message catalog)
     */
    void config(String msg);

    /**
     * Log a FINE message.
     * <p>
     * If the logger is currently enabled for the FINE message
     * level then the given message is forwarded to all the
     * registered output Handler objects.
     * <p>
     *
     * @param msg The string message (or a key in the message catalog)
     */
    void fine(String msg);

    /**
     * Log a FINER message.
     * <p>
     * If the logger is currently enabled for the FINER message
     * level then the given message is forwarded to all the
     * registered output Handler objects.
     * <p>
     *
     * @param msg The string message (or a key in the message catalog)
     */
    void finer(String msg);

    /**
     * Log a FINEST message.
     * <p>
     * If the logger is currently enabled for the FINEST message
     * level then the given message is forwarded to all the
     * registered output Handler objects.
     * <p>
     *
     * @param msg The string message (or a key in the message catalog)
     */
    void finest(String msg);

    /**
     * Logs a Exception.
     *
     * @param message Exception message.
     * @param t       Throwable to log.
     */
    void exception(String message, Throwable t);

    /**
     * Reads a line from the input.
     *
     * @return Line from input.
     */
    String readLine();

    /**
     * Reads an integer from the input.
     *
     * @return Integer from input.
     */
    int readInt();

    /**
     * Reads a boolean from the input.
     *
     * @return Boolean from input.
     */
    boolean readBoolean();
}
