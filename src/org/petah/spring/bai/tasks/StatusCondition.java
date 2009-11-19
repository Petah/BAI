/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks;

/**
 *
 * @author Petah
 */
public class StatusCondition extends DynamicCondition {

    public enum Status {

        Idle, Busy
    }
    private Status status;

    public StatusCondition(Status status) {
        this.status = status;
    }

    public boolean check() {
        switch (status) {
            case Idle:
                return unit.getCurrentCommands().size() == 0;
            case Busy:
                return unit.getCurrentCommands().size() > 0;
        }
        throw new RuntimeException("Dynamic status condition failed.");
    }
}
