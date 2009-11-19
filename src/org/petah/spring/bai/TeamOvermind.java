/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.delegate.TeamDelegate;

/**
 *
 * @author Petah
 */
public class TeamOvermind {

    private TeamDelegate teamDelegate;
    private ConcurrentHashMap<AIDelegate, CopyOnWriteArraySet<Strategy>> aiStrategys = new ConcurrentHashMap<AIDelegate, CopyOnWriteArraySet<Strategy>>();

    public TeamOvermind(TeamDelegate teamDelegate) {
        this.teamDelegate = teamDelegate;
    }

    public int getStrategyAmount(Strategy strategy) {
        int count = 0;
        for (AIDelegate aiDelegate : aiStrategys.keySet()) {
            if (isAIStrategy(aiDelegate, strategy)) {
                count++;
            }
        }
        return count;
    }

    public void addAIDelegate(AIDelegate aiDelegate) {
        aiStrategys.put(aiDelegate, new CopyOnWriteArraySet<Strategy>());
    }

    public boolean isAIStrategy(AIDelegate aiDelegate, Strategy strategy) {
        return aiStrategys.get(aiDelegate).contains(strategy);
    }

    public void addStrategy(AIDelegate aiDelegate, Strategy strategy) {
        aiStrategys.get(aiDelegate).add(strategy);
    }

    public void removeStrategy(AIDelegate aiDelegate, Strategy strategy) {
        aiStrategys.get(aiDelegate).remove(strategy);
    }
}
