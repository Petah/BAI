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

    public int init(int teamId, OOAICallback callback);

    public int release(int reason);
}
