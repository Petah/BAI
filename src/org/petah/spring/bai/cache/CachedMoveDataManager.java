/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.cache;

import com.springrts.ai.oo.clb.MoveData;
import com.springrts.ai.oo.clb.OOAICallback;
import java.io.File;
import java.io.Serializable;
import java.util.TreeMap;
import java.util.logging.Logger;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.common.util.profiler.Profiler;
import org.petah.spring.bai.InformationLogger;
import org.petah.spring.bai.util.IOUtil;

/**
 *
 * @author Petah
 */
public class CachedMoveDataManager implements Serializable {

    public static final long serialVersionUID = 1L;
    // Options
    private static final Option<Boolean> cacheToDisk = OptionsManager.getOption(
            new Option<Boolean>("CachedMoveDataManager.cacheToDisk", false));
    // Class properties
    private TreeMap<String, CachedMoveData> moveData;

    public CachedMoveDataManager(OOAICallback callback) {
        Profiler.start(CachedMoveDataManager.class, "CachedMoveDataManager()");
        File file = new File(InformationLogger.getCacheDirectory().getAbsolutePath() + File.separator +
                callback.getMod().getFileName() + "." + serialVersionUID + ".movedata.bin");
        CachedMoveDataManager loadedObject = (CachedMoveDataManager) IOUtil.loadCacheFile(cacheToDisk, file);
        if (loadedObject != null) {
            moveData = loadedObject.moveData;
            Logger.getLogger(CachedMetalMap.class.getName()).info("Loaded move data from cache.");
        } else {
            moveData = new TreeMap<String, CachedMoveData>();
            Logger.getLogger(CachedMetalMap.class.getName()).info("Processed move data.");
        }
        IOUtil.saveCacheFile(cacheToDisk, file, this);
        Profiler.stop(CachedMoveDataManager.class, "CachedMoveDataManager()");
    }

    public CachedMoveData getCachedMoveData(MoveData data) {
        if (data == null) {
            return null;
        }
        CachedMoveData cachedMoveData = new CachedMoveData(data);
        if (moveData.containsKey(cachedMoveData.getName())) {
            return moveData.get(cachedMoveData.getName());
        }
        moveData.put(cachedMoveData.getName(), cachedMoveData);
        return cachedMoveData;
    }

    public TreeMap<String, CachedMoveData> getCachedMoveData() {
        return moveData;
    }
}
