/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.group;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.tasks.Task;

/**
 *
 * @author Petah
 */
public class UnitGroup implements Iterable<CachedUnit> {

    private String name;
    protected AIDelegate aiDelegate;
    protected List<CachedUnit> units = new CopyOnWriteArrayList<CachedUnit>();
    protected List<Task> tasks = new CopyOnWriteArrayList<Task>();

    public UnitGroup(AIDelegate aiDelegate, String name) {
        this.aiDelegate = aiDelegate;
        this.name = name;
    }

    public void addUnit(CachedUnit unit) {
        if (!units.contains(unit)) {
            units.add(unit);
        }
    }

    public void removeUnit(int unitID) {
        for (CachedUnit unit : units) {
            if (unit.getUnitId() == unitID) {
                units.remove(unit);
                return;
            }
        }
    }

    public void addTask(Task task) {
        if (!tasks.contains(task)) {
            tasks.add(task);
        }
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public void clearTasks() {
        tasks.clear();
    }

    public void update(int frame) {
        // If there is a task avalible
        if (tasks.size() > 0) {
            // Get the first task and update it
            if (tasks.get(0).update(this, frame)) {
                // If the task returns true, remove it
                tasks.remove(0);
            }
        }
    }

    public CachedUnit getUnit(int i) {
        return units.get(i);
    }

    public int size() {
        return units.size();
    }

    public Iterator<CachedUnit> iterator() {
        return units.iterator();
    }

    public boolean contains(CachedUnit unit) {
        return units.contains(unit);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public String getName() {
        return name;
    }
}
