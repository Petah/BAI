/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 *
 * @author Petah
 */
public class CustomLogFormatter extends Formatter {

    private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public String format(LogRecord record) {
        // Create a StringBuffer to contain the formatted record
        // start with the date.
        StringBuffer sb = new StringBuffer();

        // Get the date from the LogRecord and add it to the buffer
        Date date = new Date(record.getMillis());
        sb.append(dateFormat.format(date));
        sb.append(" ");

        sb.append(record.getSourceClassName());
        sb.append(" ");

        sb.append(record.getSourceMethodName());
        sb.append(" ");

        // Get the level name and add it to the buffer
        sb.append(record.getLevel().getName());
        sb.append(": ");

        // Get the formatted message (includes localization
        // and substitution of paramters) and add it to the buffer
        sb.append(formatMessage(record));

        return sb.toString();
    }
}
