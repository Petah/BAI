/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.group;

import com.springrts.ai.oo.AIFloat3;
import com.springrts.ai.oo.clb.Unit;
import com.springrts.ai.oo.clb.WeaponDef;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.petah.common.util.profiler.Profiler;
import org.petah.spring.bai.AIReturnCode;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.listener.DamageEventListener;
import org.petah.spring.bai.listener.UnitEventListener;
import org.petah.spring.bai.listener.UpdateEventListener;
import org.petah.spring.bai.tasks.Task;

/**
 *
 * @author Petah
 */
public abstract class GroupManager implements UnitEventListener, DamageEventListener, UpdateEventListener {

    // Properties
    protected AIDelegate aiDelegate;
    private Map<String, UnitGroup> groups = new ConcurrentHashMap<String, UnitGroup>();

    // Constructors
    public GroupManager(AIDelegate aiDelegate) {
        Profiler.start(getClass(), "GroupManager()");
        this.aiDelegate = aiDelegate;
        initGroups();
//        aiDelegate.getAIEventHandler().addUnitEventListener(this);
//        aiDelegate.getAIEventHandler().addDamageEventListener(this);
//        aiDelegate.getAIEventHandler().addUpdateEventListener(this);
        Profiler.stop(getClass(), "GroupManager()");
    }

    // Abstract methods
    public abstract void initGroups();

    public abstract void groupUnit(CachedUnit unit);

    // Private methods
    private int addUnit(Unit unit) {
        CachedUnit cachedUnit = aiDelegate.getCachedUnit(unit.getUnitId());
        if (cachedUnit == null) {
            return AIReturnCode.UNIT_NOT_CACHED;
        }
        groupUnit(cachedUnit);
        return AIReturnCode.NORMAL;
    }

    private int removeUnit(Unit unit) {
        for (UnitGroup group : groups.values()) {
            group.removeUnit(unit.getUnitId());
        }
        return AIReturnCode.NORMAL;
    }

    // Public methods
    public void addGroup(UnitGroup group, Task... initialTasks) {
        for (Task task : initialTasks) {
            group.addTask(task);
        }
        addGroup(group);
    }

    public void addGroup(UnitGroup group) {
        groups.put(group.getName().toLowerCase(), group);
    }

    public void update(int frame) {
        // Use an iterator to get the key (group name) for profiling
        Iterator<String> i = groups.keySet().iterator();
        while (i.hasNext()) {
            String groupName = i.next();
            UnitGroup group = groups.get(groupName);
            Profiler.start(GroupManager.class, aiDelegate.getPrefix() + groupName + ".update()");
            group.update(frame);
            Profiler.stop(GroupManager.class, aiDelegate.getPrefix() + groupName + ".update()");
        }
    }

    public UnitGroup getGroup(String name) {
        name = name.toLowerCase();
        return groups.get(name);
    }

    // Getters
    public Map<String, UnitGroup> getGroups() {
        return groups;
    }

    // Implemented methods
    public void unitCaptured(Unit unit, int oldTeamId, int newTeamId) {
        addUnit(unit);
    }

    public void unitCreated(Unit unit, Unit builder) {
        addUnit(unit);
    }

    public void unitFinished(Unit unit) {
        addUnit(unit);
    }

    public void unitGiven(Unit unit, int oldTeamId, int newTeamId) {
        addUnit(unit);
    }

    public void enemyDamaged(Unit enemy, Unit attacker, float damage, AIFloat3 dir, WeaponDef weaponDef, boolean paralyzer) {
    }

    public void enemyDestroyed(Unit enemy, Unit attacker) {
    }

    public void unitDamaged(Unit unit, Unit attacker, float damage, AIFloat3 dir, WeaponDef weaponDef, boolean paralyzer) {
    }

    public void unitDestroyed(Unit unit, Unit attacker) {
        removeUnit(unit);
    }
}
