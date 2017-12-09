package com.manulaiko.assetsobscurer.main.arguments;

import com.manulaiko.assetsobscurer.main.Settings;
import com.manulaiko.tabitha.arguments.Argument;
import com.manulaiko.tabitha.log.Console;
import com.manulaiko.tabitha.log.ConsoleManager;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Command mode argument.
 * ======================
 *
 * Enables/disable command mode.
 *
 * By default it's `false`.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CommandMode extends Argument {
    /**
     * Console logger.
     */
    public static Console console = ConsoleManager.forClass(CommandMode.class);

    /**
     * Argument name.
     */
    private String _argument = "c";

    /**
     * Argument usage.
     */
    private String _usage = "-c=true|false";

    /**
     * Argument description.
     */
    private String _description = "Enables/disable command mode.";

    /**
     * Default value.
     */
    private String _defaultValue = "false";

    /**
     * Handles the argument.
     */
    @Override
    public void handle() {
        if (super.value().isEmpty()) {
            super.print(CommandMode.console);

            return;
        }

        Settings.commandMode = Boolean.parseBoolean(super.value());
        CommandMode.console.info("Command mode: " + Settings.commandMode);
    }
}
