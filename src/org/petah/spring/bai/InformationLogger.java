/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.common.util.FileUtil;
import org.petah.common.util.profiler.Memory;
import org.petah.common.util.profiler.Profiler;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.gui.GUIManager;
import org.petah.spring.bai.log.Log;
import org.petah.spring.bai.util.CustomLogHandler;
import org.petah.spring.bai.util.CustomProfileHandler;

/**
 *
 * @author Petah
 */
public class InformationLogger {

    // Files
    private static Option<File> errorLogFile;
    private static Option<File> outputLogFile;
    private static Option<File> settingsFile;
    // Directorys
    private static Option<File> aiDirectory;
    private static Option<File> logDirectory;
    private static Option<File> logArchiveDirectory;
    private static Option<File> settingsDirectory;
    private static Option<File> cacheDirectory;
    // Formatting options
    public static Option<String> timeStampFormat = OptionsManager.getOption(
            new Option<String>("InformationLogger.timeStampFormat", "HH-mm-ss__MMM-d-yyyy"));
    // Output filters
    private static Option<Boolean> logToFile = OptionsManager.getOption(
            new Option<Boolean>("InformationLogger.logToFile", true));
    private static Option<Level> loggerLevel = OptionsManager.getOption(
            new Option<Level>("InformationLogger.loggerLevel", Level.ALL));
    private static Option<Boolean> profilerEnabled = OptionsManager.getOption(
            new Option<Boolean>("InformationLogger.profilerEnabled", true));

    /**
     * Static constructor
     */
    static {
        setDirectorys();
        setFiles();
    }

    private static void setDirectorys() {
        File jarFile = new File(AIDelegate.class.getProtectionDomain().getCodeSource().getLocation().getFile());
        if (jarFile.isDirectory()) {
            aiDirectory = OptionsManager.getOption(new Option<File>("InformationLogger.aiDirectory", jarFile),
                    new Option<Boolean>("transient", true));
        } else {
            aiDirectory = OptionsManager.getOption(new Option<File>("InformationLogger.aiDirectory", jarFile.getParentFile()),
                    new Option<Boolean>("transient", true));
        }
        logDirectory = OptionsManager.getOption(
                new Option<File>("InformationLogger.logDirectory", new File(aiDirectory.getValue().getPath() + File.separator + "logs")),
                new Option<Boolean>("transient", true));
        logArchiveDirectory = OptionsManager.getOption(
                new Option<File>("InformationLogger.logArchiveDirectory", new File(logDirectory.getValue().getPath() + File.separator + "archive")),
                new Option<Boolean>("transient", true));
        settingsDirectory = OptionsManager.getOption(
                new Option<File>("InformationLogger.settingsDirectory", new File(aiDirectory.getValue().getPath() + File.separator + "settings")),
                new Option<Boolean>("transient", true));
        cacheDirectory = OptionsManager.getOption(
                new Option<File>("InformationLogger.cacheDirectory", new File(aiDirectory.getValue().getPath() + File.separator + "cache")),
                new Option<Boolean>("transient", true));
        logDirectory.getValue().mkdirs();
        logArchiveDirectory.getValue().mkdirs();
        settingsDirectory.getValue().mkdirs();
        cacheDirectory.getValue().mkdirs();
    }

    private static void setFiles() {
        String dir = logDirectory.getValue().getAbsolutePath() + File.separator;
        SimpleDateFormat formatter = new SimpleDateFormat(timeStampFormat.getValue());
        outputLogFile = OptionsManager.getOption(
                new Option<File>("InformationLogger.outputLogFile", new File(dir + "output_" + formatter.format(new Date()) + ".txt")),
                new Option<Boolean>("transient", true));
        errorLogFile = OptionsManager.getOption(
                new Option<File>("InformationLogger.errorLogFile", new File(dir + "errorlog_" + formatter.format(new Date()) + ".txt")),
                new Option<Boolean>("transient", true));
        settingsFile = OptionsManager.getOption(
                new Option<File>("InformationLogger.settingsFile", new File(settingsDirectory.getValue().getAbsolutePath() + File.separator + "settings.xml")),
                new Option<Boolean>("transient", true));
    }

    public static void init() {
        Log.entry(InformationLogger.class, "init");
        // Set up the loggers
        Logger.getLogger(InformationLogger.class.getName()).entering(InformationLogger.class.getName(), "init()");
        Logger.getLogger("org.petah").addHandler(new CustomLogHandler());
        Logger.getLogger("org.petah").setUseParentHandlers(false);
        Logger.getLogger("org.petah").setLevel(loggerLevel.getValue());

        // Setup logging to file
        if (logToFile.getValue()) {
            // Moves old logs to the archive directory
            moveOldLogs();

            // Redirect output streams to files
            redirectOutputStreams();
        }

        // Setup the profiler
        Profiler.setProfileHandler(new CustomProfileHandler());
        Profiler.setActive(profilerEnabled.getValue());

        // Log memory information
        Memory memory = new Memory();
        memory.update();
        Logger.getLogger(InformationLogger.class.getName()).info("Memory: free: " + memory.getFree() + " used: " + memory.getUsed()
                + " allocated: " + memory.getAllocated() + " maximum: " + memory.getMaximum());
    }

    /**
     * Moves old logs to the archive directory
     */
    private static void moveOldLogs() {
        try {
            FileUtil.moveAllFiles(logDirectory.getValue().getAbsoluteFile(),
                    logArchiveDirectory.getValue().getAbsoluteFile(),
                    new FilenameFilter() {

                        public boolean accept(File dir, String name) {
                            return name.endsWith(".txt");
                        }
                    });
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error archiving old log files: " + ex.getMessage(), "BAI Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(InformationLogger.class.getName()).log(Level.SEVERE, "Could not archive log files.", ex);
        }
    }

    /**
     * Redirect output streams to files
     */
    private static void redirectOutputStreams() {
        try {
            System.setOut(new PrintStream(new FileOutputStream(outputLogFile.getValue())));
            System.setErr(new PrintStream(new FileOutputStream(errorLogFile.getValue())));
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error creating log files: " + ex.getMessage(), "BAI Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(InformationLogger.class.getName()).log(Level.SEVERE, "Could not create log files.", ex);
        }
    }

    public static File getErrorLogFile() {
        return errorLogFile.getValue();
    }

    public static File getAiDirectory() {
        return aiDirectory.getValue();
    }

    public static File getLogArchiveDirectory() {
        return logArchiveDirectory.getValue();
    }

    public static File getLogDirectory() {
        return logDirectory.getValue();
    }

    public static File getSettingsDirectory() {
        return settingsDirectory.getValue();
    }

    public static File getSettingsFile() {
        return settingsFile.getValue();
    }

    public static File getCacheDirectory() {
        return cacheDirectory.getValue();
    }
}
