/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.map.control;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.common.util.GameMath;
import org.petah.common.util.profiler.Profiler;
import org.petah.spring.bai.delegate.GlobalDelegate;

/**
 *
 * @author Petah
 */
public class ControlMapRenderer {

    // Control map render options
    private static Option<Float> maxPowerColor = OptionsManager.getOption(
            new Option<Float>("ControlMapRenderer.maxPowerColor", 1000f));
    private static Option<Font> font = OptionsManager.getOption(
            new Option<Font>("ControlMapRenderer.font", new Font("Arial", Font.BOLD, 12)));

    private static void renderControlMap(Graphics2D g, ControlMap controlMap) {
        int zoneSize = ControlZone.getSize();
        for (ControlZone zone : controlMap.getControlZones()) {
            float power = GameMath.constrain(zone.getPower() / maxPowerColor.getValue(), 1f, -1f);
            if (zone.isFriendly()) {
                g.setColor(new Color(0, 1 * power, 0));
            } else if (zone.isEnemy()) {
                g.setColor(new Color(1 * (power * -1), 0, 0));
            } else {
                g.setColor(Color.YELLOW);
            }
            g.fillRect((int) zone.getMapX(), (int) zone.getMapY(), (int) zoneSize, (int) zoneSize);
            g.setColor(Color.BLUE);
            g.setFont(font.getValue());
            FontMetrics fm = g.getFontMetrics();
            String s = zone.getAge() + "";
            g.drawString(s, zone.getMapCenterX() - fm.stringWidth(s) / 2, zone.getMapCenterY());
            s = zone.getPower() + "";
            g.drawString(s, zone.getMapCenterX() - fm.stringWidth(s) / 2, zone.getMapCenterY() + fm.getHeight());
            if (zone.isFriendly()) {
                g.setColor(Color.GREEN);
                g.drawRect((int) zone.getMapX() + 1, (int) zone.getMapY() + 1, (int) zoneSize - 2, (int) zoneSize - 2);
            } else if (zone.isEnemy()) {
                g.setColor(Color.RED);
                g.drawRect((int) zone.getMapX() + 1, (int) zone.getMapY() + 1, (int) zoneSize - 2, (int) zoneSize - 2);
            }
        }
        g.setColor(Color.CYAN);
        for (int x = zoneSize; x < GlobalDelegate.getMapWidth(); x += zoneSize) {
            g.drawLine(x, 0, x, GlobalDelegate.getMapHeight());
        }
        for (int y = zoneSize; y < GlobalDelegate.getMapHeight(); y += zoneSize) {
            g.drawLine(0, y, GlobalDelegate.getMapWidth(), y);
        }
    }

    public static void renderControlMap(ControlMap controlMap, BufferedImage image) {
        Profiler.start(ControlMapRenderer.class, "renderControlMap()");
        Graphics2D g = (Graphics2D) image.getGraphics();
        ControlMapRenderer.renderControlMap(g, controlMap);
        g.dispose();
        Profiler.stop(ControlMapRenderer.class, "renderControlMap()");
    }
}
