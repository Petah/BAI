/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.listener.adapter;

import com.springrts.ai.oo.clb.Unit;
import org.petah.spring.bai.AIReturnCode;
import org.petah.spring.bai.listener.UnitEventListener;

/**
 *
 * @author davnei06
 */
public class UnitEventAdapter implements UnitEventListener {

    public void unitCaptured(Unit unit, int oldTeamId, int newTeamId) {
    }

    public void unitCreated(Unit unit, Unit builder) {
    }

    public void unitFinished(Unit unit) {
    }

    public void unitGiven(Unit unit, int oldTeamId, int newTeamId) {
    }
}
