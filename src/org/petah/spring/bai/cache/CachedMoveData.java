/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.cache;

import com.springrts.ai.oo.clb.MoveData;
import java.io.Serializable;

/**
 *
 * @author Petah
 */
public class CachedMoveData implements Serializable, Comparable<CachedMoveData> {

    private transient MoveData moveData;
    private String name;
//    private float maxAcceleration;
//    private float maxBreaking;
//    private float maxSpeed;
//    private short maxTurnRate;
    private int xSize;
    private int zSize;
    private float depth;
    private float maxSlope;
    private float slopeMod;
    private float depthMod;
    private int pathType;
    private float crushStrength;
    private int moveType;
    private int moveFamily;
    private int terrainClass;
    private boolean followGround;
    private boolean subMarine;

    public CachedMoveData(MoveData moveData) {
        this.moveData = moveData;
        name = moveData.getName();
//        maxAcceleration = moveData.getMaxAcceleration();
//        maxBreaking = moveData.getMaxBreaking();
//        maxSpeed = moveData.getMaxSpeed();
//        maxTurnRate = moveData.getMaxTurnRate();
        xSize = moveData.getXSize();
        zSize = moveData.getZSize();
        depth = moveData.getDepth();
        maxSlope = moveData.getMaxSlope();
        slopeMod = moveData.getSlopeMod();
        depthMod = moveData.getDepth();
        pathType = moveData.getPathType();
        crushStrength = moveData.getCrushStrength();
        moveType = moveData.getMoveType();
        moveFamily = moveData.getMoveFamily();
        terrainClass = moveData.getTerrainClass();
        followGround = moveData.getFollowGround();
        subMarine = moveData.isSubMarine();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CachedMoveData other = (CachedMoveData) obj;
        if (this.moveData != other.moveData && (this.moveData == null || !this.moveData.equals(other.moveData))) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.xSize != other.xSize) {
            return false;
        }
        if (this.zSize != other.zSize) {
            return false;
        }
        if (Float.floatToIntBits(this.depth) != Float.floatToIntBits(other.depth)) {
            return false;
        }
        if (Float.floatToIntBits(this.maxSlope) != Float.floatToIntBits(other.maxSlope)) {
            return false;
        }
        if (Float.floatToIntBits(this.slopeMod) != Float.floatToIntBits(other.slopeMod)) {
            return false;
        }
        if (Float.floatToIntBits(this.depthMod) != Float.floatToIntBits(other.depthMod)) {
            return false;
        }
        if (this.pathType != other.pathType) {
            return false;
        }
        if (Float.floatToIntBits(this.crushStrength) != Float.floatToIntBits(other.crushStrength)) {
            return false;
        }
        if (this.moveType != other.moveType) {
            return false;
        }
        if (this.moveFamily != other.moveFamily) {
            return false;
        }
        if (this.terrainClass != other.terrainClass) {
            return false;
        }
        if (this.followGround != other.followGround) {
            return false;
        }
        if (this.subMarine != other.subMarine) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (this.moveData != null ? this.moveData.hashCode() : 0);
        hash = 13 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 13 * hash + this.xSize;
        hash = 13 * hash + this.zSize;
        hash = 13 * hash + Float.floatToIntBits(this.depth);
        hash = 13 * hash + Float.floatToIntBits(this.maxSlope);
        hash = 13 * hash + Float.floatToIntBits(this.slopeMod);
        hash = 13 * hash + Float.floatToIntBits(this.depthMod);
        hash = 13 * hash + this.pathType;
        hash = 13 * hash + Float.floatToIntBits(this.crushStrength);
        hash = 13 * hash + this.moveType;
        hash = 13 * hash + this.moveFamily;
        hash = 13 * hash + this.terrainClass;
        hash = 13 * hash + (this.followGround ? 1 : 0);
        hash = 13 * hash + (this.subMarine ? 1 : 0);
        return hash;
    }

    

    public int compareTo(CachedMoveData other) {
        return name.compareTo(other.name);
    }

    public float getCrushStrength() {
        return crushStrength;
    }

    public float getDepth() {
        return depth;
    }

    public float getDepthMod() {
        return depthMod;
    }

    public boolean isFollowGround() {
        return followGround;
    }

//    public float getMaxAcceleration() {
//        return maxAcceleration;
//    }
//
//    public float getMaxBreaking() {
//        return maxBreaking;
//    }

    public float getMaxSlope() {
        return maxSlope;
    }

//    public float getMaxSpeed() {
//        return maxSpeed;
//    }
//
//    public short getMaxTurnRate() {
//        return maxTurnRate;
//    }

    public int getXSize() {
        return xSize;
    }

    public int getZSize() {
        return zSize;
    }

    public MoveData getMoveData() {
        return moveData;
    }

    public int getMoveFamily() {
        return moveFamily;
    }

    public int getMoveType() {
        return moveType;
    }

    public String getName() {
        return name;
    }

    public int getPathType() {
        return pathType;
    }

    public float getSlopeMod() {
        return slopeMod;
    }

    public boolean isSubMarine() {
        return subMarine;
    }

    public int getTerrainClass() {
        return terrainClass;
    }
}
