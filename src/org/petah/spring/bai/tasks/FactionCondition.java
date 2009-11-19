/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks;

import org.petah.spring.bai.unit.Faction;

/**
 *
 * @author Petah
 */
public class FactionCondition extends DynamicCondition {

    private Faction faction;

    public FactionCondition(Faction faction) {
        this.faction = faction;
    }

    public boolean check() {
        return Faction.getFaction(unit.getDef()) == faction;
    }
}
