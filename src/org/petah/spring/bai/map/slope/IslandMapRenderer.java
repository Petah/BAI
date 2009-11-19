/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.petah.spring.bai.map.slope;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import org.petah.common.util.profiler.Profiler;
import org.petah.spring.bai.delegate.GlobalDelegate;

/**
 *
 * @author Petah
 */
public class IslandMapRenderer {

    private static void renderIsland(IslandMap island, Graphics g) {
        for (int y = 0; y < GlobalDelegate.getSlopeMapHeight(); y++) {
            for (int x = 0; x < GlobalDelegate.getSlopeMapWidth(); x++) {
                if (island.getValue(x, y) == 0) {
                    g.setColor(Color.BLACK);
                } else {
                    float hue = (float) (island.getValue(x, y) - 1) / (float) (island.getMaxValue() - 1);
                    g.setColor(Color.getHSBColor(hue, 1f, 1f));
                }
                g.drawLine(x, y, x, y);
            }
        }
    }

    private static void renderMap(boolean[][] map, Graphics g) {
        for (int y = 0; y < GlobalDelegate.getSlopeMapHeight(); y++) {
            for (int x = 0; x < GlobalDelegate.getSlopeMapWidth(); x++) {
                if (map[x][y]) {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(Color.RED);
                }
                g.drawLine(x, y, x, y);
            }
        }
    }

    public static void renderMap(boolean[][] map, BufferedImage image) {
        Profiler.start(IslandMapRenderer.class, "renderMap()");
        Graphics g = image.getGraphics();
        renderMap(map, g);
        g.dispose();
        Profiler.stop(IslandMapRenderer.class, "renderMap()");
    }

    public static void renderIsland(IslandMap island, BufferedImage image) {
        Profiler.start(IslandMapRenderer.class, "renderIsland()");
        Graphics g = image.getGraphics();
        renderIsland(island, g);
        g.dispose();
        Profiler.stop(IslandMapRenderer.class, "renderIsland()");
    }
}
