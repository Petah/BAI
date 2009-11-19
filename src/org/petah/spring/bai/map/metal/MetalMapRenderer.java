/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.map.metal;

import org.petah.spring.bai.cache.CachedMetalMap;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import org.petah.common.util.profiler.Profiler;
import org.petah.spring.bai.GlobalOptions;

/**
 *
 * @author Petah
 */
public class MetalMapRenderer {

    public static void renderFullMetalMap(CachedMetalMap cachedMetalMap, MetalSpotManager metalSpotManager, Image image) {
        Profiler.start(MetalMapRenderer.class, "renderFullMetalMap()");
        Graphics2D g = (Graphics2D) image.getGraphics();
        renderMetalMap(cachedMetalMap, g);
        switch (cachedMetalMap.getMetalMapType()) {
            case Normal:
                MetalMapRenderer.renderMetalSpots(cachedMetalMap, metalSpotManager, g);
                break;
            case Metal:
                MetalMapRenderer.renderMetalZones(cachedMetalMap, metalSpotManager, g);
                break;
        }
        g.dispose();
        Profiler.stop(MetalMapRenderer.class, "renderFullMetalMap()");
    }

    public static void renderMetalMap(CachedMetalMap cachedMetalMap, Image image) {
        Graphics2D g = (Graphics2D) image.getGraphics();
        renderMetalMap(cachedMetalMap, g);
        g.dispose();
    }

    private static void renderMetalMap(CachedMetalMap cachedMetalMap, Graphics2D g) {
        for (int y = 0; y < cachedMetalMap.getHeight(); y++) {
            for (int x = 0; x < cachedMetalMap.getWidth(); x++) {
                g.setColor(new Color(0, cachedMetalMap.getValue(x, y), 0));
                g.drawLine(x, y, x, y);
            }
        }
    }

    public static void renderMetalSpots(CachedMetalMap cachedMetalMap, MetalSpotManager metalSpotManager, Image image) {
        Graphics2D g = (Graphics2D) image.getGraphics();
        renderMetalSpots(cachedMetalMap, metalSpotManager, g);
        g.dispose();
    }

    private static void renderMetalSpots(CachedMetalMap cachedMetalMap, MetalSpotManager metalSpotManager, Graphics2D g) {
        if (GlobalOptions.isAntiAlias()) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        for (MetalSpot metalSpot : metalSpotManager.getMetalSpots()) {
//            if (metalSpot.isCaptured()) {
//                g.setColor(Color.RED);
//            } else {
            g.setColor(Color.CYAN);
//            }
            g.drawOval(metalSpot.getX() - (int) cachedMetalMap.getMetalExtractorRadius(), metalSpot.getY() - (int) cachedMetalMap.getMetalExtractorRadius(),
                    (int) cachedMetalMap.getMetalExtractorRadius() * 2, (int) cachedMetalMap.getMetalExtractorRadius() * 2);
        }
    }

    public static void renderMetalZones(CachedMetalMap cachedMetalMap, MetalSpotManager metalSpotManager, Image image) {
        Graphics2D g = (Graphics2D) image.getGraphics();
        renderMetalZones(cachedMetalMap, metalSpotManager, g);
        g.dispose();
    }

    private static void renderMetalZones(CachedMetalMap cachedMetalMap, MetalSpotManager metalSpotManager, Graphics2D g) {
        for (MetalZone metalZone : metalSpotManager.getMetalZones()) {
            g.setColor(new Color(255, 255, 0));
            g.drawRect(metalZone.getX(), metalZone.getY(), metalZone.getWidth(), metalZone.getHeight());
        }
    }
}
