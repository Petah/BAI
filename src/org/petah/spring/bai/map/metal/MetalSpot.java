package org.petah.spring.bai.map.metal;

import java.io.Serializable;

/**
 *
 * @author Petah
 */
public class MetalSpot implements Serializable {

    public static final long serialVersionUID = 1L;
    // Class variables
    private int x;
    private int y;
    private int value;

    public MetalSpot(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTerrainX() {
        return x * 2 * 8;
    }

    public int getTerrainZ() {
        return y * 2 * 8;
    }
}
