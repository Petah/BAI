/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks.standard;

import org.petah.spring.bai.delegate.AIDelegate;

/**
 *
 * @author Petah
 */
public class DefaultCommanderStage2 extends DefaultCommanderStage1 {

    public DefaultCommanderStage2(AIDelegate aiDelegate) {
        super(aiDelegate);
    }

    @Override
    protected BasicBuilding getNeededBasicBuildingNeeded(AIDelegate aiDelegate) {
        int mexAmount = aiDelegate.getGroupManager().getGroup("MetalExtractors").size();
        int energyAmount = aiDelegate.getGroupManager().getGroup("EnergyGenerators").size();
        if (mexAmount < BUILD_MAX_METAL_AMOUNT && mexAmount <= energyAmount) {
            return BasicBuilding.Mex;
        }
        if (!aiDelegate.getResourceManager().isEnergyOver(BUILD_ENERGY_IF_E_UNDER, BUILD_ENERGY_IF_EINCOME_UNDER) &&
                energyAmount < BUILD_MAX_ENERGY_AMOUNT) {
            return BasicBuilding.Energy;
        }

        if (aiDelegate.getGroupManager().getGroup("Radar").size() < BUILD_MAX_RADAR) {
            return BasicBuilding.Radar;
        }

        if (aiDelegate.getResourceManager().isEnergyOver(BUILD_ESTOR_MIN_E, BUILD_ESTOR_MIN_EINCOME) &&
                aiDelegate.getResourceManager().isMetalOver(BUILD_ESTOR_MIN_M, BUILD_ESTOR_MIN_MINCOME) &&
                aiDelegate.getGroupManager().getGroup("EnergyStorage").size() < BUILD_MAX_ESTOR) {
            return BasicBuilding.EnergyStorage;
        }
        if (aiDelegate.getResourceManager().isMetalOver(BUILD_MSTOR_MIN_M, BUILD_MSTOR_MIN_MINCOME) &&
                aiDelegate.getResourceManager().isEnergyOver(BUILD_MSTOR_MIN_E, BUILD_MSTOR_MIN_EINCOME) &&
                aiDelegate.getGroupManager().getGroup("MetalStorage").size() < BUILD_MAX_MSTOR) {
            return BasicBuilding.MetalStorage;
        }

        return BasicBuilding.None;
    }
}
