package org.petah.spring.bai;

import com.springrts.ai.oo.OOAI;
import com.springrts.ai.oo.clb.OOAICallback;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.petah.spring.bai.listener.EventListener;
import org.petah.spring.bai.log.Log;

/**
 *
 * @author David Neilsen
 */
public class EventHandler extends OOAI {

    private ArrayList<BAI> ais = new ArrayList<BAI>();
    private ConcurrentHashMap<String, CopyOnWriteArrayList<EventListener>> eventListeners = 
            new ConcurrentHashMap<String, CopyOnWriteArrayList<EventListener>>();

    public EventHandler() {
        Log.entry(EventHandler.class, "BAI");
    }
    
    public void addEventListener(String event, EventListener listener) {
        Log.entry(EventHandler.class, "addEventListener");
        // Get the list of event listeners
        CopyOnWriteArrayList<EventListener> listeners = eventListeners.get(event);
        
        // Check the list exists, and create a new one if it doesn't
        if (listeners == null) {
            listeners = new CopyOnWriteArrayList<EventListener>();
            eventListeners.put(event, listeners);
        }
        
        // Add the new event listener
        listeners.add(listener);
    }
    
    public int handleEvent(String type, Object... options) {
        Log.entry(EventHandler.class, "handleEvent");
        // Get the list of event listeners
        Collection listeners = eventListeners.get(type);
        
        // If there is no event listeners, return
        if (listeners == null) {
            return AIReturnCode.NORMAL;
        }
        
        // Get the list of classes used for the method parameters
        Class[] paramterClasses = new Class[options.length];
        int i = 0;
        for (Object o : options) {
            paramterClasses[i++] = o.getClass();
        }
        
        // Iterate the event listeners
        for (Object listener : listeners) {
            try {
                // Find the event method
                Method event = listener.getClass().getMethod(type, paramterClasses);
                // Call the event
                event.invoke(listener, options);
            } catch (Exception ex) {
                // Ignore all exceptions
                Log.ignoredException(ex);
            }
        }
        
        return AIReturnCode.NORMAL;
    }

    double rand;
    @Override
    public int init(int id, OOAICallback callback) {
        Log.entry(EventHandler.class, "init");
        this.rand = Math.random();
        Log.debug(rand);
        ais.add(new BAI(id, callback, this));
        return AIReturnCode.NORMAL;
    }

    @Override
    public int update(int frame) {
        Log.entry(EventHandler.class, "update");
        return AIReturnCode.NORMAL;
    }

    @Override
    public int message(int player, String message) {
        Log.entry(EventHandler.class, "message");
        Log.debug(rand);
        handleEvent("message", player, message);
        return AIReturnCode.NORMAL;
    }

    @Override
    public int release(int reason) {
        Log.entry(EventHandler.class, "release");
        return AIReturnCode.NORMAL;
    }
    
}
