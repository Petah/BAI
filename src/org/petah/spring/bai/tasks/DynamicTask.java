/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks;

import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.group.UnitGroup;
import org.petah.spring.bai.unit.Faction;

/**
 *
 * @author Petah
 */
public class DynamicTask extends Task {

    private int nextUpdate = 0;
    private int updateSpeed = 30;
    private DynamicCommand rootCommand;

    public DynamicTask(AIDelegate aiDelegate, DynamicCommand rootCommand) {
        super(aiDelegate);
        this.rootCommand = rootCommand;
    }

    public boolean update(UnitGroup group, int frame) {
        if (nextUpdate <= frame) {
            nextUpdate = frame + updateSpeed;
            for (CachedUnit unit : group) {
                rootCommand.execute(aiDelegate, unit);
            }
        }
        return false;
    }

    public static Task getDynamicTestTask(AIDelegate aiDelegate) {
        DynamicCondition rootCommand = new StatusCondition(StatusCondition.Status.Idle);
        {
            DynamicCondition factoryCheck = rootCommand.addCommand(new UnitCondition("KbotFactory"));
            {
                DynamicCondition resourceCheck = factoryCheck.addCommand(new ResourceCondition(500, ResourceCondition.Resource.Metal, ResourceCondition.CheckType.Over));
                {
                    DynamicCondition factionCheck = resourceCheck.addCommand(new FactionCondition(Faction.Arm));
                    {
                        DynamicCondition groupCheck = factionCheck.addCommand(new GroupCondition(15, "Scouts", GroupCondition.CheckType.Under));
                        {
                            groupCheck.addCommand(new FactoryBuild("Flea"));
                            groupCheck.addCommand(new FactoryBuild("Flea"));
                        }
                        factionCheck.addCommand(new FactoryBuild("Warrior"));
                    }
                    resourceCheck.addCommand(new FactoryBuild("KLightRaider"));
                    resourceCheck.addCommand(new FactoryBuild("KLightRanger"));
                    resourceCheck.addCommand(new FactoryBuild("KLightAssault"));
                }
                NestedDynamicCommand elseCondition = resourceCheck.setElseCommand(new NestedDynamicCommand());
                {
                    DynamicCondition factionCheck = elseCondition.addCommand(new FactionCondition(Faction.Arm));
                    {
                        DynamicCondition groupCheck = factionCheck.addCommand(new GroupCondition(15, "Scouts", GroupCondition.CheckType.Under));
                        {
                            groupCheck.addCommand(new FactoryBuild("Flea"));
                        }
                    }
                    elseCondition.addCommand(new FactoryBuild("KLightRaider"));
                    elseCondition.addCommand(new FactoryBuild("KLightRaider"));
                    DynamicCondition groupCheck = elseCondition.addCommand(new GroupCondition(15, "Builders", GroupCondition.CheckType.Under));
                    {
                        groupCheck.addCommand(new FactoryBuild("KT1Builder"));
                    }
                }
            }
        }
        return new DynamicTask(aiDelegate, rootCommand);
    }
}
