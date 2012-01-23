/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.delegate;

import com.springrts.ai.oo.AIFloat3;
import com.springrts.ai.oo.clb.Economy;
import com.springrts.ai.oo.clb.Map;
import com.springrts.ai.oo.clb.OOAICallback;
import com.springrts.ai.oo.clb.Resource;
import java.util.List;
import java.util.logging.Logger;
import org.petah.common.util.profiler.Profiler;
import org.petah.spring.bai.BAI;
import org.petah.spring.bai.LocalCommandHandler;
import org.petah.spring.bai.MoveFailHandler;
import org.petah.spring.bai.AIOvermind;
import org.petah.spring.bai.PositionManager;
import org.petah.spring.bai.ResourceManager;
import org.petah.spring.bai.Strategy;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.cache.CachedUnitDef;
import org.petah.spring.bai.cache.CachedUnitManager;
import org.petah.spring.bai.factory.TeamDelegateFactory;
import org.petah.spring.bai.group.DefaultGroupManager;
import org.petah.spring.bai.group.GroupManager;
import org.petah.spring.bai.gui.GUIManager;
import org.petah.spring.bai.log.Log;
import org.petah.spring.bai.map.control.ControlZone;
import org.petah.spring.bai.map.metal.MetalSpotManager;
import org.petah.spring.bai.util.Compass;

/**
 *
 * @author davnei06
 */
public class AIDelegate {

    private static final int DEFAULT_ZONE = 0;

    private AIOvermind aiOvermind;
    // Handlers
    private BAI aiEventHandler;
    private LocalCommandHandler localCommandHandler;
    private MoveFailHandler moveFailHandler;
    // Delegates
    private TeamDelegate teamDelegate;
    // Managers
    private GroupManager groupManager;
    private ResourceManager resourceManager;
    private PositionManager positionManager;
    // Properties
    private OOAICallback callback;
    private Economy economy;
    private Map map;
    private int teamID;
    private int allianceID;

    // Constructors
    public AIDelegate(int teamID, OOAICallback callback, BAI aiEventHandler) {
        Log.entry(AIDelegate.class, "AIDelegate");
        Profiler.start(AIDelegate.class, "AIDelegate()");
        this.teamID = teamID;
        this.callback = callback;
        this.economy = callback.getEconomy();
        this.map = callback.getMap();
        this.aiEventHandler = aiEventHandler;
        this.allianceID = callback.getGame().getMyAllyTeam();
//        aiEventHandler.setAiDelegate(this);
        teamDelegate = TeamDelegateFactory.getTeamDelegate(this);
        // Add event listeners for each BAI so they are always updated while at least one BAI is alive.
        // Unit events wont be duplicated because of mapping the unit ID to the map key
//        aiEventHandler.addUnitEventListener(teamDelegate.getCachedUnitManager());
//        aiEventHandler.addEnemyEventListener(teamDelegate.getCachedUnitManager());
//        aiEventHandler.addDamageEventListener(teamDelegate.getCachedUnitManager());
        // Update events wont be called twice due to the update delay timer
//        aiEventHandler.addUpdateEventListener(teamDelegate.getCachedUnitManager());
//        aiEventHandler.addUpdateEventListener(teamDelegate.getControlMap());
//        aiEventHandler.addUpdateEventListener(teamDelegate.getTargetMap());
        groupManager = new DefaultGroupManager(this);
        positionManager = new PositionManager(this);
        localCommandHandler = new LocalCommandHandler(this);
        resourceManager = new ResourceManager(this);
        moveFailHandler = new MoveFailHandler(this);
        aiOvermind = new AIOvermind(this);
        GUIManager.addAIDelegate(this);
        Profiler.stop(AIDelegate.class, "AIDelegate()");
    }

    // Public methods
    /**
     * Not thread safe.
     * @param command
     * @return
     */
//    public int handleCommand(Command command) {
//        int result = callback.getEngine().handleCommand(CommandWrapper.COMMAND_TO_ID_ENGINE, -1, command);
//        if (result != AIReturnCode.NORMAL) {
//            throw new AICommandException(this, command, result);
//        }
//        return result;
//    }

    public int sendMessage(Object o) {
        Logger.getLogger(AIDelegate.class.getName()).info(getPrefix() + o);
        callback.getGame().sendTextMessage(getPrefix() + o, DEFAULT_ZONE);
        return 0;
    }

    public boolean sendResource(Resource resource, float amount, int teamId) {
        return economy.sendResource(resource, amount, teamId);
    }

    public String getPrefix() {
        return "AI " + teamID + ": ";
    }

    // Delegate methods
    public CachedUnitManager getCachedUnitManager() {
        return teamDelegate.getCachedUnitManager();
    }

    public MetalSpotManager getMetalSpotManager() {
        return teamDelegate.getMetalSpotManager();
    }

    public CachedUnit getCachedUnit(int unitID) {
        return teamDelegate.getCachedUnitManager().getCachedUnit(unitID);
    }

    public boolean isPossibleToBuildAt(CachedUnitDef def, AIFloat3 pos, int facing) {
        return map.isPossibleToBuildAt(def.getUnitDef(), pos, facing);
    }

    public AIFloat3 findClosestBuildSite(CachedUnitDef def, AIFloat3 pos, float searchRadius, int minDist, int facing) {
        return map.findClosestBuildSite(def.getUnitDef(), pos, searchRadius, minDist, facing);
    }

    public Compass getStartSide() {
        return positionManager.getStartSide();
    }

    public float getStartDirection() {
        return positionManager.getStartDirection();
    }

    public AIFloat3 getBaseCenter() {
        return positionManager.getBaseCenter();
    }

    public List<ControlZone> getEnemyControlZones() {
        return teamDelegate.getControlMap().getEnemyControlZones();
    }

    public List<ControlZone> getFriendlyControlZones() {
        return teamDelegate.getControlMap().getFriendlyControlZones();
    }

    public List<ControlZone> getNeutralControlZones() {
        return teamDelegate.getControlMap().getNeutralControlZones();
    }

    public void addStrategy(Strategy strategy) {
        teamDelegate.getTeamOvermind().addStrategy(this, strategy);
    }

    /**
     * Not thread safe.
     * @return the Economy for this AIDelegate
     */
    public Economy getEconomy() {
        return callback.getEconomy();
    }

    // Getters
    public BAI getAIEventHandler() {
        return aiEventHandler;
    }

    public int getAllianceID() {
        return allianceID;
    }

    /**
     * Not thread safe.
     * @return the OOAICallback for this AIDelegate
     */
    public OOAICallback getCallback() {
        return callback;
    }

    public GroupManager getGroupManager() {
        return groupManager;
    }

    public LocalCommandHandler getLocalCommandHandler() {
        return localCommandHandler;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public TeamDelegate getTeamDelegate() {
        return teamDelegate;
    }

    public int getTeamID() {
        return teamID;
    }

    public AIOvermind getAIOvermind() {
        return aiOvermind;
    }
}
