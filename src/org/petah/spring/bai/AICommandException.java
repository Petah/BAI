/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai;

import com.springrts.ai.oo.clb.Command;
import org.petah.spring.bai.delegate.AIDelegate;

/**
 *
 * @author Petah
 */
public class AICommandException extends RuntimeException {

    private AIDelegate aiDelegate;
    private Command aiCommand;
    private int errorCode;

    public AICommandException(AIDelegate aiDelegate, Command aiCommand, int errorCode) {
        super(aiDelegate.getPrefix() + "An error occured when handling an AI command: " +
                aiCommand.getClass().getSimpleName() + " Error: " + errorCode);
        this.aiDelegate = aiDelegate;
        this.aiCommand = aiCommand;
        this.errorCode = errorCode;
    }

    public AICommandException(String message, AIDelegate aiDelegate, Command aiCommand, int errorCode) {
        super(message);
        this.aiDelegate = aiDelegate;
        this.aiCommand = aiCommand;
        this.errorCode = errorCode;
    }

    public Command getAiCommand() {
        return aiCommand;
    }

    public AIDelegate getAiDelegate() {
        return aiDelegate;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
