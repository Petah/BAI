/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.map.control;

import com.springrts.ai.oo.AIFloat3;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import org.petah.common.event.UpdateAdapter;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.common.util.profiler.Profiler;
import org.petah.spring.bai.AIReturnCode;
import org.petah.spring.bai.ThreadManager;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.delegate.GlobalDelegate;
import org.petah.spring.bai.delegate.TeamDelegate;
import org.petah.spring.bai.listener.UpdateEventListener;
import org.petah.spring.bai.util.FormatUtil;
import org.petah.spring.bai.util.MapUtil;

/**
 *
 * @author Petah
 */
public class ControlMap extends UpdateAdapter implements UpdateEventListener {

    // Control map options
    private static Option<Integer> updateTime = OptionsManager.getOption(
            new Option<Integer>("ControlMap.updateTime", 40));
    private static Option<Integer> unknownUnitPower = OptionsManager.getOption(
            new Option<Integer>("ControlMap.unknownUnitPower", 100),
            new Option<String>("description", "Amount of power to allocate a unit when it is not known (In radar not LOS)."));
    // Class properties
    private TeamDelegate teamDelegate;
    /**
     * Control zones
     */
    private final Map<Point, ControlZone> controlZones = new ConcurrentHashMap<Point, ControlZone>();
    /**
     * Next frame to update the control map on
     */
    private int nextUpdate = 0;
    /**
     * Future object returned from the update thread
     */
    private Future updateFuture;

    public ControlMap(TeamDelegate teamDelegate) {
        this.teamDelegate = teamDelegate;
        Profiler.start(ControlMap.class, "ControlMap()");
        createControlZones();
        Profiler.stop(ControlMap.class, "ControlMap()");
    }

    /**
     * Updates the control map periodically.
     * @param frame the current frame
     */
    public int update(int frame) {
        if (nextUpdate <= frame) {
            nextUpdate = frame + updateTime.getValue();
            if (updateFuture == null || updateFuture.isDone()) {
                updateFuture = ThreadManager.run("ControlMap.update()", new Runnable() {

                    public void run() {
                        update();
                    }
                });
            }
        }
        return AIReturnCode.NORMAL;
    }

    private ControlZone getUnitZone(CachedUnit unit) {
        AIFloat3 pos = unit.getPos();
        Point point = new Point((int) (MapUtil.terrainToMap(pos.x) / ControlZone.getSize()), (int) (MapUtil.terrainToMap(pos.z) / ControlZone.getSize()));
        ControlZone zone = controlZones.get(point);
        if (zone.getX() == 0 || zone.getY() == 0) {
            // TODO: fix this bug
//            System.err.println(FormatUtil.formatAIFloat3(pos));
//            System.err.println(MapUtil.terrainToMap(pos.x) + "\t/ " + ControlZone.getSize() + "\t, " + MapUtil.terrainToMap(pos.z) + "\t/ " + ControlZone.getSize());
        }
//        if (zone == null) {
//            Logger.getLogger(ControlMap.class.getName()).warning("Unit outside all valid control zones: " +
//                    unit.getDef().getHumanName() + " " + pos.x + ", " + pos.z);
//        }
        return zone;
    }

    private void updateZones(CachedUnit unit, boolean friendly) {
        ControlZone zone = getUnitZone(unit);
//        System.err.println("ControlMap.updateZones(" + unit.getUnitId() + ", " + zone.getX() + ", " + zone.getY() + ")");
        if (zone != null) {
            if (friendly) {
                zone.incPower(unit.getPower());
            } else {
                zone.decPower(unit.getPower() == -1 ? unknownUnitPower.getValue() : unit.getPower());
            }
        }
    }

    private void createControlZones() {
        for (int x = 0; x < GlobalDelegate.getMapWidth() / ControlZone.getSize(); x++) {
            for (int y = 0; y < GlobalDelegate.getMapHeight() / ControlZone.getSize(); y++) {
                controlZones.put(new Point(x, y), new ControlZone(x, y));
            }
        }
    }

    /**
     * Updates the ControlMap's state.
     */
    private void update() {
        Profiler.start(ControlMap.class, "update()");
        for (ControlZone zone : controlZones.values()) {
            zone.reset();
        }
        for (CachedUnit unit : teamDelegate.getFriendlyUnits().values()) {
            updateZones(unit, true);
        }
        for (CachedUnit unit : teamDelegate.getEnemyUnits().values()) {
            updateZones(unit, false);
        }
        // Update listeners
        fireUpdate(this);
        Profiler.stop(ControlMap.class, "update()");
    }

    public Collection<ControlZone> getControlZones() {
        return controlZones.values();
    }

    public List<ControlZone> getEnemyControlZones() {
        List<ControlZone> enemyControlZones = new ArrayList<ControlZone>();
        for (ControlZone zone : controlZones.values()) {
            if (zone.isEnemy()) {
                enemyControlZones.add(zone);
            }
        }
        return enemyControlZones;
    }

    public List<ControlZone> getFriendlyControlZones() {
        List<ControlZone> friendlyControlZones = new ArrayList<ControlZone>();
        for (ControlZone zone : controlZones.values()) {
            if (zone.isFriendly()) {
                friendlyControlZones.add(zone);
            }
        }
        return friendlyControlZones;
    }

    public List<ControlZone> getNeutralControlZones() {
        List<ControlZone> neutralControlZones = new ArrayList<ControlZone>();
        for (ControlZone zone : controlZones.values()) {
            if (zone.isNeutral()) {
                neutralControlZones.add(zone);
            }
        }
        return neutralControlZones;
    }
}
