package com.manulaiko.tabitha;

import com.manulaiko.tabitha.arguments.Argument;
import com.manulaiko.tabitha.arguments.ArgumentParser;
import com.manulaiko.tabitha.arguments.HelpArgument;
import com.manulaiko.tabitha.log.ConsoleManager;

import java.util.ArrayList;

/**
 * Application class.
 * ==================
 *
 * Represents the application itself. You can extend this
 * class to avoid to perform common operations each time.
 *
 * The constructor accepts as parameter the author name,
 * application name and command line arguments.
 *
 * The method `_arguments` returns an iterable with the `Argument`
 * instances that will be parsed.
 *
 * The method `onStarted` is called when the application has
 * finished bootstrapping.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public abstract class Application {
    /**
     * Console instance.
     */
    public static final com.manulaiko.tabitha.log.Console console = ConsoleManager.forClass(Application.class);

    /**
     * Author name.
     */
    private String _author = "";

    /**
     * Application name.
     */
    private String _application = "";

    /**
     * Application version.
     */
    private String _version = "";

    /**
     * Command line arguments.
     */
    private String[] _arguments = new String[]{};

    /**
     * Constructor.
     *
     * @param author      Author name.
     * @param application Application name.
     * @param version     Application version.
     * @param arguments   Command line arguments.
     */
    public Application(String author, String application, String version, String[] arguments) {
        this.author(author);
        this.application(application);
        this.version(version);
        this.arguments(arguments);
    }

    /**
     * Empty constructor.
     *
     * For those who prefer to override methods.
     */
    public Application() {

    }

    /**
     * Starts the application.
     */
    public void start() {
        Application.console.info(this.application() + " v" + this.version() + " by " + this.author());
        Application.console.info(com.manulaiko.tabitha.log.Console.LINE_EQ);

        this._parseArguments();

        this.onStarted();
    }

    /**
     * Parses command line arguments.
     */
    private void _parseArguments() {
        ArgumentParser ap = new ArgumentParser(this.arguments(), this.helpArgument());

        this._arguments().forEach(ap::add);

        ap.parse();
    }

    /**
     * Returns the _help argument.
     *
     * @return Help argument.
     */
    public HelpArgument helpArgument() {
        return new HelpArgument();
    }

    /**
     * Returns the argument instances.
     *
     * @return Argument instances.
     */
    protected Iterable<Argument> _arguments() {
        return new ArrayList<>();
    }

    /**
     * Called when the application has finished bootstrapping.
     */
    public abstract void onStarted();

    ///////////////////////////////
    // Start Getters and Setters //
    ///////////////////////////////

    /**
     * Sets `author`.
     *
     * @param author New value for `author`.
     */
    public void author(String author) {
        this._author = author;
    }

    /**
     * Returns `author`.
     *
     * @return Current value of `author`.
     */
    public String author() {
        return this._author;
    }

    /**
     * Sets `application`.
     *
     * @param application New value for `application`.
     */
    public void application(String application) {
        this._application = application;
    }

    /**
     * Returns `application`.
     *
     * @return Current value of `application`.
     */
    public String application() {
        return this._application;
    }

    /**
     * Sets `version`.
     *
     * @param version New value for `version`.
     */
    public void version(String version) {
        this._version = version;
    }

    /**
     * Returns `version`.
     *
     * @return Current value of `version`.
     */
    public String version() {
        return this._version;
    }

    /**
     * Sets `arguments`.
     *
     * @param arguments New value for `arguments`.
     */
    public void arguments(String[] arguments) {
        this._arguments = arguments;
    }

    /**
     * Returns `arguments`.
     *
     * @return Current value of `arguments`.
     */
    public String[] arguments() {
        return this._arguments;
    }
    /////////////////////////////
    // End Getters and Setters //
    /////////////////////////////
}
