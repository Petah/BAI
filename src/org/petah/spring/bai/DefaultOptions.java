/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai;

import com.springrts.ai.oo.Info;
import com.springrts.ai.oo.OOAICallback;
import com.springrts.ai.oo.OptionValues;
import java.awt.Font;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;

/**
 *
 * @author Petah
 */
public class DefaultOptions {

    private static boolean ready = false;
    // Files
    public static Option<File> errorLogFile;
    public static Option<File> outputLogFile;
    // Directorys
    public static Option<File> aiDirectory;
    public static Option<File> logDirectory;
    public static Option<File> logArchiveDirectory;
    public static Option<File> settingsDirectory;
    // Version 
    public static Option<String> version = OptionsManager.getOption(
            new Option<String>("version", "0.1"));
    // Output filters
    public static Option<Boolean> debug = OptionsManager.getOption(
            new Option<Boolean>("debug", true));
    public static Option<Boolean> guiEnabled = OptionsManager.getOption(
            new Option<Boolean>("gui", true));
    public static Option<Boolean> logToFile = OptionsManager.getOption(
            new Option<Boolean>("logToFile", true));
    public static Option<Level> loggerLevel = OptionsManager.getOption(
            new Option<Level>("logger", Level.ALL));
    public static Option<Boolean> profiler = OptionsManager.getOption(
            new Option<Boolean>("profiler", true));
    public static Option<Long> profilerMinimumNanos = OptionsManager.getOption(
            new Option<Long>("profilerMinimumNanos", 25000000l));
    // Formatting
    public static Option<String> logTimeStampFormat = OptionsManager.getOption(
            new Option<String>("logTimeStampFormat", "HH-mm-ss__MMM-d-yyyy"));
    // Commands
    public static Option<Integer> commandTimeout = OptionsManager.getOption(
            new Option<Integer>("commandTimeout", 3000));
    // Control map/zone
    public static Option<Integer> controlZoneSize = OptionsManager.getOption(
            new Option<Integer>("controlZoneSize", 128));
    public static Option<Integer> controlResetTime = OptionsManager.getOption(
            new Option<Integer>("controlResetTime", 75));
    public static Option<Float> controlZoneRenderMaxPower = OptionsManager.getOption(
            new Option<Float>("controlZoneRenderMaxPower", 1000f));
    public static Option<Font> controlZoneRenderFont = OptionsManager.getOption(
            new Option<Font>("controlZoneRenderFont", new Font("Arial", Font.BOLD, 12)));
    public static Option<Integer> controlMapUpdateTime = OptionsManager.getOption(
            new Option<Integer>("controlMapUpdateTime", 35));
    // Target map/zone
    public static Option<Integer> targetMapUpdateTime = OptionsManager.getOption(
            new Option<Integer>("targetMapUpdateTime", 45));
    public static Option<Integer> targetZoneMaxSize = OptionsManager.getOption(
            new Option<Integer>("targetZoneMaxSize", 1000));
    // Metal map
    public static Option<Integer> metalMapUpdateTime = OptionsManager.getOption(
            new Option<Integer>("metalMapUpdateTime", 250));
    public static Option<Integer> metalSpotJiggleAmount = OptionsManager.getOption(
            new Option<Integer>("metalSpotJiggleAmount", 20));
    public static Option<Float> metalSpotSpacingFactor = OptionsManager.getOption(
            new Option<Float>("metalSpotSpacingFactor", 0.4f));
    // Task update times
    public static Option<Integer> scoutUpdateTime = OptionsManager.getOption(
            new Option<Integer>("scoutUpdateTime", 250));
    public static Option<Integer> raidUpdateTime = OptionsManager.getOption(
            new Option<Integer>("raidUpdateTime", 150));
    public static Option<Integer> bomberUpdateTime = OptionsManager.getOption(
            new Option<Integer>("bomberUpdateTime", 300));
    public static Option<Integer> defenceUpdateTime = OptionsManager.getOption(
            new Option<Integer>("defenceUpdateTime", 20));
    public static Option<Integer> builderUpdateTime = OptionsManager.getOption(
            new Option<Integer>("builderUpdateTime", 20));
    public static Option<Integer> resourceUpdateTime = OptionsManager.getOption(
            new Option<Integer>("resourceUpdateTime", 30));
    public static Option<Integer> nanoUpdateTime = OptionsManager.getOption(
            new Option<Integer>("nanoUpdateTime", 150));
    public static Option<Integer> metalMakerUpdateTime = OptionsManager.getOption(
            new Option<Integer>("metalMakerUpdateTime", 30));
    // Resource share levels
    public static Option<Integer> resourceEnergyShareLevel = OptionsManager.getOption(
            new Option<Integer>("resourceEnergyShareLevel", 700));
    public static Option<Integer> resourceMetalShareLevel = OptionsManager.getOption(
            new Option<Integer>("resourceMetalShareLevel", 400));
    // Graphics
    public static Option<Boolean> antiAlias = OptionsManager.getOption(
            new Option<Boolean>("antiAlias", true));
//    public static Option<Integer> energyBuildingSpacing = OptionsManager.getOption(
//            new Option<Integer>("energyBuildingSpacing", 6));
//    public static Option<Integer> buildingSearchRadius = OptionsManager.getOption(
//            new Option<Integer>("buildingSearchRadius", 1000));

