package com.manulaiko.assetsobscurer.main.arguments;

import com.manulaiko.assetsobscurer.main.Settings;
import com.manulaiko.tabitha.arguments.Argument;
import com.manulaiko.tabitha.log.Console;
import com.manulaiko.tabitha.log.ConsoleManager;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.File;

/**
 * Key length argument.
 * =====================
 *
 * Sets the key length.
 *
 * By default it's `128`.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class KeyLength extends Argument {
    /**
     * Console logger.
     */
    public static Console console = ConsoleManager.forClass(KeyLength.class);

    /**
     * Argument name.
     */
    private String _argument = "l";

    /**
     * Argument usage.
     */
    private String _usage = "-l=LENGTH";

    /**
     * Argument description.
     */
    private String _description = "Sets the key length.";

    /**
     * Default value.
     */
    private String _defaultValue = "128";

    /**
     * Handles the argument.
     */
    @Override
    public void handle() {
        if (super.value().isEmpty()) {
            super.print(KeyLength.console);

            return;
        }

        try {
            Settings.keyLength = Integer.parseInt(super.value());
            KeyLength.console.info("Key length: " + super.value());
        } catch (Exception e) {
            super.print(KeyLength.console);
        }
    }
}
