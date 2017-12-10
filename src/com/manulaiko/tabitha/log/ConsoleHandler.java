package com.manulaiko.tabitha.log;

import java.util.logging.*;

/**
 * Console handler.
 * ================
 *
 * Logs records to standard output or error output
 * depending on the log level.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class ConsoleHandler extends Handler {
    /**
     * Publish a <tt>LogRecord</tt>.
     * <p>
     * The logging request was made initially to a <tt>Logger</tt> object,
     * which initialized the <tt>LogRecord</tt> and forwarded it here.
     * <p>
     * The <tt>Handler</tt>  is responsible for formatting the message, when and
     * if necessary.  The formatting should include localization.
     *
     * @param record description of the log event. A null record is
     *               silently ignored and is not published
     */
    @Override
    public void publish(LogRecord record) {
        if (this.getFormatter() == null) {
            this.setFormatter(new SimpleFormatter());
        }

        try {
            String message = this.getFormatter().format(record);
            if (record.getLevel().intValue() >= Level.WARNING.intValue()) {
                System.err.write(message.getBytes());

                return;
            }

            System.out.write(message.getBytes());
        } catch (Exception exception) {
            this.reportError(null, exception, ErrorManager.FORMAT_FAILURE);
        }
    }

    /**
     * Flush any buffered output.
     */
    @Override
    public void flush() {

    }

    /**
     * Close the <tt>Handler</tt> and free all associated resources.
     * <p>
     * The close method will perform a <tt>flush</tt> and then close the
     * <tt>Handler</tt>.   After close has been called this <tt>Handler</tt>
     * should no longer be used.  Method calls may either be silently
     * ignored or may throw runtime exceptions.
     *
     * @throws SecurityException if a security manager exists and if
     *                           the caller does not have <tt>LoggingPermission("control")</tt>.
     */
    @Override
    public void close() throws SecurityException {

    }
}
