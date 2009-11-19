/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks;

import org.petah.spring.bai.util.CommandUtil;

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
        CommandUtil.factoryBuild(aiDelegate, unitName, unit);
    }
}
