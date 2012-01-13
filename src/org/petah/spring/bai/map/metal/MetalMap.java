///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.petah.spring.bai.map.metal;
//
//import com.springrts.ai.oo.AIFloat3;
//import java.util.List;
//import java.util.concurrent.CopyOnWriteArrayList;
//import java.util.concurrent.Future;
//import java.util.logging.Logger;
//import org.petah.common.option.Option;
//import org.petah.common.option.OptionsManager;
//import org.petah.common.util.GameMath;
//import org.petah.common.util.profiler.Profiler;
//import org.petah.spring.bai.AIReturnCode;
//import org.petah.spring.bai.ThreadManager;
//import org.petah.spring.bai.cache.CachedUnit;
//import org.petah.spring.bai.delegate.TeamDelegate;
//import org.petah.spring.bai.listener.UpdateEventListener;
//import org.petah.spring.bai.util.ArrayUtil;
//
///**
// *
// * @author Petah
// */
//public class MetalMap implements UpdateEventListener {
//
//    // FIXME:
////    private static CachedMetalMap cachedMetalMap;
//    // Metal map options
////    private static Option<Integer> metalMapUpdateTime = OptionsManager.getOption(
////            new Option<Integer>("metalMapUpdateTime", 150));
//    // Static variables
////    private static int radius;
////    private static float averageMetal;
////    private static long totalMetal;
////    private static short[][] metalMap;
////    private static MetalMapType metalMapType = null;
//    // Class variables
//    private TeamDelegate teamDelegate;
//    private Future updateFuture;
//    private int nextUpdate = 0;
//    private final List<MetalSpot> metalSpots = new CopyOnWriteArrayList<MetalSpot>();
//    private final List<MetalZone> metalZones = new CopyOnWriteArrayList<MetalZone>();
//
//    public MetalMap(TeamDelegate teamManager) {
//        this.teamDelegate = teamManager;
//        Profiler.start(MetalMap.class, "MetalMap()", "Processing metal spots/zones.");
//        switch (metalMapType) {
//            case Normal:
//                metalSpots.clear();
//                metalSpots.addAll(MetalSpot.getBestMetalSpots());
//                break;
//            case Metal:
//                metalZones.clear();
//                metalZones.addAll(MetalZone.getBestMetalZones());
//                break;
//        }
//        Profiler.stop(MetalMap.class, "MetalMap()");
//    }
//
//    public int update(int frame) {
//        if (nextUpdate <= frame) {
//            nextUpdate = frame + metalMapUpdateTime.getValue();
//            if (updateFuture == null || updateFuture.isDone()) {
//                updateFuture = ThreadManager.run("MetalMap.update()", new Runnable() {
//
//                    public void run() {
//                        updateMetalSpots();
//                    }
//                });
//            }
//        }
//        return AIReturnCode.NORMAL;
//    }
//
////    private boolean isMetalSpotCaptured(MetalSpot metalSpot) {
////        float yPos = HeightMap.getElevationAt(metalSpot.getTerrainX(), metalSpot.getTerrainZ());
////        AIFloat3 pos = new AIFloat3(metalSpot.getTerrainX(), yPos, metalSpot.getTerrainZ());
////        int searchRadius = getTerrainExtractorRadius() * 2;
////        for (Unit unit : teamDelegate.getCallBack().getFriendlyUnitsIn(pos, searchRadius)) {
////            if (isUnitCapturedMetalSpot(unit, metalSpot)) {
////                metalSpot.setCaptured(true);
////                return true;
////            }
////        }
//////        for (Unit unit : bai.getCallBack().getEnemyUnitsIn(
//////                new AIFloat3(metalSpot.getTerrainX(), 0, metalSpot.getTerrainZ()), getTerrainExtractorRadius() * 2)) {
//////            if (isUnitCapturedMetalSpot(unit, metalSpot)) {
//////                return true;
//////            }
//////        }
//////        for (Unit unit : bai.getCallBack().getNeutralUnitsIn(
//////                new AIFloat3(metalSpot.getTerrainX(), 0, metalSpot.getTerrainZ()), getTerrainExtractorRadius())) {
//////            if (isUnitCapturedMetalSpot(unit, metalSpot)) {
//////                return true;
//////            }
//////        }
////        metalSpot.setCaptured(false);
////        return false;
////    }
////    private boolean isUnitCapturedMetalSpot(Unit unit, MetalSpot metalSpot) {
////        if (unit.getDef().getExtractsResource(ResourceManager.getMetal()) > 0) {
////            AIFloat3 pos = unit.getPos();
////            if (GameMath.pointDistance(pos.x, pos.z,
////                    metalSpot.getTerrainX(), metalSpot.getTerrainZ()) <= getTerrainExtractorRadius()) {
////                return true;
////            }
////        }
////        return false;
////    }
//    private MetalSpot findClosestMetalSpot(AIFloat3 pos) {
//        Profiler.start(MetalMap.class, "findClosestMetalSpot()");
//        MetalSpot closestMetalSpot = null;
//        float closestDistance = Float.MAX_VALUE;
//        for (MetalSpot metalSpot : metalSpots) {
//            float distance = GameMath.pointDistance(pos.x, pos.z, metalSpot.getTerrainX(), metalSpot.getTerrainZ());
//            if (closestMetalSpot == null || distance < closestDistance) {
//                closestMetalSpot = metalSpot;
//                closestDistance = distance;
//            }
//        }
//        Profiler.stop(MetalMap.class, "findClosestMetalSpot()");
//        return closestMetalSpot;
//    }
//
//    private void updateMetalSpots() {
//        Profiler.start(MetalMap.class, "updateMetalSpots()");
//        // Reset all metal spots
//        for (MetalSpot metalSpot : metalSpots) {
//            metalSpot.setCaptured(false);
//        }
//        // Loop all friendly units
//        for (CachedUnit unit : teamDelegate.getFriendlyUnits()) {
//            // If the unit is a metal extractor
//            if (unit.getDef().getExtractsMetal() > 0) {
//                AIFloat3 pos = unit.getPos();
//                MetalSpot closestMetalSpot = findClosestMetalSpot(pos);
//                float distance = GameMath.pointDistance(pos.x, pos.z, closestMetalSpot.getTerrainX(), closestMetalSpot.getTerrainZ());
//                if (distance < CachedMetalMap.getMetalExtractorRadius() * 2) {
//                    closestMetalSpot.setCaptured(true);
//                }
//            }
//        }
//        Profiler.stop(MetalMap.class, "updateMetalSpots()");
//    }
//
//    public MetalSpot findFreeMetalSpot(AIFloat3 pos) {
//        Profiler.start(MetalMap.class, "findFreeMetalSpot()");
//        MetalSpot closestMetalSpot = null;
//        float closestDistance = Float.MAX_VALUE;
//        for (MetalSpot metalSpot : metalSpots) {
//            if (!metalSpot.isCaptured()) {
//                float distance = GameMath.pointDistance(pos.x, pos.z, metalSpot.getTerrainX(), metalSpot.getTerrainZ());
//                if (closestMetalSpot == null || distance < closestDistance) {
//                    closestMetalSpot = metalSpot;
//                    closestDistance = distance;
//                }
//            }
//        }
//        Profiler.stop(MetalMap.class, "findFreeMetalSpot()");
//        return closestMetalSpot;
//    }
////    public MetalSpot getNearestMetalSpot(AIFloat3 pos) {
////        Profiler.start(MetalMap.class, "getNearestMetalSpot()");
////        MetalSpot closest = null;
////        for (MetalSpot metalSpot : metalSpots) {
////            if (!isMetalSpotCaptured(metalSpot)) {
////                if (closest == null) {
////                    closest = metalSpot;
////                } else {
////                    if (GameMath.pointDistance(pos.x, pos.z, metalSpot.getTerrainX(), metalSpot.getTerrainZ()) <
////                            GameMath.pointDistance(pos.x, pos.z, closest.getTerrainX(), closest.getTerrainZ())) {
////                        closest = metalSpot;
////                    }
////                }
////            }
////        }
////        Profiler.stop(MetalMap.class, "getNearestMetalSpot()");
////        return closest;
////    }
//
//    /**
//     * Initializes the metal map.
//     * @param callback
//     */
////    public static void init() {
////        Logger.getLogger(MetalMap.class.getName()).entering(MetalMap.class.getName(), "init()");
////        if (metalMapType == null) {
////            Profiler.start(MetalMap.class, "init()");// Normalise metal map values
////            radius = (int) (cachedMetalMap.getMetalExtractorRadius() / 20);
////            // Create 2D signed metal map
////            long total = 0;
////            short[][] metalMap2D = new short[cachedMetalMap.getWidth()][cachedMetalMap.getHeight()];
////            for (int y = 0; y < cachedMetalMap.getHeight(); y++) {
////                for (int x = 0; x < cachedMetalMap.getWidth(); x++) {
////                    short value = cachedMetalMap.getValue(x, y);//getMetalMap().get(ArrayUtil.get1DIndex(x, y, cachedMetalMap.getWidth()));
////                    if (value == -1) {
////                        value = 255;
////                    } else if (value < -1) {
////                        value += 256;
////                    }
////                    total += value;
////                    metalMap2D[x][y] = value;
////                }
////            }
////            // Initializes metal map values
////            MetalMap.radius = radius;
////            MetalMap.averageMetal = (float) total / (float) CachedMetalMap.getMetalMap().size();
////            MetalMap.totalMetal = total;
////            MetalMap.metalMap = metalMap2D;
////            MetalMap.metalMapType = MetalSpotFinder.getMetalMapType(cachedMetalMap);//MetalMapType.getMetalMapType();
////            Profiler.stop(MetalMap.class, "init()");
////        }
////    }
//
////    public static short getValue(int x, int y) {
////        return metalMap[x][y];
////    }
//
////    public static int getExtractorRadius() {
////        return radius;
////    }
////
////    public static MetalMapType getMetalMapType() {
////        return metalMapType;
////    }
//
////    public static float getAverageMetal() {
////        return averageMetal;
////    }
////
////    public static long getTotalMetal() {
////        return totalMetal;
////    }
////
////    public List<MetalSpot> getMetalSpots() {
////        return metalSpots;
////    }
////
////    public List<MetalZone> getMetalZones() {
////        return metalZones;
////    }
//}
