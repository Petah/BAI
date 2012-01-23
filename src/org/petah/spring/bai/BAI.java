package org.petah.spring.bai;

import com.springrts.ai.oo.clb.OOAICallback;
import org.petah.spring.bai.listener.EventListener;
import org.petah.spring.bai.log.Log;

/**
 *
 * @author David Neilsen
 */
public class BAI {
    
    private int id;
    private OOAICallback callback;
    private EventHandler eventHandler;
    private CommandHandler commandHandler;

    public BAI(int id, OOAICallback callback, EventHandler eventHandler) {
        Log.entry(BAI.class, "BAI");
        this.id = id;
        this.callback = callback;
        this.eventHandler = eventHandler;
        this.commandHandler = new CommandHandler(this);
    }

    public void addEventListener(String event, EventListener listener) {
        Log.entry(BAI.class, "addEventListener");
        eventHandler.addEventListener(event, listener);
    }
    
}
