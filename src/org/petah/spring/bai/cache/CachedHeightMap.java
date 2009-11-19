/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.cache;

import com.springrts.ai.oo.Map;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.common.util.profiler.Profiler;
import org.petah.spring.bai.InformationLogger;
import org.petah.spring.bai.util.ArrayUtil;
import org.petah.spring.bai.util.IOUtil;

/**
 *
 * @author Petah
 */
public class CachedHeightMap implements Serializable {

    public static final long serialVersionUID = 1L;
    // Options
    private static final Option<Boolean> cacheToDisk = OptionsManager.getOption(
            new Option<Boolean>("CachedHeightMap.cacheToDisk", false));
    // Class properties
    private List<Float> heightMap;
    private int width;
    private int height;
    private float minHeight;
    private float maxHeight;

    public CachedHeightMap(Map map) {
        Profiler.start(CachedHeightMap.class, "CachedHeightMap()");
        File file = new File(InformationLogger.getCacheDirectory().getAbsolutePath() + File.separator +
                map.getName() + "." + map.getChecksum() + "." + serialVersionUID + ".heightmap.bin");
        CachedHeightMap loadedObject = (CachedHeightMap) IOUtil.loadCacheFile(cacheToDisk, file);
        if (loadedObject != null) {
            heightMap = loadedObject.heightMap;
            width = loadedObject.width;
            height = loadedObject.height;
            minHeight = loadedObject.minHeight;
            maxHeight = loadedObject.maxHeight;
            Logger.getLogger(CachedHeightMap.class.getName()).info("Loaded height map from cache.");
        } else {
            heightMap = map.getHeightMap();
            width = map.getWidth();
            height = map.getHeight();
            minHeight = map.getMinHeight();
            maxHeight = map.getMaxHeight();
            Logger.getLogger(CachedHeightMap.class.getName()).info("Processed height map.");
        }
        IOUtil.saveCacheFile(cacheToDisk, file, this);
        Profiler.stop(CachedHeightMap.class, "CachedHeightMap()");
    }

    public float getMaxHeight() {
        return maxHeight;
    }

    public float getMinHeight() {
        return minHeight;
    }

    public float getElevationAt(int x, int z) {
        return heightMap.get(ArrayUtil.get1DIndex(x, z, width));//map.getElevationAt(x, z);
    }

    public float getElevationAtTerrain(int x, int z) {
        return getElevationAt(x / 8, z / 8) * 8;
    }
}
