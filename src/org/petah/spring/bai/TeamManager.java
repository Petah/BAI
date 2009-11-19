/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai;

import com.springrts.ai.oo.OOAICallback;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.petah.common.util.profiler.Profiler;
import org.petah.spring.bai.group.DefaultGroupManager;
import org.petah.spring.bai.group.PorcGroupManager;
import org.petah.spring.bai.gui.GUIManager;
import org.petah.spring.bai.map.control.ControlMap;
import org.petah.spring.bai.map.metal.MetalMap;
import org.petah.spring.bai.map.target.TargetMap;

/**
 *
 * @author Petah
 */
public class TeamManager {

    private ControlMap controlMap;
    private MetalMap metalMap;
    private TargetMap targetMap;
    private int allyTeamID;
    private List<BAI> ais = new CopyOnWriteArrayList<BAI>();
    private static final HashMap<Integer, TeamManager> teamManagers = new HashMap<Integer, TeamManager>();

    public static TeamManager getTeamManager(BAI bai) {
        TeamManager teamManager;
        if (teamManagers.containsKey(bai.getAllyTeamID())) {
            teamManager = teamManagers.get(bai.getAllyTeamID());
            teamManager.addBAI(bai);
        } else {
            teamManager = new TeamManager(bai.getAllyTeamID());
            teamManager.addBAI(bai);
            teamManager.init();
            teamManagers.put(bai.getAllyTeamID(), teamManager);
            GUIManager.addTeamManager(teamManager);
        }
        if (teamManager.ais.size() % 2 == 0) {
            bai.setGroupManager(new PorcGroupManager(bai));
        } else {
            bai.setGroupManager(new DefaultGroupManager(bai));
        }
        return teamManager;
    }

    public TeamManager(int allyTeamID) {
        this.allyTeamID = allyTeamID;
    }

    private void init() {
        this.controlMap = new ControlMap(this);
        this.metalMap = new MetalMap(this);
        this.targetMap = new TargetMap(this);
    }
    public void update(float tbf, int frame, BAI bai) {
        Profiler.start(getClass(), "update()");
        controlMap.update(frame);
        targetMap.update(frame);
        Profiler.stop(getClass(), "update()");
    }

    public void addBAI(BAI bai) {
        ais.add(bai);
    }

    public void removeBAI(BAI bai) {
        ais.add(bai);
    }

    public String getPrefix() {
        return "Team " + allyTeamID + ": ";
    }

    public OOAICallback getCallBack() {
        return ais.get(0).getCallBack();
    }

    public ControlMap getControlMap() {
        return controlMap;
    }

    public MetalMap getMetalMap() {
        return metalMap;
    }

    public TargetMap getTargetMap() {
        return targetMap;
    }

    public List<BAI> getAIs() {
        return ais;
    }
}
