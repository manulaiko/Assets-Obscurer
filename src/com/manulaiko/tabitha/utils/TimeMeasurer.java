package com.manulaiko.tabitha.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Time measurer.
 * ==============
 *
 * Helper util for measuring elapsed time.
 *
 * The constructor accepts as parameter a boolean indicating
 * whether a starter mark should be instantiated or not.
 * If it's set to true (default), a mark named `start` will be
 * instantiated.
 *
 * To add a new mark use the method `start` which accepts as
 * parameter the name of the mark.
 *
 * Once a mark is added it won't be stopped until you call the
 * method `stop` with the name of the mark.
 *
 * The method `mark` will return the elapsed time in milliseconds
 * of the specified mark.
 *
 * Example:
 *
 * ```java
 * TimeMeasurer measurer = new TimeMeasurer();
 * measurer.start("something");
 * // do something.
 * measurer.stop("something");
 *
 * Console.println("'something' took "+ measurer.measure("something") +"ms");
 * ```
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class TimeMeasurer {
    /**
     * Registered marks.
     */
    private Map<String, Mark> _marks = new HashMap<>();

    /**
     * Constructor.
     */
    public TimeMeasurer() {
        this(true);
    }

    /**
     * Constructor.
     *
     * @param instantiateStart Whether to instantiate `start` mark or not.
     */
    public TimeMeasurer(boolean instantiateStart) {
        if (!instantiateStart) {
            return;
        }

        this.start("start");
    }

    /**
     * Starts a new mark.
     *
     * @param name Mark name.
     *
     * @return Instantiated mark.
     */
    public Mark start(String name) {
        Mark mark = new Mark(name, System.nanoTime());

        this.mark(mark);

        return mark;
    }

    /**
     * Stops a mark.
     *
     * @param name Mark name.
     */
    public void stop(String name) {
        Mark mark = this.mark(name);

        if (mark != null) {
            this.stop(mark);
        }
    }

    /**
     * Stops a mark.
     *
     * @param mark Mark instance.
     */
    public void stop(Mark mark) {
        mark.stop(System.nanoTime());
    }

    /**
     * Returns elapsed milliseconds of given mark.
     *
     * @param name Mark name.
     */
    public long measure(String name) {
        Mark mark = this.mark(name);

        if (mark != null) {
            return this.measure(mark);
        }

        return -1;
    }

    /**
     * Returns elapsed milliseconds of given mark.
     *
     * @param mark Mark instance.
     */
    public long measure(Mark mark) {
        long start = mark.start();
        long end = mark.stop();

        if (end <= 0) { // Mark hasn't been stopped
            end = System.nanoTime();
        }

        return (end - start) / 1000000;
    }

    /**
     * Returns specified mark.
     *
     * @param name Mark name.
     */
    public Mark mark(String name) {
        return this._marks.get(name);
    }

    /**
     * Adds a mark to the map.
     *
     * @param mark Mark to add.
     */
    public void mark(Mark mark) {
        this._marks.put(mark.name(), mark);
    }

    /**
     * Mark class.
     * ===========
     *
     * Represents a measured mark.
     *
     * @author Manulaiko <manulaiko@gmail.com>
     */
    public class Mark {
        /**
         * Start time.
         */
        private long _start = -1;

        /**
         * Stop time.
         */
        private long _stop = -1;

        /**
         * Mark name.
         */
        private String _name = "";

        /**
         * Constructor.
         *
         * @param name  Mark name.
         * @param start Start time.
         */
        public Mark(String name, long start) {
            this._name = name;
            this._start = start;
        }

        /**
         * Restarts the mark.
         */
        public void restart() {
            this.start(System.nanoTime());
        }

        /**
         * Returns start time.
         *
         * @return Start time.
         */
        public long start() {
            return this._start;
        }

        /**
         * Sets start time.
         *
         * @param start New start time.
         */
        public void start(long start) {
            this._start = start;
        }

        /**
         * Returns stop time.
         *
         * @return Stop time.
         */
        public long stop() {
            return this._stop;
        }

        /**
         * Sets stop time.
         *
         * @param stop New stop time.
         */
        public void stop(long stop) {
            this._stop = stop;
        }

        /**
         * Returns mark name.
         *
         * @return Mark name.
         */
        public String name() {
            return this._name;
        }

        /**
         * Sets mark time
         *
         * @param name New mark name.
         */
        public void name(String name) {
            this._name = name;
        }
    }
}
