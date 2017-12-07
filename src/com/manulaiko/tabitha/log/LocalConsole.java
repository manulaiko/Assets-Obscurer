package com.manulaiko.tabitha.log;

import com.manulaiko.tabitha.utils.Str;

import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Local console.
 * ==============
 *
 * Serves as mediator between the user and the program.
 *
 * The constructor accepts as parameter the output logger and
 * the input scanner.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class LocalConsole implements Console {
    /**
     * Logger to write the output.
     */
    private Logger _out;

    /**
     * Input stream.
     */
    private Scanner _in;

    /**
     * Constructor.
     *
     * @param out Output logger.
     * @param in  Input scanner.
     */
    public LocalConsole(Logger out, Scanner in) {
        this._out = out;
        this._in = in;
    }

    //<editor-fold desc="Output methods" defaultState="collapsed">

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
    @Override
    public void severe(String msg) {
        this.out().severe(msg);
    }

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
    @Override
    public void warning(String msg) {
        this.out().warning(msg);
    }

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
    @Override
    public void info(String msg) {
        this.out().info(msg);
    }

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
    @Override
    public void config(String msg) {
        this.out().config(msg);
    }

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
    @Override
    public void fine(String msg) {
        this.out().fine(msg);
    }

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
    @Override
    public void finer(String msg) {
        this.out().finer(msg);
    }

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
    @Override
    public void finest(String msg) {
        this.out().finest(msg);
    }

    /**
     * Logs a Exception.
     *
     * @param message Exception message.
     * @param t       Throwable to log.
     */
    @Override
    public void exception(String message, Throwable t) {
        this.out().warning(message);

        if (t == null) {
            return;
        }

        this.out().warning(t.getMessage());
        this.out().severe(Str.stackTraceToString(t));
    }
    //</editor-fold>

    //<editor-fold desc="Input methods" defaultState="collapsed">

    /**
     * Reads a line from the input.
     *
     * @return Line from input.
     */
    @Override
    public String readLine() {
        return this.in().nextLine();
    }

    /**
     * Reads an integer from the input.
     *
     * @return Integer from input.
     */
    @Override
    public int readInt() {
        return this.in().nextInt();
    }

    /**
     * Reads a boolean from the input.
     *
     * @return Boolean from input.
     */
    @Override
    public boolean readBoolean() {
        return this.in().nextBoolean();
    }
    //</editor-fold>

    //<editor-fold desc="Getters and Setters" defaultState="collapsed">

    /**
     * Sets `in`.
     *
     * @param in New value for `in`.
     */
    public void in(Scanner in) {
        this._in = in;
    }

    /**
     * Returns `in`.
     *
     * @return Current value of `in`.
     */
    public Scanner in() {
        return this._in;
    }

    /**
     * Sets `out`.
     *
     * @param out New value for `out`.
     */
    public void out(Logger out) {
        this._out = out;
    }

    /**
     * Returns `out`.
     *
     * @return Current value of `out`.
     */
    public Logger out() {
        return this._out;
    }
    //</editor-fold>
}
