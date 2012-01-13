/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.cache;

import com.springrts.ai.oo.clb.Map;
import java.io.File;
import java.io.Serializable;
import java.util.logging.Logger;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.common.util.profiler.Profiler;
import org.petah.spring.bai.InformationLogger;
import org.petah.spring.bai.util.IOUtil;

/**
 *
 * @author davnei06
 */
public class CachedMap implements Serializable {

    public static final long serialVersionUID = 1L;
    // Options
    private static final Option<Boolean> cacheToDisk = OptionsManager.getOption(
            new Option<Boolean>("CachedMap.cacheToDisk", true));
    // Class properties
    private int width;
    private int height;

    public CachedMap(Map map) {
        Profiler.start(CachedMap.class, "CachedMap()");
        File file = new File(InformationLogger.getCacheDirectory().getAbsolutePath() + File.separator +
                map.getName() + "." + map.getChecksum() + "." + serialVersionUID + ".map.bin");
        CachedMap loadedObject = (CachedMap) IOUtil.loadCacheFile(cacheToDisk, file);
        if (loadedObject != null) {
            width = loadedObject.width;
            height = loadedObject.height;
            Logger.getLogger(CachedMap.class.getName()).info("Loaded map from cache.");
        } else {
            width = map.getWidth();
            height = map.getHeight();
            Logger.getLogger(CachedMap.class.getName()).info("Processed map.");
        }
        IOUtil.saveCacheFile(cacheToDisk, file, this);
        Profiler.stop(CachedMap.class, "CachedMap()");

        // TODO: move these
//        CachedSlopeMap.init(map);
//        IslandMap.init(); // Depends on CachedSlopeMap and cached unit defs
    }


    // Getters
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
