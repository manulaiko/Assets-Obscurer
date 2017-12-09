package com.manulaiko.assetsobscurer.main.commands;

import com.manulaiko.assetsobscurer.assets.AssetsManager;
import com.manulaiko.tabitha.utils.ICommand;

/**
 * Encrypt command.
 * =============
 *
 * Encrypts the assets library.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Encrypt implements ICommand {
    /**
     * Executes the command.
     *
     * @param command Command arguments.
     */
    @Override
    public void handle(String[] command) {
        AssetsManager.instance().encrypt();
    }

    /**
     * Checks whether this command can execute `name` command.
     *
     * @param name Command name to check.
     *
     * @return Whether this command can execute `name`.
     */
    @Override
    public boolean canHandle(String name) {
        return name.equalsIgnoreCase("encrypt");
    }

    /**
     * Returns command name.
     *
     * @return Command name.
     */
    @Override
    public String name() {
        return "encrypt";
    }

    /**
     * Returns command description.
     *
     * @return Command description.
     */
    @Override
    public String description() {
        return "Encrypts the assets library.";
    }
}