    static {
        setDirectorys();
        setFiles();
    }

    private static void setDirectorys() {
        File jarFile = new File(BAI.class.getProtectionDomain().getCodeSource().getLocation().getFile());
        if (jarFile.isDirectory()) {
            aiDirectory = OptionsManager.getOption(new Option<File>("aiDirectory", jarFile));
        } else {
            aiDirectory = OptionsManager.getOption(new Option<File>("aiDirectory", jarFile.getParentFile()));
        }
        logDirectory = OptionsManager.getOption(
                new Option<File>("logDirectory", new File(aiDirectory.getValue().getPath() + File.separator + "logs")));
        logArchiveDirectory = OptionsManager.getOption(
                new Option<File>("logArchiveDirectory", new File(logDirectory.getValue().getPath() + File.separator + "archive")));
        settingsDirectory = OptionsManager.getOption(
                new Option<File>("settingsDirectory", new File(aiDirectory.getValue().getPath() + File.separator + "settings")));
        logDirectory.getValue().mkdirs();
        logArchiveDirectory.getValue().mkdirs();
        settingsDirectory.getValue().mkdirs();
    }

    private static void setFiles() {
        String dir = DefaultOptions.logDirectory.getValue().getAbsolutePath() + File.separator;
        SimpleDateFormat formatter = new SimpleDateFormat(DefaultOptions.logTimeStampFormat.getValue());
        DefaultOptions.outputLogFile = OptionsManager.getOption(
                new Option<File>("outputLogFile", new File(dir + "output_" + formatter.format(new Date()) + ".txt")));
        DefaultOptions.errorLogFile = OptionsManager.getOption(
                new Option<File>("errorLogFile", new File(dir + "errorlog_" + formatter.format(new Date()) + ".txt")));
    }

    public static synchronized void parseLua(OOAICallback callback) {
        if (!ready) {
            Info info = callback.getSkirmishAI().getInfo();
            for (int i = 0; i < info.getSize(); i++) {
                String name = info.getKey(i);
                String value = info.getValue(i);
                OptionsManager.setOption(new Option<String>(name, value));
                Logger.getLogger(DefaultOptions.class.getName()).info("Set AI info: " + name + " = " + value);
            }

            OptionValues options = callback.getSkirmishAI().getOptionValues();
            for (int i = 0; i < options.getSize(); i++) {
                String name = options.getKey(i);
                String value = options.getValue(i);
                OptionsManager.setOption(new Option<String>(name, value));
                Logger.getLogger(DefaultOptions.class.getName()).info("Set option: " + name + " = " + value);
            }
            ready = true;
        }
    }
}
