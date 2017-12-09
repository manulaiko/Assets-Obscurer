package com.manulaiko.assetsobscurer.main.commands;

import com.manulaiko.assetsobscurer.assets.AssetsManager;
import com.manulaiko.tabitha.utils.ICommand;

/**
 * Save index command.
 * ===================
 *
 * Saves the assets index.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class SaveIndex implements ICommand {
    /**
     * Executes the command.
     *
     * @param command Command arguments.
     */
    @Override
    public void handle(String[] command) {
        AssetsManager.instance().saveIndex();
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
        return name.equalsIgnoreCase("save_index");
    }

    /**
     * Returns command name.
     *
     * @return Command name.
     */
    @Override
    public String name() {
        return "save_index";
    }

    /**
     * Returns command description.
     *
     * @return Command description.
     */
    @Override
    public String description() {
        return "Saves the assets index.";
    }
}
