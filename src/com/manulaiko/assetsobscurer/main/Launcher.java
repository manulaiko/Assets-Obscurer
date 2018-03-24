package com.manulaiko.assetsobscurer.main;

import com.manulaiko.assetsobscurer.assets.AssetsManager;
import com.manulaiko.assetsobscurer.main.arguments.*;
import com.manulaiko.assetsobscurer.main.commands.*;
import com.manulaiko.tabitha.Application;
import com.manulaiko.tabitha.arguments.Argument;
import com.manulaiko.tabitha.log.Console;
import com.manulaiko.tabitha.log.ConsoleManager;
import com.manulaiko.tabitha.utils.CommandPrompt;

import java.util.Arrays;

/**
 * Launcher class.
 * ===============
 *
 * Executes the application.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Launcher extends Application {
    ///////////////////////////////////
    // Static methods and properties //
    ///////////////////////////////////

    /**
     * Console logger.
     */
    public static final Console console = ConsoleManager.forClass(Launcher.class);

    /**
     * Main method.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Launcher launcher = new Launcher("Manulaiko", "Assets Obscurer", "2.0.0", args);
        launcher.start();
    }

    ///////////////////////////////////////
    // Non static methods and properties //
    ///////////////////////////////////////

    /**
     * Constructor.
     *
     * @param author      Author name.
     * @param application Application name.
     * @param version     Application version.
     * @param arguments   Command line arguments.
     */
    public Launcher(String author, String application, String version, String[] arguments) {
        super(author, application, version, arguments);
    }

    /**
     * Called when the application has finished bootstrapping.
     */
    @Override
    public void onStarted() {
        if (Settings.commandMode) {
            this._startCommandPrompt();

            return;
        }

        AssetsManager a = AssetsManager.instance();
        a.scan();

        if (Settings.encryptMode) {
            a.encrypt();
        } else {
            a.decrypt();
        }

        a.saveIndex();
        new DumpIndex().handle(new String[]{});
    }

    /**
     * Starts the command prompt.
     */
    private void _startCommandPrompt() {
        CommandPrompt cp = new CommandPrompt();

        cp.add(new Scan());
        cp.add(new Encrypt());
        cp.add(new Decrypt());
        cp.add(new DumpIndex());
        cp.add(new SaveIndex());
        cp.add(new GenerateKeyPair());
        cp.add(new LoadKeyPair());

        cp.start();
    }

    /**
     * Returns the argument instances.
     *
     * @return Argument instances.
     */
    @Override
    protected Iterable<Argument> _arguments() {
        return Arrays.asList(
                new Assets(),
                new KeyLength(),
                new CommandMode(),
                new EncryptMode()
        );
    }
}
