/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.listener;

import com.springrts.ai.AIFloat3;

/**
 *
 * @author davnei06
 */
public interface SeismicPingEventListener {

    public int seismicPing(AIFloat3 pos, float strength);
}
