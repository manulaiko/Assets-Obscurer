package com.manulaiko.assetsobscurer.main.arguments;

import com.manulaiko.assetsobscurer.main.Settings;
import com.manulaiko.tabitha.arguments.Argument;
import com.manulaiko.tabitha.log.Console;
import com.manulaiko.tabitha.log.ConsoleManager;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.File;

/**
 * Private key argument.
 * =====================
 *
 * Sets the path to the private key.
 *
 * By default it's `./private.key`.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PrivateKey extends Argument {
    /**
     * Console logger.
     */
    public static Console console = ConsoleManager.forClass(PrivateKey.class);

    /**
     * Argument name.
     */
    private String _argument = "K";

    /**
     * Argument usage.
     */
    private String _usage = "-K=PATH";

    /**
     * Argument description.
     */
    private String _description = "Sets the path to the private key";

    /**
     * Default value.
     */
    private String _defaultValue = "./private.key";

    /**
     * Handles the argument.
     */
    @Override
    public void handle() {
        if (super.value().isEmpty()) {
            super.print(PrivateKey.console);

            return;
        }

        File f = new File(super.value());
        if (!f.isFile()) {
            super.print(PrivateKey.console);

            return;
        }

        Settings.privateKey = f;
        PrivateKey.console.info("Private key path: " + f.getAbsolutePath());
    }
}
