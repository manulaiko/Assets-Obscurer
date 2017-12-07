package com.manulaiko.tabitha.log;

import com.manulaiko.tabitha.utils.Tools;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;

/**
 * Console manager.
 * ================
 *
 * Configures and manages all consoles.
 *
 * The method `initialize` accepts as parameter the configuration settings
 * that will be applied to all consoles.
 *
 * The method `forClass` returns a console for the specified class.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class ConsoleManager {
    /**
     * Console cache.
     */
    private static Map<Class, Console> _cache = new HashMap<>();

    /**
     * Configuration.
     */
    private static Configuration _config = new Configuration();

    /**
     * Initializes the logger manager.
     *
     * @param config Console settings.
     */
    public static void initialize(Configuration config) {
        ConsoleManager._config = config;
    }

    /**
     * Returns a console for the specified class.
     *
     * @param classs Class for the console.
     *
     * @return Console for `classs`.
     */
    public static Console forClass(Class classs) {
        Console logger = ConsoleManager._cache.getOrDefault(
                classs,
                ConsoleManager._buildConsole(classs)
        );

        if (!ConsoleManager._cache.containsKey(classs)) {
            ConsoleManager._cache.put(classs, logger);
        }

        return logger;
    }

    /**
     * Builds and returns a console.
     *
     * @param classs Class for the console.
     *
     * @return Console instance.
     */
    private static Console _buildConsole(Class classs) {
        Logger logger = Logger.getLogger(classs.getName());
        logger.setUseParentHandlers(false);
        logger.setLevel(ConsoleManager._config.level);

        for (Handler h : ConsoleManager._config.handlers) {
            h.setFormatter(ConsoleManager._config.format);
            h.setLevel(ConsoleManager._config.level);

            logger.addHandler(h);
        }

        return new LocalConsole(
                logger,
                Tools.in
        );
    }

    /**
     * Logger configuration.
     * =====================
     *
     * Contains the configuration for the loggers.
     *
     * @author Manulaiko <manulaiko@gmail.com>
     */
    public static class Configuration {
        /**
         * Log formatter.
         */
        public Formatter format = new LogFormatter();

        /**
         * Levels the log should log.
         */
        public Level level = Level.ALL;

        /**
         * Log handlers.
         */
        public Handler[] handlers = new Handler[]{
                new ConsoleHandler(),
                this._getFileHandler()
        };

        /**
         * Returns the file handler.
         *
         * @return File handler.
         */
        private FileHandler _getFileHandler() {
            SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/EEEE dd");

            String name = "logs/" + f.format(new Date()) + ".%u.log";

            try {
                new File(name).getParentFile().mkdirs();
                return new FileHandler(name);
            } catch (Exception e) {
                System.err.println("Couldn't instantiate file handler!");
                e.printStackTrace(System.err);
            }

            return null;
        }
    }
}
