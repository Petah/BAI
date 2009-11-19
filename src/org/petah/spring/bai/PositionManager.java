/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai;

import com.springrts.ai.AIFloat3;
import com.springrts.ai.oo.Unit;
import org.petah.common.util.GameMath;
import org.petah.spring.bai.cache.CachedMap;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.delegate.GlobalDelegate;
import org.petah.spring.bai.listener.adapter.UnitEventAdapter;
import org.petah.spring.bai.util.Compass;
import org.petah.spring.bai.util.MapUtil;

/**
 *
 * @author davnei06
 */
public class PositionManager extends UnitEventAdapter {

    private AIDelegate aiDelegate;
    private AIFloat3 baseCenter;
    private float startDirection;
    private Compass startSide;

    public PositionManager(AIDelegate aiDelegate) {
        this.aiDelegate = aiDelegate;
        aiDelegate.getAIEventHandler().addUnitEventListener(this);
    }

    // Implemented methods
    @Override
    public int unitFinished(Unit unit) {
        AIFloat3 pos = unit.getPos();
        baseCenter = pos;
        startDirection = GameMath.pointDirection(pos.x, pos.y,
                MapUtil.mapToTerrain(GlobalDelegate.getMapWidth() / 2),
                MapUtil.mapToTerrain(GlobalDelegate.getMapHeight() / 2));
        startSide = Compass.fromAngle(startDirection);
        aiDelegate.getAIEventHandler().removeUnitEventListener(this);
        return AIReturnCode.NORMAL;
    }

    // Getters
    public AIFloat3 getBaseCenter() {
        return baseCenter;
    }

    public float getStartDirection() {
        return startDirection;
    }

    public Compass getStartSide() {
        return startSide;
    }
}
