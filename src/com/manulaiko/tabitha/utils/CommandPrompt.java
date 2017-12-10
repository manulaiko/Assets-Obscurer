package com.manulaiko.tabitha.utils;

import com.manulaiko.tabitha.log.Console;
import com.manulaiko.tabitha.log.ConsoleManager;

import java.util.ArrayList;

/**
 * Command Prompt.
 * ===============
 *
 * Offers an easy way to interact with the application with the use of commands.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class CommandPrompt {
    /**
     * Console instance.
     */
    public static final Console console = ConsoleManager.forClass(CommandPrompt.class);

    /**
     * Commands list.
     */
    private ArrayList<ICommand> _commands = new ArrayList<>();

    /**
     * Whether the loop should run or not.
     */
    private boolean _isRunning = false;

    /**
     * Adds a command to the list.
     *
     * @param command Command to add.
     */
    public void add(ICommand command) {
        this._commands.add(command);
    }

    /**
     * Starts the infinite loop for reading commands.
     */
    public void start() {
        this._isRunning = true;
        while (this._isRunning) {
            CommandPrompt.console.info("Enter a command to execute ('_help' for a list of commands): ");
            String[] command = CommandPrompt.console.readLine().split(" ");

            if (command[0].equalsIgnoreCase("_help")) {
                this.printAvailableCommands();
            } else {
                this.execute(command);
            }
        }
    }

    /**
     * Prints available commands.
     */
    public void printAvailableCommands() {
        CommandPrompt.console.info("Available commands:");
        CommandPrompt.console.info("");

        this._commands.forEach((ICommand c) -> {
            CommandPrompt.console.info(c.description());
            CommandPrompt.console.info("");
            CommandPrompt.console.info(Console.LINE_MINUS);
            CommandPrompt.console.info("");
        });
    }

    /**
     * Executes an specific command.
     *
     * @param command Command to execute.
     */
    public void execute(String[] command) {
        this._commands.forEach((ICommand c) -> {
            if (c.canHandle(command[0])) {
                c.handle(command);
            }
        });
    }

    /**
     * Stops the command prompt.
     */
    public void stop() {
        this._isRunning = false;
    }
}
