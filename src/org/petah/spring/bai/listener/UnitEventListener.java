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
public interface UnitEventListener {

    public void unitCaptured(Unit unit, int oldTeamId, int newTeamId);

    public void unitCreated(Unit unit, Unit builder);

    public void unitFinished(Unit unit);

    public void unitGiven(Unit unit, int oldTeamId, int newTeamId);
}
