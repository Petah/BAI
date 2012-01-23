package org.petah.spring.bai.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author David Neilsen
 */
public class Log {

    public static PrintStream file;
    public static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss:SSS dd.MM.yyyy");
    public static Object lastMessage = null;
    public static int lastMessageCount = 0;

    static {
        try {
            file = new PrintStream(new File("c:\\work\\bai.log"));
        } catch (FileNotFoundException ex) {
        }
    }
    
    public static void println(String type, Object message) {
        if (lastMessage != null) {
            if (lastMessage.equals(message)) {
                lastMessageCount++;
                return;
            } else {
                if (lastMessageCount > 0) {
                    file.print(" [" + (lastMessageCount + 1) + "...]");
                }
                file.println();
                lastMessageCount = 0;
            }
        }
        file.print("[" + type + " " + dateFormat.format(new Date()) + "] " + message);
        file.flush();
        lastMessage = message;
    }

    public static void debug(Object message) {
        println("DEBUG", message);
    }
    
    public static void entry(Class cls, String method) {
        println("ENTRY", cls.getName() + "." + method + "()");
    }
    
    public static void ignoredException(Exception exception) {
        println("IGEXP", exception);
    }

    @Override
    protected void finalize() throws Throwable {
        println("EOF", "");
        file.println();
        file.println();
        file.flush();
    }
    
    
}
