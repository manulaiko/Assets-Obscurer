package com.manulaiko.tabitha.arguments;

import com.manulaiko.tabitha.log.Console;

/**
 * Argument handler.
 * =================
 *
 * Base class for all argument handlers.
 *
 * The method `handle` is executed when an argument can be handled by the instance.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public abstract class Argument {
    /**
     * Argument name.
     */
    private String _name;

    /**
     * Argument value.
     */
    private String _value;

    /**
     * Checks that the argument can be handled
     * by this instance.
     *
     * @param arg Argument.
     *
     * @return `true` if the argument can be handled by this instance, `false` if not.
     */
    public boolean canHandle(String arg) {
        return (
                arg.equalsIgnoreCase("-"+ this.argument()) ||
                arg.equalsIgnoreCase("--"+ this.argument())
        );
    }

    /**
     * Prints the argument info to the specified console.
     *
     * @param console Console to print the output.
     */
    public void print(Console console) {
        console.info( this.argument() +"\t\t"+ this.description());
        console.info("\tUsage:");
        console.info("\t\t\t"+ this.usage());
        console.info("\tDefault value:");
        console.info("\t\t\t"+ this.defaultValue());
    }

    /**
     * Sets argument name.
     *
     * @param name New argument name.
     */
    public void name(String name) {
        this._name = name;
    }

    /**
     * Returns argument name.
     *
     * @return Argument name.
     */
    public String name() {
        return this._name;
    }

    /**
     * Sets argument value.
     *
     * @param value New argument value.
     */
    public void value(String value) {
        this._value = value;
    }

    /**
     * Returns argument value.
     *
     * @return Argument value.
     */
    public String value() {
        return this._value;
    }

    /**
     * Returns argument description.
     *
     * @return Argument description.
     */
    public String description() {
        return "";
    }

    /**
     * Returns argument usage.
     *
     * @return Argument usage.
     */
    public String usage() {
        return "";
    }

    /**
     * Returns argument default value.
     *
     * @return Argument default value.
     */
    public String defaultValue() {
        return "";
    }

    /**
     * Returns the argument name.
     *
     * @return Argument name.
     */
    public abstract String argument();

    /**
     * Handles the argument.
     */
    public abstract void handle();
}
