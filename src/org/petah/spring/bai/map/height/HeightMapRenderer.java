/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.map.height;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import org.petah.spring.bai.cache.CachedMap;

/**
 *
 * @author Petah
 */
public class HeightMapRenderer {

//    public static void renderHeightMap(Image image) {
//        Graphics2D g = (Graphics2D) image.getGraphics();
//        renderHeightMap(g);
//        g.dispose();
//    }
//
//    private static void renderHeightMap(Graphics2D g) {
//        float max = CachedMap.getMaxHeight() - CachedMap.getMinHeight();
//        for (int y = 0; y < CachedMap.getHeight(); y++) {
//            for (int x = 0; x < CachedMap.getWidth(); x++) {
//                float color = CachedMap.getElevationAt(x, y);
//                color -= HeightMap.getMinHeight();
//                color = color / max;
//                g.setColor(new Color(color, color, color));
//                g.drawLine(x, y, x, y);
//            }
//        }
//    }
//    public static BufferedImage getHeightMapImage(List<Float> rawHeightMap, int width, int height) {
//        BufferedImage image = new BufferedImage(HeightMap.getWidth(), HeightMap.getHeight(), BufferedImage.TYPE_INT_RGB);
//        Graphics2D g = (Graphics2D) image.getGraphics();
//        if (DefaultOptions.antiAlias.getValue()) {
//            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        }
//        HeightMapRenderer.renderHeightMap(g);
//        return image;
//    }
}
