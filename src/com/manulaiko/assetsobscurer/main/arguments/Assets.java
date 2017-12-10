package com.manulaiko.assetsobscurer.main.arguments;

import com.manulaiko.assetsobscurer.main.Settings;
import com.manulaiko.tabitha.arguments.Argument;
import com.manulaiko.tabitha.log.Console;
import com.manulaiko.tabitha.log.ConsoleManager;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.File;

/**
 * Assets argument.
 * ================
 *
 * Sets the path to the assets folder.
 *
 * By default it's `./assets`.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Assets extends Argument {
    /**
     * Console logger.
     */
    public static Console console = ConsoleManager.forClass(Assets.class);

    /**
     * Argument name.
     */
    private String _argument = "a";

    /**
     * Argument usage.
     */
    private String _usage = "-a=PATH";

    /**
     * Argument description.
     */
    private String _description = "Sets the path to the assets folder";

    /**
     * Default value.
     */
    private String _defaultValue = "./assets";

    /**
     * Handles the argument.
     */
    @Override
    public void handle() {
        if (super.value().isEmpty()) {
            super.print(Assets.console);

            return;
        }

        File f = new File(super.value());
        if (!f.isDirectory()) {
            super.print(Assets.console);

            return;
        }

        Settings.assets = f;
        Assets.console.info("Assets path: " + f.getAbsolutePath());
    }
}
