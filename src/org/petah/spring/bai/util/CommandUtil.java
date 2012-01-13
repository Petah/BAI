package org.petah.spring.bai.util;

import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.delegate.AIDelegate;

/**
 *
 * @author Petah
 */
public class CommandUtil {

    // Options
    private static Option<Integer> commandTimeout = OptionsManager.getOption(
            new Option<Integer>("CommandUtil.commandTimeout", 3000));

    // Move states
    public static final int MOVE_STATE_HOLD_POSITION    = 0;
    public static final int MOVE_STATE_MANEUVER         = 1;
    public static final int MOVE_STATE_ROAM             = 2;

    // Building facing directions
    public static final int FACING_NORTH    = 0;
    public static final int FACING_EAST     = 1;
    public static final int FACING_SOUTH    = 2;
    public static final int FACING_WEST     = 3;

    // Bits for the option field of a command
    public static final int META_KEY        = (1 << 2); // 4
    public static final int DONT_REPEAT     = (1 << 3); // 8
    public static final int RIGHT_MOUSE_KEY = (1 << 4); // 16
    public static final int SHIFT_KEY       = (1 << 5); // 32
    public static final int CONTROL_KEY     = (1 << 6); // 64
    public static final int ALT_KEY         = (1 << 7); // 128

    // Option definitions
    public static final short OPT_NONE = 0;
    public static final short OPT_QUEUE = SHIFT_KEY;

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

}
