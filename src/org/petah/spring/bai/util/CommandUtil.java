/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.util;

import com.springrts.ai.AICommand;
import com.springrts.ai.AIFloat3;
import com.springrts.ai.command.AddLineDrawAICommand;
import com.springrts.ai.command.AddPointDrawAICommand;
import com.springrts.ai.command.AttackUnitAICommand;
import com.springrts.ai.command.BuildUnitAICommand;
import com.springrts.ai.command.FightUnitAICommand;
import com.springrts.ai.command.GuardUnitAICommand;
import com.springrts.ai.command.MoveUnitAICommand;
import com.springrts.ai.command.PatrolUnitAICommand;
import com.springrts.ai.command.ReclaimAreaUnitAICommand;
import com.springrts.ai.command.ReclaimUnitAICommand;
import com.springrts.ai.command.SetMoveStateUnitAICommand;
import com.springrts.ai.command.SetOnOffUnitAICommand;
import com.springrts.ai.command.StopUnitAICommand;
import java.util.Arrays;
import java.util.List;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.spring.bai.AICommandException;
import org.petah.spring.bai.AIReturnCode;
import org.petah.spring.bai.GlobalOptions;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.cache.CachedUnitDef;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.delegate.GlobalDelegate;

/**
 *
 * @author Petah
 */
public class CommandUtil {

    // Options
    private static Option<Integer> commandTimeout = OptionsManager.getOption(
            new Option<Integer>("CommandUtil.commandTimeout", 3000));
    // Command options
    public static final List<AICommand.Option> queueOptionList = Arrays.asList(new AICommand.Option[]{AICommand.Option.SHIFT_KEY});
    public static final int queueOption = AICommand.Option.getBitField(queueOptionList);
    // Move states
    public static final int MOVE_STATE_HOLD_POSITION = 0;
    public static final int MOVE_STATE_MANEUVER = 1;
    public static final int MOVE_STATE_ROAM = 2;
    // Building facing directions
    public static final int FACING_NORTH = 0;
    public static final int FACING_EAST = 1;
    public static final int FACING_SOUTH = 2;
    public static final int FACING_WEST = 3;

    public static int getMapCenterFacing(AIDelegate aiDelegate, CachedUnit unit) {
        double direction = BuilderUtil.getMapCenterDirection(unit.getPos());
//        System.err.println("direction: " + direction);
        if (direction >= 45 && direction < 135) {
            return FACING_SOUTH;
        }
        if (direction >= 135 && direction < 225) {
            return FACING_WEST;
        }
        if (direction >= 225 && direction < 315) {
            return FACING_NORTH;
        }
        return FACING_EAST;
    }

    private static void handleCommand(AIDelegate aiDelegate, AICommand command) {
        try {
            aiDelegate.handleCommand(command);
        } catch (AICommandException ex) {
            if (GlobalOptions.isDebug()) {
                ex.printStackTrace();
            } else {
                System.err.println(ex.getMessage());
            }
        }
    }

    public static void guard(AIDelegate aiDelegate, CachedUnit unit, CachedUnit guard, boolean queue) {
        AICommand command = new GuardUnitAICommand(unit.getUnitId(), -1, queue ? queueOption : 0,
                commandTimeout.getValue(), guard.getUnitId());
        handleCommand(aiDelegate, command);
    }

    public static void patrol(AIDelegate aiDelegate, CachedUnit unit, AIFloat3 pos, boolean queue) {
        AICommand command = new PatrolUnitAICommand(unit.getUnitId(), -1, queue ? queueOption : 0,
                commandTimeout.getValue(), pos);
        handleCommand(aiDelegate, command);
    }

    public static void stop(AIDelegate aiDelegate, CachedUnit unit) {
        AICommand command = new StopUnitAICommand(unit.getUnitId(), -1, 0,
                commandTimeout.getValue());
        handleCommand(aiDelegate, command);
    }

    public static void attack(AIDelegate aiDelegate, CachedUnit unit, CachedUnit attackUnit, boolean queue) {
        AICommand command = new AttackUnitAICommand(unit.getUnitId(), -1, queue ? queueOption : 0,
                commandTimeout.getValue(), attackUnit.getUnitId());
        try {
            handleCommand(aiDelegate, command);
        } catch (Exception e) {
            System.err.println(unit.getDef().getHumanName() + ", " + attackUnit.getUnitId());
        }
    }

