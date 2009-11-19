/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks;

/**
 *
 * @author Petah
 */
public abstract class DynamicCondition extends NestedDynamicCommand {

    DynamicCommand elseCommand;

    @Override
    protected void execute() {
        if (check()) {
            super.execute();
        } else {
            if (elseCommand != null) {
                elseCommand.execute(aiDelegate, unit);
            }
        }
    }

    public abstract boolean check();

    public DynamicCommand setElseCommand(DynamicCommand elseCommand) {
        this.elseCommand = elseCommand;
        return elseCommand;
    }

    public NestedDynamicCommand setElseCommand(NestedDynamicCommand elseCommand) {
        this.elseCommand = elseCommand;
        return elseCommand;
    }

    public DynamicCondition setElseCommand(DynamicCondition elseCommand) {
        this.elseCommand = elseCommand;
        return elseCommand;
    }
}
