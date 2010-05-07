package org.petah.spring.bai;

import com.springrts.ai.oo.OOAI;
import com.springrts.ai.oo.OOAICallback;
import com.springrts.ai.oo.OOAIFactory;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.petah.common.util.profiler.Profiler;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.delegate.AIEventHandler;
import org.petah.spring.bai.delegate.GlobalDelegate;
import org.petah.spring.bai.gui.GUIManager;
import org.petah.spring.bai.listener.adapter.AIEventAdapter;
import org.petah.spring.bai.unit.UnitInfo;
import org.petah.spring.bai.util.CustomOptionIO;

/**
 *
 * @author Petah
 */
public class BAIFactory extends OOAIFactory {

    private static Map<Integer, AIDelegate> aiDelegates = new ConcurrentHashMap<Integer, AIDelegate>();
    private static boolean globalInit = false;

    public BAIFactory() {
        try {
            System.setOut(new PrintStream(new FileOutputStream("java.lang.System.out.txt")));
            System.setErr(new PrintStream(new FileOutputStream("java.lang.System.err.txt")));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BAIFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public OOAI createAI(final int teamId, OOAICallback callback) {
        // Check if this is the first call
        if (!globalInit) {
            globalInit = true;
            init(callback);
        }

        Profiler.start(BAIFactory.class, "createAI()");

        AIEventHandler eventHandler = new AIEventHandler();
        eventHandler.addAIEventListener(new AIEventAdapter() {

            @Override
            public int release(int reason) {
                aiDelegates.remove(teamId);
                if (aiDelegates.size() == 0) {
                    shutDown();
                }
                return AIReturnCode.NORMAL;
            }
        });
        AIDelegate delegate = new AIDelegate(teamId, callback, eventHandler);
        aiDelegates.put(teamId, delegate);

        Profiler.stop(BAIFactory.class, "createAI()");

        return eventHandler;
    }

    public static void init(OOAICallback callback) {
        Profiler.start(BAIFactory.class, "init()");

        // Initialise global options and information logger
        GlobalOptions.parseLua(callback);
        InformationLogger.init();

        // Parse the resources
        ResourceManager.parseResources(callback);

        // Initialise other classes in order of dependance
        GlobalDelegate.init(callback); // Dependant on InformationLogger, ResourceManager
        GUIManager.init(); // Dependant on GlobalDelegate
        UnitInfo.init(); // Dependant on CacheManager

        Profiler.stop(BAIFactory.class, "init()");
    }

    public static void shutDown() {
        CustomOptionIO.save(InformationLogger.getSettingsFile());
        ThreadManager.shutDown();
        GUIManager.shutDown();
    }
}
