package com.manulaiko.assetsobscurer.main.commands;

import com.manulaiko.assetsobscurer.assets.AssetsManager;
import com.manulaiko.assetsobscurer.assets.Index;
import com.manulaiko.tabitha.log.Console;
import com.manulaiko.tabitha.log.ConsoleManager;
import com.manulaiko.tabitha.utils.ICommand;

/**
 * Dump index command.
 * ===================
 *
 * Prints the current asset index.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class DumpIndex implements ICommand {
    /**
     * Console logger.
     */
    public static final Console console = ConsoleManager.forClass(DumpIndex.class);

    /**
     * Executes the command.
     *
     * @param command Command arguments.
     */
    @Override
    public void handle(String[] command) {
        Index i = AssetsManager.instance().index();

        i.assets().forEach(a -> {
            DumpIndex.console.fine(a.path() + ":");
            DumpIndex.console.fine("\tHash: " + a.hash());
            DumpIndex.console.fine("\tEncrypted: " + a.isEncrypted());
            DumpIndex.console.fine("");
        });

        DumpIndex.console.info("Total: " + i.assets().size() + " indexed assets.");
        DumpIndex.console.info("\t" + i.encrypted() + " encrypted assets.");
        DumpIndex.console.info("\t" + i.decrypted() + " decrypted assets.");
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
        return name.equalsIgnoreCase("dump_index");
    }

    /**
     * Returns command name.
     *
     * @return Command name.
     */
    @Override
    public String name() {
        return "dump_index";
    }

    /**
     * Returns command description.
     *
     * @return Command description.
     */
    @Override
    public String description() {
        return "Prints the current asset index.";
    }
}
