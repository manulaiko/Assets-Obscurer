package com.manulaiko.tabitha.log;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Log formatter.
 * ==============
 *
 * Formats log entries according to the specified format.
 *
 * You can change the format by calling the `LogFormatter.format(String)` method.
 *
 * Format variables:
 *
 * * `sequenceNumber`: Sequence number.
 * * `sourceClassName`: Class that issued logging call.
 * * `level`: Logging message level.
 * * `sourceMethodName`: Method that issued logging call.
 * * `message`: Non-localized raw message text.
 * * `threadID`: Thread ID for thread that issued logging call.
 * * `millis`: Event time in milliseconds since 1970.
 * * `loggerName`: Name of the source Logger.
 * * `Y`: Current year.
 * * `m`: Current month.
 * * `d`: Current day.
 * * `H`: Current hour.
 * * `i`: Current minute.
 * * `s`: Current second.
 * * `S`: Current millisecond.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class LogFormatter extends Formatter {
    /**
     * Log format.
     *
     * Each variable must be wrapped by `{}`.
     */
    private static String _format = "[{loggerName} {H}:{i}:{s},{S}] ({level}) {message}";

    /**
     * Sets the log format.
     *
     * @param format New log format.
     */
    public static void format(String format) {
        LogFormatter._format = format;
    }

    /**
     * Format the given log record and return the formatted string.
     * <p>
     * The resulting formatted String will normally include a
     * localized and formatted version of the LogRecord's message field.
     * It is recommended to use the {@link Formatter#formatMessage}
     * convenience method to localize and format the message field.
     *
     * @param record the log record to be formatted.
     *
     * @return the formatted log record
     */
    @Override
    public String format(LogRecord record) {
        String f = LogFormatter._format;
        Calendar c = new GregorianCalendar();
        c.setTimeInMillis(record.getMillis());

        return f.replace("{sequenceNumber}", record.getSequenceNumber() + "")
                .replace("{sourceClassName}", record.getSourceClassName())
                .replace("{level}", record.getLevel().toString())
                .replace("{sourceMethodName}", record.getSourceMethodName())
                .replace("{message}", record.getMessage())
                .replace("{threadID}", record.getThreadID() + "")
                .replace("{millis}", record.getMillis() + "")
                .replace("{loggerName}", record.getLoggerName())
                .replace("{Y}", c.get(Calendar.YEAR) + "")
                .replace("{m}", c.get(Calendar.MONTH) + "")
                .replace("{d}", c.get(Calendar.DAY_OF_MONTH) + "")
                .replace("{H}", c.get(Calendar.HOUR) + "")
                .replace("{i}", c.get(Calendar.MINUTE) + "")
                .replace("{s}", c.get(Calendar.SECOND) + "")
                .replace("{S}", c.get(Calendar.MILLISECOND) + "") + "\n";
    }
}