    public static void fight(AIDelegate aiDelegate, CachedUnit unit, AIFloat3 pos, boolean queue) {
        AICommand command = new FightUnitAICommand(unit.getUnitId(), -1, queue ? queueOption : 0,
                commandTimeout.getValue(), pos);
        try {
            handleCommand(aiDelegate, command);
        } catch (Exception e) {
            System.err.println(unit.getDef().getHumanName() + "::" + FormatUtil.formatAIFloat3(pos));
        }
    }

    public static void move(AIDelegate aiDelegate, CachedUnit unit, AIFloat3 pos, boolean queue) {
        AICommand command = new MoveUnitAICommand(unit.getUnitId(), -1, queue ? queueOption : 0,
                commandTimeout.getValue(), pos);
        handleCommand(aiDelegate, command);
    }

    public static void reclaim(AIDelegate aiDelegate, CachedUnit unit, CachedUnit reclaim, boolean queue) {
        AICommand command = new ReclaimUnitAICommand(unit.getUnitId(), -1, queue ? queueOption : 0,
                commandTimeout.getValue(), reclaim.getUnitId());
        handleCommand(aiDelegate, command);
    }

    public static void reclaimArea(AIDelegate aiDelegate, CachedUnit unit, AIFloat3 pos, float radius, boolean queue) {
        AICommand command = new ReclaimAreaUnitAICommand(unit.getUnitId(), -1, queue ? queueOption : 0,
                commandTimeout.getValue(), pos, radius);
        handleCommand(aiDelegate, command);
    }

    public static void setMoveState(AIDelegate aiDelegate, CachedUnit unit, int moveState) {
        AICommand command = new SetMoveStateUnitAICommand(unit.getUnitId(), -1,
                0, commandTimeout.getValue(), moveState);
        handleCommand(aiDelegate, command);
    }

    public static void setOnOff(AIDelegate aiDelegate, CachedUnit unit, boolean on) {
        AICommand command = new SetOnOffUnitAICommand(unit.getUnitId(), -1,
                0, commandTimeout.getValue(), on);
        handleCommand(aiDelegate, command);
    }

    public static void queueUnit(AIDelegate aiDelegate, CachedUnit builder, CachedUnitDef def, AIFloat3 pos) {
        queueUnit(aiDelegate, builder, def, pos, 0);
    }

    public static void queueUnit(AIDelegate aiDelegate, CachedUnit builder, CachedUnitDef def, AIFloat3 pos, int facing) {
        AICommand command = new BuildUnitAICommand(builder.getUnitId(), -1,
                queueOption, commandTimeout.getValue(), def.getUnitDefId(), pos, facing);
        handleCommand(aiDelegate, command);
    }

    public static void buildUnit(AIDelegate aiDelegate, CachedUnit builder, CachedUnitDef def, AIFloat3 pos) {
        buildUnit(aiDelegate, builder, def, pos, 0);
    }

    public static void buildUnit(AIDelegate aiDelegate, CachedUnit builder, CachedUnitDef def, AIFloat3 pos, int facing) {
        AICommand command = new BuildUnitAICommand(builder.getUnitId(), -1,
                0, commandTimeout.getValue(), def.getUnitDefId(), pos, facing);
        handleCommand(aiDelegate, command);
    }

    public static void drawPoint(AIDelegate aiDelegate, AIFloat3 pos, String label) {
        AICommand command = new AddPointDrawAICommand(pos, label);
        handleCommand(aiDelegate, command);
    }

    public static void drawLine(AIDelegate aiDelegate, AIFloat3 from, AIFloat3 to) {
        AICommand command = new AddLineDrawAICommand(from, to);
        handleCommand(aiDelegate, command);
    }

    public static void factoryBuild(AIDelegate aiDelegate, String name, CachedUnit factory) {
        CachedUnitDef def = GlobalDelegate.getUnitDef(name, factory.getFaction());
        AICommand command = new BuildUnitAICommand(factory.getUnitId(), -1, 0,
                commandTimeout.getValue(), def.getUnitDefId(), new AIFloat3(), 0);
        handleCommand(aiDelegate, command);
    }
}
