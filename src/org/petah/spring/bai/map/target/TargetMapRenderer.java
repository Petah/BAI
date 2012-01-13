/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.map.target;

import com.springrts.ai.oo.AIFloat3;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.petah.common.util.GraphicsUtil;
import org.petah.common.util.profiler.Profiler;
import org.petah.spring.bai.GlobalOptions;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.util.MapUtil;

/**
 *
 * @author Petah
 */
public class TargetMapRenderer {

    private static BufferedImage metal;
    private static BufferedImage energy;
    private static BufferedImage commander;
    private static BufferedImage nuke;
    private static BufferedImage building;
    private static BufferedImage factory;
    private static BufferedImage builder;
    private static BufferedImage unit;
    private static BufferedImage aircraft;
    private static BufferedImage defencetower;
    private static BufferedImage antiair;
    private static BufferedImage ship;
    private static BufferedImage hovercraft;
    private static BufferedImage nano;
    private static BufferedImage unknown;

    static {
        try {
            metal = ImageIO.read(TargetMapRenderer.class.getResource("images/metal.png"));
            energy = ImageIO.read(TargetMapRenderer.class.getResource("images/energy.png"));
            commander = ImageIO.read(TargetMapRenderer.class.getResource("images/commander.png"));
            nuke = ImageIO.read(TargetMapRenderer.class.getResource("images/nuke.png"));
            building = ImageIO.read(TargetMapRenderer.class.getResource("images/building.png"));
            factory = ImageIO.read(TargetMapRenderer.class.getResource("images/factory.png"));
            builder = ImageIO.read(TargetMapRenderer.class.getResource("images/builder.png"));
            unit = ImageIO.read(TargetMapRenderer.class.getResource("images/unit.png"));
            aircraft = ImageIO.read(TargetMapRenderer.class.getResource("images/aircraft.png"));
            defencetower = ImageIO.read(TargetMapRenderer.class.getResource("images/defencetower.png"));
            antiair = ImageIO.read(TargetMapRenderer.class.getResource("images/antiair.png"));
            ship = ImageIO.read(TargetMapRenderer.class.getResource("images/ship.png"));
            hovercraft = ImageIO.read(TargetMapRenderer.class.getResource("images/hovercraft.png"));
            nano = ImageIO.read(TargetMapRenderer.class.getResource("images/nano.png"));
            unknown = ImageIO.read(TargetMapRenderer.class.getResource("images/unknown.png"));
        } catch (IOException ex) {
            Logger.getLogger(TargetMapRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void renderTargetMap(final Graphics2D g, TargetMap targetMap, int width, int height) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        for (TargetZone zone : targetMap.getTargetZones()) {
            AIFloat3 center = zone.getPosition();
            int radius = (int) MapUtil.terrainToMap(zone.getRadius());
//            if (radius > 300) {
//                zone.iterateUnits(new ElementProcessor<CachedUnit>() {
//
//                    public void process(CachedUnit unit) {
//                        System.err.println(FormatUtil.formatAIFloat3(unit.getPos()));
//                    }
//                });
//            }
            g.setColor(new Color(1, 0, 0, 0.2f));
            g.fillOval((int) MapUtil.terrainToMap(center.x) - radius, (int) MapUtil.terrainToMap(center.z) - radius, radius * 2, radius * 2);
            g.setColor(Color.RED);
            g.drawOval((int) MapUtil.terrainToMap(center.x) - radius, (int) MapUtil.terrainToMap(center.z) - radius, radius * 2, radius * 2);
            BufferedImage image;
            switch (zone.getTargetType()) {
                case Commander:
                    image = commander;
                    break;
                case Energy:
                    image = energy;
                    break;
                case Metal:
                    image = metal;
                    break;
                case AntiAir:
                    image = antiair;
                    break;
                case DefenceTowers:
                    image = defencetower;
                    break;
                case Units:
                    image = unit;
                    break;
                case Nuke:
                    image = nuke;
                    break;
                case AntiNuke:
                    image = nuke;
                    break;
                case Factory:
                    image = factory;
                    break;
                case Buildings:
                    image = building;
                    break;
                case Builders:
                    image = builder;
                    break;
                case Ships:
                    image = ship;
                    break;
                case Nanos:
                    image = nano;
                    break;
                case Hovercraft:
                    image = hovercraft;
                    break;
                case Aircraft:
                    image = aircraft;
                    break;
                default:
                    image = unknown;
            }
            g.drawImage(image, (int) MapUtil.terrainToMap(center.x) - 8, (int) MapUtil.terrainToMap(center.z) - 8, 16, 16, null);
        }

        g.setColor(Color.GREEN);
        for (TargetZone zone : targetMap.getTargetZones()) {
            for (CachedUnit zoneUnit : zone.getUnits()) {
                AIFloat3 pos = zoneUnit.getPos();
                GraphicsUtil.drawCross(g, (int) MapUtil.terrainToMap(pos.x), (int) MapUtil.terrainToMap(pos.z), 2);
            }
        }
    }

    public static void renderTargetMap(TargetMap targetMap, BufferedImage image) {
        Profiler.start(TargetMapRenderer.class, "renderTargetMap()");
        Graphics2D g = (Graphics2D) image.getGraphics();
        if (GlobalOptions.isAntiAlias()) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        renderTargetMap(g, targetMap, image.getWidth(), image.getHeight());
        g.dispose();
        Profiler.stop(TargetMapRenderer.class, "renderTargetMap()");
    }
}
