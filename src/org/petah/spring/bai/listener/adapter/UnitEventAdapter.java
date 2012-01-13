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

    public int unitCaptured(Unit unit, int oldTeamId, int newTeamId) {
        return AIReturnCode.NORMAL;
    }

    public int unitCreated(Unit unit, Unit builder) {
        return AIReturnCode.NORMAL;
    }

    public int unitFinished(Unit unit) {
        return AIReturnCode.NORMAL;
    }

    public int unitGiven(Unit unit, int oldTeamId, int newTeamId) {
        return AIReturnCode.NORMAL;
    }
}
