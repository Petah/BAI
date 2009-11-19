/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.petah.common.util.profiler.Profiler;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.delegate.TeamDelegate;
import org.petah.spring.bai.gui.GUIManager;

/**
 *
 * @author davnei06
 */
public class TeamDelegateFactory {

    private static final Map<Integer, TeamDelegate> teamDelegates = new ConcurrentHashMap<Integer, TeamDelegate>();

    public static TeamDelegate getTeamDelegate(AIDelegate aiDelegate) {
        Profiler.start(TeamDelegateFactory.class, "getTeamDelegate()");
        TeamDelegate teamDelegate;
        int allianceID = aiDelegate.getAllianceID();
        if (teamDelegates.containsKey(allianceID)) {
            teamDelegate = teamDelegates.get(allianceID);
            teamDelegate.addAIDelegate(aiDelegate);
        } else {
            teamDelegate = new TeamDelegate(aiDelegate);
            teamDelegates.put(allianceID, teamDelegate);
            GUIManager.addTeamDelegate(teamDelegate);
        }
        // FIXME: Group manager assignments
//        if (teamManager.ais.size() % 2 == 0) {
//        bai.setGroupManager(new PorcGroupManager(bai));
//        } else {
//            bai.setGroupManager(new DefaultGroupManager(bai));
//        }
        Profiler.stop(TeamDelegateFactory.class, "getTeamDelegate()");
        return teamDelegate;
    }
}
