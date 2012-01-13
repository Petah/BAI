package org.petah.spring.bai.map.metal;

import com.springrts.ai.oo.AIFloat3;
import org.petah.spring.bai.cache.CachedMetalMap;
import java.util.Iterator;
import java.util.LinkedList;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.common.util.GameMath;
import org.petah.spring.bai.cache.CachedUnit;

/**
 *
 * @author Petah
 */
public class MetalFinder {

    // Options
    private static Option<Integer> metalSpotJiggleAmount = OptionsManager.getOption(
            new Option<Integer>("metalSpotJiggleAmount", 20));
    private static Option<Float> metalSpotSpacingFactor = OptionsManager.getOption(
            new Option<Float>("metalSpotSpacingFactor", 0.4f));

    public static LinkedList<MetalSpot> getMetalSpots(CachedMetalMap cachedMetalMap) {
        LinkedList<MetalSpot> metalSpots = getAllMetalSpots(cachedMetalMap);
        Iterator<MetalSpot> i;

        int totalValue = 0;
        for (MetalSpot metalSpot : metalSpots) {
            totalValue += metalSpot.getValue();
        }

        int average = totalValue / metalSpots.size();
        average /= 2;
        i = metalSpots.iterator();
        while (i.hasNext()) {
            MetalSpot metalSpot = i.next();
            if (metalSpot.getValue() < average) {
                i.remove();
            }
        }

        if (metalSpots.size() < 1000) {
            i = metalSpots.iterator();
            while (i.hasNext()) {
                MetalSpot outer = i.next();
                Iterator<MetalSpot> j = metalSpots.iterator();
                while (j.hasNext()) {
                    MetalSpot inner = j.next();
                    if (outer == inner) {
                        continue;
                    }
                    double distance = GameMath.pointDistance(outer.getX(), outer.getY(), inner.getX(), inner.getY());
                    if (outer.getValue() <= inner.getValue() &&
                            distance <= cachedMetalMap.getMetalExtractorRadius() * 2) {
                        i.remove();
                        break;
                    }
                }
            }
        }

        // Find center of metal spot
        for (MetalSpot metalSpot : metalSpots) {
            int highestX = Integer.MIN_VALUE;
            int highestY = Integer.MIN_VALUE;
            int lowestX = Integer.MAX_VALUE;
            int lowestY = Integer.MAX_VALUE;
            for (int x = 0; x < cachedMetalMap.getMetalExtractorRadius()*2; x++) {
                for (int y = 0; y < cachedMetalMap.getMetalExtractorRadius()*2; y++) {
                    int realX = (int) (x + metalSpot.getX() - cachedMetalMap.getMetalExtractorRadius());
                    int realY = (int) (y + metalSpot.getY() - cachedMetalMap.getMetalExtractorRadius());
                    float distance = GameMath.pointDistance(realX, realY, metalSpot.getX(), metalSpot.getY());
//                    System.err.println(distance + "\t<=\t" + cachedMetalMap.getMetalExtractorRadius() + "\t&&\t" +
//                            cachedMetalMap.getValue(realX, realY) + "\t>\t" + cachedMetalMap.getAverageMetal() +
//                            "\t::" + realX + "," + realY);
                    if (distance <= cachedMetalMap.getMetalExtractorRadius() &&
                            cachedMetalMap.getValue(realX, realY) > cachedMetalMap.getAverageMetal()) {
                        if (realX < lowestX) {
                            lowestX = realX;
                        }
                        if (realY < lowestY) {
                            lowestY = realY;
                        }
                        if (realX > highestX) {
                            highestX = realX;
                        }
                        if (realY > highestY) {
                            highestY = realY;
                        }
                    }
                }
            }
            metalSpot.setX(lowestX + ((highestX - lowestX) / 2));
            metalSpot.setY(lowestY + ((highestY - lowestY) / 2));
        }
        // Jiggle metal spot
//        List<MetalSpot> newMetalSpots = new ArrayList<MetalSpot>();
//        i = metalSpots.iterator();
//        while (i.hasNext()) {
//            MetalSpot metalSpot = i.next();
//            MetalSpot bestMetalSpot = null;
//            for (int j = 0; j < metalSpotJiggleAmount.getValue(); j++) {
//                int x = metalSpot.getX();
//                int y = metalSpot.getY();
//                int jiggleSize = (int) (cachedMetalMap.getMetalExtractorRadius() * 2);
//                x += Math.random() * jiggleSize - jiggleSize / 2;
//                y += Math.random() * jiggleSize - jiggleSize / 2;
//                MetalSpot newMetalSpot = getMetalSpot(cachedMetalMap, x, y);
//                if (newMetalSpot != null) {
//                    if (bestMetalSpot != null) {
//                        if (newMetalSpot.getValue() >= bestMetalSpot.getValue()) {
//                            bestMetalSpot = newMetalSpot;
//                        }
//                    } else if (newMetalSpot.getValue() >= metalSpot.getValue()) {
//                        bestMetalSpot = newMetalSpot;
//                    }
//                }
//            }
//            if (bestMetalSpot != null) {
//                i.remove();
//                newMetalSpots.add(bestMetalSpot);
//            }
//        }
//        metalSpots.addAll(newMetalSpots);

        return metalSpots;
    }

