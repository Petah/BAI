/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.group;

import com.springrts.ai.oo.Unit;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.Vector;
import org.petah.spring.bai.BAI;
import org.petah.spring.bai.tasks.Task;

/**
 *
 * @author Petah
 */
public class Group implements Iterable<Unit> {

    protected BAI bai;
    protected List<Unit> units = new Vector<Unit>();
    protected List<Task> tasks = new Stack<Task>();

    public Group(BAI bai) {
        this.bai = bai;
    }

    public void addUnit(Unit unit) {
        units.add(unit);
    }

    public void removeUnit(Unit unit) {
        units.remove(unit);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }
    public void clearTasks() {
        tasks.clear();
    }

    public void update(float tbf, int frame) {
        // If there is a task avalible
        if (tasks.size() > 0) {
            // Get the first task and update it
            if (tasks.get(0).update(this, tbf, frame)) {
                // If the task returns true, remove it
                tasks.remove(0);
            }
        }
    }

    public Unit getUnit(int i) {
        return units.get(i);
    }

    public int size() {
        return units.size();
    }

    public Iterator<Unit> iterator() {
        return units.iterator();
    }
}
