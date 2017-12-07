package com.manulaiko.assetsobscurer.main.arguments;

import com.manulaiko.assetsobscurer.main.Settings;
import com.manulaiko.tabitha.arguments.Argument;
import com.manulaiko.tabitha.log.Console;
import com.manulaiko.tabitha.log.ConsoleManager;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.File;

/**
 * Public key argument.
 * =====================
 *
 * Sets the path to the public key.
 *
 * By default it's `./public.key`.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PublicKey extends Argument {
    /**
     * Console logger.
     */
    public static Console console = ConsoleManager.forClass(PublicKey.class);

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
    private String _description = "Sets the path to the public key";

    /**
     * Default value.
     */
    private String _defaultValue = "./public.key";

    /**
     * Handles the argument.
     */
    @Override
    public void handle() {
        if (super.value().isEmpty()) {
            super.print(PublicKey.console);

            return;
        }

        File f = new File(super.value());
        if (!f.isFile()) {
            super.print(PublicKey.console);

            return;
        }

        Settings.publicKey = f;
        PublicKey.console.info("Public key path: " + f.getAbsolutePath());
    }
}
