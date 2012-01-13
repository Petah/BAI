/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.cache;

import com.springrts.ai.oo.clb.Map;
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
import org.petah.spring.bai.util.MapUtil;

/**
 *
 * @author Petah
 */
public class CachedSlopeMap implements Serializable {

    public static final long serialVersionUID = 1L;
    // Options
    private static final Option<Boolean> cacheToDisk = OptionsManager.getOption(
            new Option<Boolean>("CachedSlopeMap.cacheToDisk", false));
    // Class properties
    private List<Float> slopeMap;
    private int width;
    private int height;

    public CachedSlopeMap(Map map) {
        Profiler.start(CachedSlopeMap.class, "CachedSlopeMap()");
        File file = new File(InformationLogger.getCacheDirectory().getAbsolutePath() + File.separator +
                map.getName() + "." + map.getChecksum() + "." + serialVersionUID + ".slope.bin");
        CachedSlopeMap loadedObject = (CachedSlopeMap) IOUtil.loadCacheFile(cacheToDisk, file);
        if (loadedObject != null) {
            slopeMap = loadedObject.slopeMap;
            width = loadedObject.width;
            height = loadedObject.height;
            Logger.getLogger(CachedSlopeMap.class.getName()).info("Loaded slope map from cache.");
        } else {
            slopeMap = map.getSlopeMap();
            width = MapUtil.mapToSlope(map.getWidth());
            height = MapUtil.mapToSlope(map.getHeight());
            Logger.getLogger(CachedSlopeMap.class.getName()).info("Processed slope map.");
        }
        IOUtil.saveCacheFile(cacheToDisk, file, this);
        Profiler.stop(CachedSlopeMap.class, "CachedSlopeMap()");
    }

    public Float getValue(int x, int y) {
        return slopeMap.get(ArrayUtil.get1DIndex(x, y, width));
    }

    public List<Float> getSlopeMap() {
        return slopeMap;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
