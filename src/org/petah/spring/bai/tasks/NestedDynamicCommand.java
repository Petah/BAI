package org.petah.spring.bai.tasks;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Petah
 */
public class NestedDynamicCommand extends DynamicCommand {

    protected Queue<DynamicCommand> subCommands = new ConcurrentLinkedQueue<DynamicCommand>();

    @Override
    protected void execute() {
        for (DynamicCommand command : subCommands) {
            command.execute(aiDelegate, unit);
        }
    }

    public DynamicCommand addCommand(DynamicCommand command) {
        subCommands.add(command);
        return command;
    }

    public NestedDynamicCommand addCommand(NestedDynamicCommand command) {
        subCommands.add(command);
        return command;
    }

    public DynamicCondition addCommand(DynamicCondition command) {
        subCommands.add(command);
        return command;
    }
}
