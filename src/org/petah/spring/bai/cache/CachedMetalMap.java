/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.cache;

import org.petah.spring.bai.map.metal.*;
import com.springrts.ai.oo.clb.Map;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.common.util.profiler.Profiler;
import org.petah.spring.bai.InformationLogger;
import org.petah.spring.bai.ResourceManager;
import org.petah.spring.bai.util.ArrayUtil;
import org.petah.spring.bai.util.IOUtil;
import org.petah.spring.bai.util.MapUtil;

/**
 *
 * @author Petah
 */
public class CachedMetalMap implements Serializable {

    public static final long serialVersionUID = 1L;
    // Options
    private static final Option<Integer> metalMapMinAverageMetal = OptionsManager.getOption(
            new Option<Integer>("CachedMetalMap.metalMapMinAverageMetal", 60));
    private static final Option<Integer> greenFieldsMapMaxMetal = OptionsManager.getOption(
            new Option<Integer>("CachedMetalMap.greenFieldsMapMaxMetal", 1000));
    private static final Option<Boolean> cacheToDisk = OptionsManager.getOption(
            new Option<Boolean>("CachedMetalMap.cacheToDisk", false));
    // Class properties
    private int width;
    private int height;
    private List<Short> metalMap;
    private float metalExtractorRadius;
    private MetalMapType metalMapType;
    private long totalMetal;
    private float averageMetal;

    public CachedMetalMap(Map map) {
        Profiler.start(CachedMetalMap.class, "CachedMetalMap()");
        File file = new File(InformationLogger.getCacheDirectory().getAbsolutePath() + File.separator +
                map.getName() + "." + map.getChecksum() + "." + serialVersionUID + ".metal.bin");
        CachedMetalMap loadedObject = (CachedMetalMap) IOUtil.loadCacheFile(cacheToDisk, file);
        if (loadedObject != null) {
            width = loadedObject.width;
            height = loadedObject.height;
            metalMap = loadedObject.metalMap;
            metalExtractorRadius = loadedObject.metalExtractorRadius;
            metalMapType = loadedObject.metalMapType;
            totalMetal = loadedObject.totalMetal;
            averageMetal = loadedObject.averageMetal;
            Logger.getLogger(CachedMetalMap.class.getName()).info("Loaded metal map from cache.");
        } else {
            width = MapUtil.mapToMetal(map.getWidth());
            height = MapUtil.mapToMetal(map.getHeight());
            metalMap = map.getResourceMapRaw(ResourceManager.getMetal());
            metalExtractorRadius = map.getExtractorRadius(ResourceManager.getMetal());
            // Calculate total metal
            totalMetal = 0;
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    totalMetal += getValue(x, y);
                }
            }
            // Get average metal
            averageMetal = (float) totalMetal / (float) metalMap.size();
            // Get metal map type
            if (averageMetal >= metalMapMinAverageMetal.getValue()) {
                metalMapType = MetalMapType.Metal;
            } else if (totalMetal <= greenFieldsMapMaxMetal.getValue()) {
                metalMapType = MetalMapType.GreenFields;
            } else {
                metalMapType = MetalMapType.Normal;
            }
            Logger.getLogger(CachedMetalMap.class.getName()).info("Processed metal map.");
        }
        IOUtil.saveCacheFile(cacheToDisk, file, this);
        Profiler.stop(CachedMetalMap.class, "CachedMetalMap()");
    }

    /**
     * Gets the metal value at a position.
     * @param x x position on the metal map
     * @param y y position on the metal map
     * @return value between 0-255
     */
    public short getValue(int x, int y) {
        short value = metalMap.get(ArrayUtil.get1DIndex(x, y, width));
        if (value == -1) {
            value = 255;
        } else if (value < -1) {
            value += 256;
        }
        return value;
    }

    public float getMetalExtractorTerrainRadius() {
        return metalExtractorRadius;
    }

    public float getMetalExtractorRadius() {
        return metalExtractorRadius / 20;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public MetalMapType getMetalMapType() {
        return metalMapType;
    }

    public float getAverageMetal() {
        return averageMetal;
    }

    public long getTotalMetal() {
        return totalMetal;
    }
}
