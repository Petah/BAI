package org.petah.spring.bai;

import com.springrts.ai.oo.clb.OOAICallback;
import org.petah.common.util.profiler.Profiler;
import org.petah.spring.bai.delegate.GlobalDelegate;
import org.petah.spring.bai.gui.GUIManager;
import org.petah.spring.bai.unit.UnitInfo;

public class Global {

    private static boolean inited = false;

    public static void init(OOAICallback callback) {
        if (inited) {
            return;
        }
        inited = true;

        Profiler.start(Global.class, "init()");

        // Initialise global options and information logger
        GlobalOptions.parseLua(callback);
        InformationLogger.init();

        // Parse the resources
        ResourceManager.parseResources(callback);

        // Initialise other classes in order of dependance
        GlobalDelegate.init(callback); // Dependant on InformationLogger, ResourceManager
        GUIManager.init(); // Dependant on GlobalDelegate
        UnitInfo.init(); // Dependant on CacheManager

        Profiler.stop(Global.class, "init()");
    }

}
