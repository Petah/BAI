/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks;

/**
 *
 * @author Petah
 */
public class ResourceCondition extends DynamicCondition {

    public enum Resource {

        Energy, Metal
    }

    public enum CheckType {

        Over, OverPercent, IncomeOver, Under, UnderPercent, IncomeUnder
    }
    private float amount;
    private Resource resource;
    private CheckType checkType;

    public ResourceCondition(float amount, Resource resource, CheckType checkType) {
        this.amount = amount;
        this.resource = resource;
        this.checkType = checkType;
    }

    public boolean check() {
        switch (resource) {
            case Metal:
                switch (checkType) {
                    case Over:
                        return aiDelegate.getResourceManager().isMetalOver((int) amount);
                    case OverPercent:
                        return aiDelegate.getResourceManager().isMetalOver(amount);
                    case IncomeOver:
                        return aiDelegate.getResourceManager().getMetalIncome() > amount;
                    case Under:
                        return !aiDelegate.getResourceManager().isMetalOver((int) amount);
                    case UnderPercent:
                        return !aiDelegate.getResourceManager().isMetalOver(amount);
                    case IncomeUnder:
                        return !(aiDelegate.getResourceManager().getMetalIncome() <= amount);
                }
            case Energy:
                switch (checkType) {
                    case Over:
                        return aiDelegate.getResourceManager().isEnergyOver((int) amount);
                    case OverPercent:
                        return aiDelegate.getResourceManager().isEnergyOver(amount);
                    case IncomeOver:
                        return aiDelegate.getResourceManager().getEnergyIncome() > amount;
                    case Under:
                        return !aiDelegate.getResourceManager().isEnergyOver((int) amount);
                    case UnderPercent:
                        return !aiDelegate.getResourceManager().isEnergyOver(amount);
                    case IncomeUnder:
                        return !(aiDelegate.getResourceManager().getEnergyIncome() <= amount);
                }
        }
        throw new RuntimeException("Dynamic resource condition failed.");
    }
}
