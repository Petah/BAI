/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.delegate.AIDelegate;

/**
 *
 * @author Petah
 */
public class AIOvermind {

    private AIDelegate aiDelegate;
    // TODO: need to remove these units from the list some how
    private List<CachedUnit> reclaimableUnits = new CopyOnWriteArrayList<CachedUnit>();

    public AIOvermind(AIDelegate aiDelegate) {
        this.aiDelegate = aiDelegate;
    }

    public void addReclaimableUnit(CachedUnit unit) {
        reclaimableUnits.add(unit);
    }

    public List<CachedUnit> getReclaimableUnits() {
        return reclaimableUnits;
    }
}
