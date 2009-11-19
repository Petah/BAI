/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//importPackage(org.petah.spring.bai);
//importPackage(org.petah.spring.bai.cache);
//importPackage(org.petah.spring.bai.delegate);
//importPackage(org.petah.spring.bai.group);
//importPackage(org.petah.spring.bai.map.control);
//importPackage(org.petah.spring.bai.map.height);
//importPackage(org.petah.spring.bai.map.metal);
//importPackage(org.petah.spring.bai.map.slope);
//importPackage(org.petah.spring.bai.map.target);
//importPackage(org.petah.spring.bai.tasks);
//importPackage(org.petah.spring.bai.unit);
//importPackage(org.petah.spring.bai.util);
//importPackage(org.petah.common.util);

var DefaultScout = {

    nextUpdate: 0,

    update: function(group, frame) {
        if (this.nextUpdate <= frame) {
            this.nextUpdate = frame + 200;
            scoutClosestZone(group);
        }
        return false;
    }
};

function scoutClosestZone(group) {
    var zones = aiDelegate.getNeutralControlZones();
    zones.addAll(aiDelegate.getEnemyControlZones());
    for (var i = 0; i < group.size(); i++) {
        var unit = group.getUnit(i);
        if (unit.getCurrentCommands().size() == 0) {
            var controlZone = getClosestControlZone(zones, unit.getPos(), -5);
            if (controlZone != null) {
                var moveTo = new com.springrts.ai.AIFloat3(controlZone.getTerrainX(), 0, controlZone.getTerrainZ());
                moveTo.x += Math.random() * MapUtil.mapToTerrain(ControlZone.getSize());
                moveTo.z += Math.random() * MapUtil.mapToTerrain(ControlZone.getSize());
                CommandUtil.move(aiDelegate, unit, moveTo, false);
            }
        }
    }
}

function getClosestControlZone(zones, pos, maxAge) {
    var closest = null;
    var closestDistance = null;
    for (var i = 0; i < zones.size(); i++) {
        var zone = zones.get(i);
        if (zone.getAge() < maxAge) {
            var distance = GameMath.pointDistance(zone.getTerrainCenterX(), zone.getTerrainCenterZ(), pos.x, pos.z);
            if (closest == null || distance < closestDistance) {
                closestDistance = distance;
                closest = zone;
            }
        }
    }
    return closest;
}
