/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.delegate;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.petah.spring.bai.TeamOvermind;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.cache.CachedUnitManager;
import org.petah.spring.bai.map.control.ControlMap;
import org.petah.spring.bai.map.metal.MetalSpotManager;
import org.petah.spring.bai.map.target.TargetMap;

/**
 *
 * @author davnei06
 */
public class TeamDelegate {
    // Managers

//    private CacheManager cacheManager;
    private TeamOvermind teamOvermind;
    private CachedUnitManager cachedUnitManager;
    private MetalSpotManager metalSpotManager;
    // Maps
    private ControlMap controlMap;
    private TargetMap targetMap;
    // Alliance ID
    private int allianceID;
    // AI delegates
    private List<AIDelegate> aiDelegates = new CopyOnWriteArrayList<AIDelegate>();

    //Constructors
    public TeamDelegate(AIDelegate aiDelegate) {
        allianceID = aiDelegate.getAllianceID();
        teamOvermind = new TeamOvermind(this);
        addAIDelegate(aiDelegate);
        cachedUnitManager = new CachedUnitManager();
        metalSpotManager = new MetalSpotManager(GlobalDelegate.getCachedMetalMap());
        controlMap = new ControlMap(this);
        targetMap = new TargetMap(this);
    }

    // Public methods
    public String getPrefix() {
        return "Team " + allianceID + ": ";
    }

    // List accessors/mutators
    public void addAIDelegate(AIDelegate aiDelegate) {
        // TODO: check ai delegate are being removed
        aiDelegates.add(aiDelegate);
        teamOvermind.addAIDelegate(aiDelegate);
    }

    // Delegate Methods
//    public CachedMap getMap() {
//        return cacheManager.getCachedMap();
//    }
    public ConcurrentHashMap<Integer, CachedUnit> getFriendlyUnits() {
        return cachedUnitManager.getFriendlyUnits();
    }

    public ConcurrentHashMap<Integer, CachedUnit> getEnemyUnits() {
        return cachedUnitManager.getEnemyUnits();
    }

    public CachedUnitManager getCachedUnitManager() {
        return cachedUnitManager;
    }

    //Getters
//    public CacheManager getCacheManager() {
//        return cacheManager;
//    }
    public ControlMap getControlMap() {
        return controlMap;
    }

    public TargetMap getTargetMap() {
        return targetMap;
    }

    public List<AIDelegate> getAIDelegates() {
        return aiDelegates;
    }

    public MetalSpotManager getMetalSpotManager() {
        return metalSpotManager;
    }

    public TeamOvermind getTeamOvermind() {
        return teamOvermind;
    }
}
