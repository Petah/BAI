/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai;

import com.springrts.ai.AICommand;
import com.springrts.ai.AICommandWrapper;
import com.springrts.ai.AIFloat3;
import com.springrts.ai.command.SendTextMessageAICommand;
import com.springrts.ai.oo.AbstractOOAI;
import com.springrts.ai.oo.Map;
import com.springrts.ai.oo.OOAICallback;
import com.springrts.ai.oo.Unit;
import java.util.logging.Logger;
import org.petah.common.commandline.ArgumentParser;
import org.petah.common.util.GameMath;
import org.petah.common.util.profiler.Profiler;
import org.petah.spring.bai.group.GroupManager;
import org.petah.spring.bai.gui.GUIManager;
import org.petah.spring.bai.map.control.ControlMap;
import org.petah.spring.bai.map.metal.MetalMap;
import org.petah.spring.bai.util.MapUtil;

/**
 *
 * @author Petah
 */
public class BAI extends AbstractOOAI {

    private boolean active = true;
    private boolean initialised = false;
    // Managers
    private GroupManager groupManager;
    private TeamManager teamManager;
    private ResourceManager resourceManager;
    private LocalCommandHandler localCommandHandler;
    // System
    private OOAICallback callBack;
    private int teamID;
    private int allyTeamID;
    // Map
    private Map map;
    // Timing
    private long lastFrame = System.currentTimeMillis();
    // Positioning
    private AIFloat3 baseCenter;
    private float startDirection;
    private Compass startSide;

    public BAI(int teamID, OOAICallback callBack) {
        this.teamID = teamID;
        this.callBack = callBack;
    }

    @Override
    public int init(int teamId, OOAICallback callback) {
        this.callBack = callback;
        this.teamID = teamId;
        this.allyTeamID = callback.getGame().getMyAllyTeam();
        this.map = callback.getMap();
        localCommandHandler = new LocalCommandHandler(this);
        teamManager = TeamManager.getTeamManager(this);
        resourceManager = new ResourceManager(this);
        GUIManager.addBAI(this);
        sendMessage("BAI Ready!");
        return super.init(teamId, callback);
    }

    public String getPrefix() {
        return "BAI" + teamID + ": ";
    }

    public int handleCommand(AICommand command) {
        if (!active) {
            throw new RuntimeException(getPrefix() + "called handleCommand while inactive.");
        }
        int result = callBack.getEngine().handleCommand(AICommandWrapper.COMMAND_TO_ID_ENGINE, -1, command);
        if (result != 0) {
            String errorMessage = "An error occured when handling an AI command: " + command.getClass().getSimpleName() +
                    " Topic " + command.getTopic() + " Error: " + result;
            sendMessage(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        return result;
    }

    public int sendMessage(Object o) {
        Logger.getLogger(BAI.class.getName()).info(getPrefix() + o);
        return handleCommand(new SendTextMessageAICommand(getPrefix() + o, 0));
    }

    @Override
    public int message(int player, String message) {
        Logger.getLogger(GlobalCommandHandler.class.getName()).info(getPrefix() + "Message from " + player + ": " + message);
        if (message.startsWith(ArgumentParser.getArgumentPrefix())) {
            GlobalCommandHandler.processCommand(this, player, message);
            localCommandHandler.processCommand(player, message);
        }
        return super.message(player, message);
    }

    @Override
    public int update(int frame) {
        Profiler.start(BAI.class, "update()", "BAI: " + teamID);
        float tbf = (float) (System.currentTimeMillis() - lastFrame) / 1000f;
        teamManager.update(tbf, frame, this);
        resourceManager.update(tbf, frame);
        groupManager.update(tbf, frame);
        lastFrame = System.currentTimeMillis();
        Profiler.stop(BAI.class, "update()");
        return super.update(frame);
    }

    @Override
    public int release(int reason) {
        Logger.getLogger(GlobalCommandHandler.class.getName()).info(getPrefix() + "Release AI");
        active = false;
        teamManager.removeBAI(this);
        BAIFactory.removeAI(this);
        return super.release(reason);
    }

    @Override
    public int unitFinished(Unit unit) {
        if (!initialised) {
            initialised = true;
            AIFloat3 pos = unit.getPos();
            baseCenter = pos;
            startDirection = GameMath.pointDirection(pos.x, pos.y, MapUtil.mapToTerrain(map.getWidth() / 2), MapUtil.mapToTerrain(map.getHeight() / 2));
            if (startDirection < 22.5f) {
                startSide = Compass.East;
            } else if (startDirection < 67.5f) {
                startSide = Compass.SouthEast;
            } else if (startDirection < 112.5f) {
                startSide = Compass.South;
            } else if (startDirection < 157.5f) {
                startSide = Compass.SouthWest;
            } else if (startDirection < 202.5f) {
                startSide = Compass.West;
            } else if (startDirection < 247.5f) {
                startSide = Compass.NorthWest;
            } else if (startDirection < 292.5f) {
                startSide = Compass.North;
            } else if (startDirection < 337.5f) {
                startSide = Compass.NorthEast;
            } else {
                startSide = Compass.East;
            }
        }
        addUnit(unit);
        return super.unitFinished(unit);
    }

    @Override
    public int unitDestroyed(Unit unit, Unit attacker) {
        removeUnit(unit);
        return super.unitDestroyed(unit, attacker);
    }

    @Override
    public int unitGiven(Unit unit, int oldTeamId, int newTeamId) {
        addUnit(unit);
        return super.unitGiven(unit, oldTeamId, newTeamId);
    }

    @Override
    public int unitCaptured(Unit unit, int oldTeamId, int newTeamId) {
        if (oldTeamId == teamID) {
            removeUnit(unit);
        }
        if (newTeamId == teamID) {
            addUnit(unit);
        }
        return super.unitCaptured(unit, oldTeamId, newTeamId);
    }

    public void addUnit(Unit unit) {
        groupManager.groupUnit(unit);
    }

    public void removeUnit(Unit unit) {
        groupManager.removeUnit(unit);
    }

    // Getters and Setters
    public OOAICallback getCallBack() {
        if (!active) {
            throw new RuntimeException(getPrefix() + "called getCallBack while inactive.");
        }
        return callBack;
    }

    public int getTeamID() {
        return teamID;
    }

    public GroupManager getGroupManager() {
        return groupManager;
    }

    public void setGroupManager(GroupManager groupManager) {
        this.groupManager = groupManager;
    }

    public Map getMap() {
        return map;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public AIFloat3 getBaseCenter() {
        return baseCenter;
    }

    public void setBaseCenter(AIFloat3 baseCenter) {
        this.baseCenter = baseCenter;
    }

    public int getAllyTeamID() {
        return allyTeamID;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public Compass getStartSide() {
        return startSide;
    }

    public float getStartDirection() {
        return startDirection;
    }
}
