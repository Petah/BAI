/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.util;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import org.petah.spring.bai.gui.GUIManager;

/**
 *
 * @author Petah
 */
public class CustomLogHandler extends Handler {

    private CustomLogFormatter customLogFormatter = new CustomLogFormatter();

    @Override
    public void close() throws SecurityException {
    }

    @Override
    public void flush() {
    }

    @Override
    public void publish(LogRecord record) {
        GUIManager.log(customLogFormatter.format(record));
    }
}
