/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.listener.adapter;

import com.springrts.ai.oo.OOAICallback;
import org.petah.spring.bai.AIReturnCode;
import org.petah.spring.bai.listener.AIEventListener;

/**
 *
 * @author davnei06
 */
public class AIEventAdapter implements AIEventListener {

    public int init(int teamId, OOAICallback callback) {
        return AIReturnCode.NORMAL;
    }

    public int release(int reason) {
        return AIReturnCode.NORMAL;
    }
}
