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
public interface UnitEventListener {

    public int unitCaptured(Unit unit, int oldTeamId, int newTeamId);

    public int unitCreated(Unit unit, Unit builder);

    public int unitFinished(Unit unit);

    public int unitGiven(Unit unit, int oldTeamId, int newTeamId);
}
