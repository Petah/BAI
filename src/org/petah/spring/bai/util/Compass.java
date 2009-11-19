/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.util;

/**
 *
 * @author Petah
 */
public enum Compass {

    North, NorthEast, East, SouthEast, South, SouthWest, West, NorthWest;

    public static Compass fromAngle(float degrees) {
        Compass result;
        if (degrees < 22.5f) {
            result = Compass.East;
        } else if (degrees < 67.5f) {
            result = Compass.SouthEast;
        } else if (degrees < 112.5f) {
            result = Compass.South;
        } else if (degrees < 157.5f) {
            result = Compass.SouthWest;
        } else if (degrees < 202.5f) {
            result = Compass.West;
        } else if (degrees < 247.5f) {
            result = Compass.NorthWest;
        } else if (degrees < 292.5f) {
            result = Compass.North;
        } else if (degrees < 337.5f) {
            result = Compass.NorthEast;
        } else {
            result = Compass.East;
        }
        return result;
    }
}
