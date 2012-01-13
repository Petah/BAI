/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks;

import com.springrts.ai.oo.AIFloat3;
import org.petah.spring.bai.delegate.GlobalDelegate;

/**
 *
 * @author Petah
 */
public class FactoryBuild extends DynamicCommand {

    private String unitName;

    public FactoryBuild(String unitName) {
        this.unitName = unitName;
    }

    @Override
    public void execute() {
        unit.build(GlobalDelegate.getUnitDef(unitName, unit.getFaction()).getUnitDef(), new AIFloat3());
    }
}