    public static LinkedList<MetalZone> getMetalZones(CachedMetalMap cachedMetalMap) {
        LinkedList<MetalZone> metalZones = new LinkedList<MetalZone>();
        // TODO: improve metal zone finder
//        int zoneWidth = cachedMetalMap.getWidth() / 30;
//        int zoneHeight = cachedMetalMap.getHeight() / 30;
//        zoneWidth = Math.max(zoneWidth, zoneHeight);
//        zoneHeight = Math.max(zoneWidth, zoneHeight);
//        for (int y = 0; y < cachedMetalMap.getHeight(); y += zoneHeight) {
//            for (int x = 0; x < cachedMetalMap.getWidth(); x += zoneWidth) {
//                MetalZone metalZone = new MetalZone(x, y, zoneWidth, zoneHeight);
//                if (metalZone.getAverageMetal() > cachedMetalMap.getAverageMetal()) {
//                    metalZones.add(metalZone);
//                }
//            }
//        }
        return metalZones;
    }

    private static LinkedList<MetalSpot> getAllMetalSpots(CachedMetalMap cachedMetalMap) {
        LinkedList<MetalSpot> metalSpots = new LinkedList<MetalSpot>();
        boolean stagger = true;
        float factor = metalSpotSpacingFactor.getValue();
        for (float y = cachedMetalMap.getMetalExtractorRadius(); y < cachedMetalMap.getHeight(); y += cachedMetalMap.getMetalExtractorRadius() * factor) {
            stagger = !stagger;
            for (float x = cachedMetalMap.getMetalExtractorRadius() + (stagger ? cachedMetalMap.getMetalExtractorRadius() * factor : 0);
                    x < cachedMetalMap.getWidth();
                    x += cachedMetalMap.getMetalExtractorRadius() * (1 + factor)) {
//        for (int y = radius / 2; y < height; y += radius) {
//            for (int x = radius / 2; x < width; x += radius) {
                MetalSpot metalSpot = getMetalSpot(cachedMetalMap, (int) x, (int) y);
                if (metalSpot != null) {
                    metalSpots.add(metalSpot);
                }
            }
        }
        return metalSpots;
    }

    private static int getMetalSpotValue(CachedMetalMap cachedMetalMap, int x, int y) {
        int value = 0;
        for (int h = (int) -cachedMetalMap.getMetalExtractorRadius(); h < cachedMetalMap.getMetalExtractorRadius(); h++) {
            for (int w = (int) -cachedMetalMap.getMetalExtractorRadius(); w < cachedMetalMap.getMetalExtractorRadius(); w++) {
                try {
                    value += cachedMetalMap.getValue(x + w, y + h);
                } catch (Exception e) {
                    //Catch when outside map bounds and presume 0
                }
            }
        }
        return value;
    }

    private static MetalSpot getMetalSpot(CachedMetalMap cachedMetalMap, int x, int y) {
        int value = getMetalSpotValue(cachedMetalMap, x, y);
        if (value > 0) {
            return new MetalSpot(x, y, value);
        }
        return null;
    }
}
