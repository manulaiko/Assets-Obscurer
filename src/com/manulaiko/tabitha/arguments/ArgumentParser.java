package com.manulaiko.tabitha.arguments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Argument Parser.
 * ================
 *
 * Helper class for parsing arguments.
 *
 * The constructor accepts as parameter the arguments to parse.
 *
 * For adding arguments to the list use the method `add`.
 *
 * For parsing the arguments use the method `parse`.
 *
 * Example:
 *
 * ```java
 * ArgumentParser ap = new ArgumentParser(args);
 *
 * ap.add(new ShowHelpArgument());
 * ap.add(new EnableDebugModeArgument());
 *
 * ap.parse();
 * ```
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class ArgumentParser {
    /**
     * The arguments to parse.
     */
    private List<String> _args;

    /**
     * The available arguments handlers.
     */
    private ArrayList<Argument> _handlers = new ArrayList<>();

    /**
     * Help argument.
     */
    private HelpArgument _help = new HelpArgument();

    /**
     * Constructor.
     *
     * @param args Arguments to parse.
     */
    public ArgumentParser(String[] args, HelpArgument help) {
        this._args = Arrays.asList(args);
        this._help = help;
    }

    /**
     * Adds an argument handler to the array.
     *
     * @param handler Handler to add.
     */
    public void add(Argument handler) {
        this._handlers.add(handler);
        this._help.arguments.add(handler);
    }

    /**
     * Parses the arguments.
     */
    public void parse() {
        this._args.forEach(this::_handleArgument);
    }

    /**
     * Handles an argument.
     *
     * @param argument Argument to handle.
     */
    private void _handleArgument(String argument) {
        Argument handler = this._findHandler(argument);

        if (handler == null) {
            return;
        }

        handler.handle();
    }

    /**
     * Finds the argument handler for a certain argument.
     *
     * @param arg Argument.
     *
     * @return Argument's handler.
     */
    private Argument _findHandler(String arg) {
        Argument handler = null;

        String[] argument = this._parseArgument(arg);

        String name = argument[0];
        String value = argument[1];

        if (this._help.canHandle(name)) {
            return this._help;
        }

        for (Argument h : this._handlers) {
            if (!h.canHandle(name)) {
                continue;
            }

            h.name(name);
            h.value(value);

            handler = h;
        }

        return handler;
    }

    /**
     * Parses an argument.
     *
     * @param arg Argument to parse.
     *
     * @return Parsed argument.
     */
    private String[] _parseArgument(String arg) {
        if (!arg.contains("=")) {
            return new String[]{
                    arg,
                    ""
            };
        }

        int pos = arg.indexOf("=");

        String key = arg.substring(0, pos);
        String val = arg.substring(pos + 1, arg.length());

        return new String[]{
                key,
                val
        };
    }

}
