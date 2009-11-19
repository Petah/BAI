/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.map.control;

import java.awt.Font;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.spring.bai.util.MapUtil;

/**
 *
 * @author Petah
 */
public class ControlZone {

    // Control zone options
    private static Option<Integer> size = OptionsManager.getOption(
            new Option<Integer>("ControlZone.size", 128));
    private static Option<Integer> resetTime = OptionsManager.getOption(
            new Option<Integer>("ControlZone.resetTime", 75));
    // Class properties
    private int x;
    private int y;
    private float power = 0;
    private int age = 0;
    private float residualPower = 0;

    public ControlZone(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void reset() {
        if (power != 0) {
            residualPower = power;
            age = resetTime.getValue();
        } else {
            age--;
            if (age <= 0) {
                residualPower = 0;
            }
        }
        power = 0;
    }

    public void incPower(float power) {
        this.power += power;
    }

    public void decPower(float power) {
        this.power -= power;
    }

    public boolean isEnemy() {
        return getPower() < 0;
    }

    public boolean isFriendly() {
        return getPower() > 0;
    }

    public boolean isNeutral() {
        return getPower() == 0;
    }

    public float getTerrainX() {
        return MapUtil.mapToTerrain(getMapX());
    }

    public float getTerrainZ() {
        return MapUtil.mapToTerrain(getMapY());
    }

    public float getTerrainCenterX() {
        return MapUtil.mapToTerrain(getMapCenterX());
    }

    public float getTerrainCenterZ() {
        return MapUtil.mapToTerrain(getMapCenterY());
    }

    public float getMapX() {
        return getX() * size.getValue();
    }

    public float getMapY() {
        return getY() * size.getValue();
    }

    public float getMapCenterX() {
        float zoneSize = size.getValue();
        return getMapX() + zoneSize / 2;
    }

    public float getMapCenterY() {
        float zoneSize = size.getValue();
        return getMapY() + zoneSize / 2;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float getPower() {
        if (power == 0) {
            return residualPower;
        }
        return power;
    }

    public int getAge() {
        return age;
    }

    public static int getSize() {
        return size.getValue();
    }
}
