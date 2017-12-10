package com.manulaiko.tabitha.arguments;

import com.manulaiko.tabitha.log.Console;
import com.manulaiko.tabitha.log.ConsoleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Help argument.
 * ==============
 *
 * Prints the help page and exits.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class HelpArgument extends Argument {
    /**
     * Console logger.
     */
    public static final Console console = ConsoleManager.forClass(HelpArgument.class);

    /**
     * Available arguments.
     */
    public List<Argument> arguments = new ArrayList<>();

    /**
     * Returns the argument name.
     *
     * @return Argument name.
     */
    @Override
    public String argument() {
        return "help";
    }

    /**
     * Handles the argument.
     */
    @Override
    public void handle() {
        this.print();

        System.exit(0);
    }

    /**
     * Prints the arguments, override this instead of `handle`.
     */
    public void print() {
        HelpArgument.console.info("Arguments:");
        this.arguments.forEach(this::_print);
    }

    /**
     * Prints an argument to the console.
     *
     * @param a Argument to print.
     */
    private void _print(Argument a) {
        HelpArgument.console.info(Console.LINE_MINUS);
        a.print(HelpArgument.console);
    }
}
