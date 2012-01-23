package org.petah.spring.bai;

import com.springrts.ai.oo.clb.Info;
import com.springrts.ai.oo.clb.OOAICallback;
import com.springrts.ai.oo.clb.OptionValues;
import java.io.File;
import java.util.logging.Logger;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.spring.bai.log.Log;
import org.petah.spring.bai.util.CustomOptionIO;

/**
 *
 * @author Petah
 */
public class GlobalOptions {

    private final static Logger LOG = Logger.getLogger(BAI.class.getName());

    // Test options IO
    public static void main(String[] args) {
        CustomOptionIO.save(new File("testsettings.xml"));
        System.out.println("Options saved to: " + new File("testsettings.xml").getAbsolutePath());
    }
    // Class properties
    private static boolean ready = false;
    // Options
    private static Option<Boolean> debug = OptionsManager.getOption(
            new Option<Boolean>("GlobalOptions.debug", true),
            new Option<String>("description", "Enable debug mode"),
            new Option<String>("values", "true, false"));
    // Version
    private static Option<String> version = OptionsManager.getOption(
            new Option<String>("GlobalOptions.version", "0.1"),
            new Option<Boolean>("transient", true));
    // Graphics
    private static Option<Boolean> antiAlias = OptionsManager.getOption(
            new Option<Boolean>("GlobalOptions.antiAlias", true),
            new Option<String>("description", "Enable anti aliasing when drawing images in the GUI"),
            new Option<String>("values", "true, false"));

    public static synchronized void parseLua(OOAICallback callback) {
        Log.entry(GlobalOptions.class, "parseLua");
        if (!ready) {
            Info info = callback.getSkirmishAI().getInfo();
            for (int i = 0; i < info.getSize(); i++) {
                String name = info.getKey(i);
                String value = info.getValue(i);
                OptionsManager.getOption(new Option<String>(name, value),
                        new Option<Boolean>("transient", true));
                LOG.info("Set AI info: " + name + " = " + value);
            }

            OptionValues options = callback.getSkirmishAI().getOptionValues();
            for (int i = 0; i < options.getSize(); i++) {
                String name = options.getKey(i);
                String value = options.getValue(i);
                OptionsManager.getOption(new Option<String>(name, value),
                        new Option<Boolean>("transient", true));
                LOG.info("Set option: " + name + " = " + value);
            }
            ready = true;
        }
    }

    public static boolean isDebug() {
        return debug.getValue();
    }

    public static boolean isAntiAlias() {
        return antiAlias.getValue();
    }
}
