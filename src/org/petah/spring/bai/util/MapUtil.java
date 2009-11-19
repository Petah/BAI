/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.util;

/**
 *
 * @author Petah
 */
public class MapUtil {

    public static int mapToSlope(int pos) {
        return pos / 2;
    }

    public static int mapToTerrain(int pos) {
        return pos * 8;
    }

    public static int terrainToMap(int pos) {
        return pos / 8;
    }

    public static float mapToTerrain(float pos) {
        return pos * 8;
    }

    public static float terrainToMap(float pos) {
        return pos / 8;
    }

    public static int metalToMap(int pos) {
        return pos * 2;
    }

    public static int metalToTerrain(int pos) {
        return (int) mapToTerrain(metalToMap(pos));
    }

    public static int mapToMetal(int pos) {
        return pos / 2;
    }

    public static int terrainToMetal(int pos) {
        return (int) terrainToMap(mapToMetal(pos));
    }
}
