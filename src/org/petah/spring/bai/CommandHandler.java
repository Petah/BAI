package org.petah.spring.bai;

import org.petah.spring.bai.listener.MessageEventListener;
import org.petah.spring.bai.log.Log;

/**
 *
 * @author David Neilsen
 */
public class CommandHandler implements MessageEventListener {

    private BAI ai;

    public CommandHandler(BAI ai) {
        Log.entry(CommandHandler.class, "CommandHandler");
        this.ai = ai;
        ai.addEventListener("message", this);
    }

    public void message(Integer player, String message) {
        Log.entry(CommandHandler.class, "message");
    }
    
}
