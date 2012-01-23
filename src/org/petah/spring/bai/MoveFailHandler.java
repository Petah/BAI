/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai;

import com.springrts.ai.oo.AIFloat3;
import com.springrts.ai.oo.clb.Unit;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.listener.MoveEventListener;
import org.petah.spring.bai.util.CommandUtil;

/**
 *
 * @author Petah
 */
public class MoveFailHandler implements MoveEventListener {

    // Options
    private static Option<Integer> moveDistance = OptionsManager.getOption(
            new Option<Integer>("MoveFailHandler.moveDistance", 100));
    // Class properties
    private AIDelegate aiDelegate;

    public MoveFailHandler(AIDelegate aiDelegate) {
        this.aiDelegate = aiDelegate;
    }

    public void unitIdle(Unit unit) {
    }

    public void unitMoveFailed(Unit unit) {
        CachedUnit cachedUnit = aiDelegate.getCachedUnit(unit.getUnitId());
        AIFloat3 pos = cachedUnit.getPos();
        pos.x += Math.random() * moveDistance.getValue();
        pos.z += Math.random() * moveDistance.getValue();
        cachedUnit.moveTo(pos);
    }
}
