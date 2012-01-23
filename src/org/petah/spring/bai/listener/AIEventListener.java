/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.listener;

import com.springrts.ai.oo.clb.OOAICallback;

/**
 *
 * @author davnei06
 */
public interface AIEventListener {

    public void init(int teamId, OOAICallback callback);

    public void release(int reason);
}
