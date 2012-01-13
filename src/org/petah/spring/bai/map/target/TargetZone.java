/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.map.target;

import com.springrts.ai.oo.AIFloat3;
import java.awt.Point;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.common.util.GameMath;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.util.AIUtil;

/**
 *
 * @author Petah
 */
public class TargetZone {

    // Target zone options
    private static Option<Integer> maxSize = OptionsManager.getOption(
            new Option<Integer>("TargetZone.maxSize", 1000));
    // Class properties
    private TargetType targetType;
    private List<CachedUnit> units = new CopyOnWriteArrayList<CachedUnit>();

    public TargetZone(TargetType targetType) {
        this.targetType = targetType;
    }

    public int getRadius() {
        AIFloat3 center = getPosition();
        float radius = 0;
        for (CachedUnit unit : units) {
            AIFloat3 pos = unit.getPos();
            float distance = GameMath.pointDistance(center.x, center.z, pos.x, pos.z);
            if (distance > radius) {
                radius = distance;
            }
        }
        return (int) radius;
    }

    public AIFloat3 getPosition() {
        return AIUtil.getCenterPosition(units);
    }

    public List<CachedUnit> getUnits() {
        return units;
    }

    public void addUnit(CachedUnit unit) {
        units.add(unit);
    }

    public TargetType getTargetType() {
        return targetType;
    }
}
