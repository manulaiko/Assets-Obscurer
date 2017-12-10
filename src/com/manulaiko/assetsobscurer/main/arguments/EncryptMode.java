package com.manulaiko.assetsobscurer.main.arguments;

import com.manulaiko.assetsobscurer.main.Settings;
import com.manulaiko.tabitha.arguments.Argument;
import com.manulaiko.tabitha.log.Console;
import com.manulaiko.tabitha.log.ConsoleManager;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Encrypt mode argument.
 * ======================
 *
 * Enables/disable encrypt mode.
 *
 * By default it's `false`.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EncryptMode extends Argument {
    /**
     * Console logger.
     */
    public static Console console = ConsoleManager.forClass(CommandMode.class);

    /**
     * Argument name.
     */
    private String _argument = "e";

    /**
     * Argument usage.
     */
    private String _usage = "-e=true|false";

    /**
     * Argument description.
     */
    private String _description = "Enables/disable encrypt mode.";

    /**
     * Default value.
     */
    private String _defaultValue = "true";

    /**
     * Handles the argument.
     */
    @Override
    public void handle() {
        if (super.value().isEmpty()) {
            super.print(EncryptMode.console);

            return;
        }

        Settings.encryptMode = Boolean.parseBoolean(super.value());
        CommandMode.console.info("Encrypt mode: " + Settings.encryptMode);
    }
}
