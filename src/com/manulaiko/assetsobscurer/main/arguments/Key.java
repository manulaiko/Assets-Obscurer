package com.manulaiko.assetsobscurer.main.arguments;

import com.manulaiko.assetsobscurer.main.Settings;
import com.manulaiko.tabitha.arguments.Argument;
import com.manulaiko.tabitha.log.Console;
import com.manulaiko.tabitha.log.ConsoleManager;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.File;

/**
 * Key argument.
 * =============
 *
 * Sets the path to the encryption key.
 *
 * By default it's `./key`.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Key extends Argument {
    /**
     * Console logger.
     */
    public static Console console = ConsoleManager.forClass(Key.class);

    /**
     * Argument name.
     */
    private String _argument = "k";

    /**
     * Argument usage.
     */
    private String _usage = "-k=PATH";

    /**
     * Argument description.
     */
    private String _description = "Sets the path to the encryption key";

    /**
     * Default value.
     */
    private String _defaultValue = "./key";

    /**
     * Handles the argument.
     */
    @Override
    public void handle() {
        if (super.value().isEmpty()) {
            super.print(Key.console);

            return;
        }

        File f = new File(super.value());
        if (!f.isFile()) {
            super.print(Key.console);

            return;
        }

        Settings.key = f;
        Key.console.info("Encryption key path: " + f.getAbsolutePath());
    }
}
