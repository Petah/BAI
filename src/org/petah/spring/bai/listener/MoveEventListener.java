/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.listener;

import com.springrts.ai.oo.clb.Unit;

/**
 *
 * @author davnei06
 */
public interface MoveEventListener {

    public void unitIdle(Unit unit);

    public void unitMoveFailed(Unit unit);
}
