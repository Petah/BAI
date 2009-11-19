/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.listener;

import com.springrts.ai.oo.Unit;

/**
 *
 * @author davnei06
 */
public interface MoveEventListener {

    public int unitIdle(Unit unit);

    public int unitMoveFailed(Unit unit);
}
