package org.petah.spring.bai.cache;

import com.springrts.ai.oo.AIFloat3;
import com.springrts.ai.oo.clb.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class CachedUnitDef implements Serializable {

    // Serialization ID
    public static final long serialVersionUID = 5L;
    private transient UnitDef def;

    public CachedUnitDef(UnitDef def) {
        this.def = def;
    }

    public UnitDef getUnitDef() {
        return def;
    }

    public boolean isUseBuildingGroundDecal() {
        return def.isUseBuildingGroundDecal();
    }

    public boolean isUpright() {
        return def.isUpright();
    }

    public boolean isTurnInPlace() {
        return def.isTurnInPlace();
    }

    public boolean isTransportByEnemy() {
        return def.isTransportByEnemy();
    }

    public boolean isTargetingFacility() {
        return def.isTargetingFacility();
    }

    public boolean isStrafeToAttack() {
        return def.isStrafeToAttack();
    }

    public boolean isStealth() {
        return def.isStealth();
    }

    public boolean isStartCloaked() {
        return def.isStartCloaked();
    }

    public boolean isSquareResourceExtractor(Resource rsrc) {
        return def.isSquareResourceExtractor(rsrc);
    }

    public boolean isSonarStealth() {
        return def.isSonarStealth();
    }

    public boolean isShowPlayerName() {
        return def.isShowPlayerName();
    }

    public boolean isReleaseHeld() {
        return def.isReleaseHeld();
    }

    public boolean isReclaimable() {
        return def.isReclaimable();
    }

    public boolean isPushResistant() {
        return def.isPushResistant();
    }

    public boolean isOnOffable() {
        return def.isOnOffable();
    }

    public boolean isNotTransportable() {
        return def.isNotTransportable();
    }

    public boolean isNeedGeo() {
        return def.isNeedGeo();
    }

    public boolean isLevelGround() {
        return def.isLevelGround();
    }

    public boolean isLeaveTracks() {
        return def.isLeaveTracks();
    }

    public boolean isHoverAttack() {
        return def.isHoverAttack();
    }

    public boolean isHoldSteady() {
        return def.isHoldSteady();
    }

    public boolean isHideDamage() {
        return def.isHideDamage();
    }

    public boolean isFullHealthFactory() {
        return def.isFullHealthFactory();
    }

    public boolean isFloater() {
        return def.isFloater();
    }

    public boolean isFirePlatform() {
        return def.isFirePlatform();
    }

    public boolean isFeature() {
        return def.isFeature();
    }

    public boolean isFactoryHeadingTakeoff() {
        return def.isFactoryHeadingTakeoff();
    }

    public boolean isDontLand() {
        return def.isDontLand();
    }

    public boolean isDecloakSpherical() {
        return def.isDecloakSpherical();
    }

    public boolean isDecloakOnFire() {
        return def.isDecloakOnFire();
    }

    public boolean isCommander() {
        return def.isCommander();
    }

    public boolean isCollide() {
        return def.isCollide();
    }

    public boolean isCapturable() {
        return def.isCapturable();
    }

    public boolean isBuilder() {
        return def.isBuilder();
    }

    public boolean isBuildRange3D() {
        return def.isBuildRange3D();
    }

    public boolean isAssistable() {
        return def.isAssistable();
    }

    public boolean isAirStrafe() {
        return def.isAirStrafe();
    }

    public boolean isAirBase() {
        return def.isAirBase();
    }

    public boolean isActivateWhenBuilt() {
        return def.isActivateWhenBuilt();
    }

    public boolean isAbleToSubmerge() {
        return def.isAbleToSubmerge();
    }

    public boolean isAbleToSelfRepair() {
        return def.isAbleToSelfRepair();
    }

    public boolean isAbleToSelfD() {
        return def.isAbleToSelfD();
    }

    public boolean isAbleToResurrect() {
        return def.isAbleToResurrect();
    }

    public boolean isAbleToRestore() {
        return def.isAbleToRestore();
    }

    public boolean isAbleToRepeat() {
        return def.isAbleToRepeat();
    }

    public boolean isAbleToRepair() {
        return def.isAbleToRepair();
    }

    public boolean isAbleToReclaim() {
        return def.isAbleToReclaim();
    }

    public boolean isAbleToPatrol() {
        return def.isAbleToPatrol();
    }

    public boolean isAbleToMove() {
        return def.isAbleToMove();
    }

    public boolean isAbleToLoopbackAttack() {
        return def.isAbleToLoopbackAttack();
    }

    public boolean isAbleToKamikaze() {
        return def.isAbleToKamikaze();
    }

    public boolean isAbleToHover() {
        return def.isAbleToHover();
    }

    public boolean isAbleToGuard() {
        return def.isAbleToGuard();
    }

    public boolean isAbleToFly() {
        return def.isAbleToFly();
    }

    public boolean isAbleToFireControl() {
        return def.isAbleToFireControl();
    }

    public boolean isAbleToFight() {
        return def.isAbleToFight();
    }

    public boolean isAbleToDropFlare() {
        return def.isAbleToDropFlare();
    }

    public boolean isAbleToCrash() {
        return def.isAbleToCrash();
    }

    public boolean isAbleToCloak() {
        return def.isAbleToCloak();
    }

    public boolean isAbleToCapture() {
        return def.isAbleToCapture();
    }

    public boolean isAbleToAttack() {
        return def.isAbleToAttack();
    }

    public boolean isAbleToAssist() {
        return def.isAbleToAssist();
    }

    public int getZSize() {
        return def.getZSize();
    }

    public List<Short> getYardMap(int i) {
        return def.getYardMap(i);
    }

    public int getXSize() {
        return def.getXSize();
    }

    public String getWreckName() {
        return def.getWreckName();
    }

    public float getWingDrag() {
        return def.getWingDrag();
    }

    public float getWingAngle() {
        return def.getWingAngle();
    }

    public float getWindResourceGenerator(Resource rsrc) {
        return def.getWindResourceGenerator(rsrc);
    }

    public List<WeaponMount> getWeaponMounts() {
        return def.getWeaponMounts();
    }

    public float getWaterline() {
        return def.getWaterline();
    }

    public float getWantedHeight() {
        return def.getWantedHeight();
    }

    public float getVerticalSpeed() {
        return def.getVerticalSpeed();
    }

    public float getUpkeep(Resource rsrc) {
        return def.getUpkeep(rsrc);
    }

    public float getUnloadSpread() {
        return def.getUnloadSpread();
    }

    public float getUnitFallSpeed() {
        return def.getUnitFallSpeed();
    }

    public int getUnitDefId() {
        return def.getUnitDefId();
    }

    public String getType() {
        return def.getType();
    }

    public float getTurnRate() {
        return def.getTurnRate();
    }

    public float getTurnRadius() {
        return def.getTurnRadius();
    }

    public float getTurnInPlaceSpeedLimit() {
        return def.getTurnInPlaceSpeedLimit();
    }

    public float getTurnInPlaceDistance() {
        return def.getTurnInPlaceDistance();
    }

    public int getTransportUnloadMethod() {
        return def.getTransportUnloadMethod();
    }

    public int getTransportSize() {
        return def.getTransportSize();
    }

    public float getTransportMass() {
        return def.getTransportMass();
    }

    public int getTransportCapacity() {
        return def.getTransportCapacity();
    }

    public float getTrackWidth() {
        return def.getTrackWidth();
    }

    public int getTrackType() {
        return def.getTrackType();
    }

    public float getTrackStretch() {
        return def.getTrackStretch();
    }

    public float getTrackStrength() {
        return def.getTrackStrength();
    }

    public float getTrackOffset() {
        return def.getTrackOffset();
    }

    public String getTooltip() {
        return def.getTooltip();
    }

    public float getTidalResourceGenerator(Resource rsrc) {
        return def.getTidalResourceGenerator(rsrc);
    }

    public float getTerraformSpeed() {
        return def.getTerraformSpeed();
    }

    public int getTechLevel() {
        return def.getTechLevel();
    }

    public float getStorage(Resource rsrc) {
        return def.getStorage(rsrc);
    }

    public WeaponDef getStockpileDef() {
        return def.getStockpileDef();
    }

    public float getSpeedToFront() {
        return def.getSpeedToFront();
    }

    public float getSpeed() {
        return def.getSpeed();
    }

    public int getSonarRadius() {
        return def.getSonarRadius();
    }

    public int getSonarJamRadius() {
        return def.getSonarJamRadius();
    }

    public float getSlideTolerance() {
        return def.getSlideTolerance();
    }

    public WeaponDef getShieldDef() {
        return def.getShieldDef();
    }

    public String getSelfDExplosion() {
        return def.getSelfDExplosion();
    }

    public int getSelfDCountdown() {
        return def.getSelfDCountdown();
    }

    public float getSeismicSignature() {
        return def.getSeismicSignature();
    }

    public int getSeismicRadius() {
        return def.getSeismicRadius();
    }

    public float getResurrectSpeed() {
        return def.getResurrectSpeed();
    }

    public float getResourceMake(Resource rsrc) {
        return def.getResourceMake(rsrc);
    }

    public float getResourceExtractorRange(Resource rsrc) {
        return def.getResourceExtractorRange(rsrc);
    }

    public float getRepairSpeed() {
        return def.getRepairSpeed();
    }

    public float getRefuelTime() {
        return def.getRefuelTime();
    }

    public float getReclaimSpeed() {
        return def.getReclaimSpeed();
    }

    public float getRadius() {
        return def.getRadius();
    }

    public int getRadarRadius() {
        return def.getRadarRadius();
    }

    public float getPower() {
        return def.getPower();
    }

    public int getNoChaseCategory() {
        return def.getNoChaseCategory();
    }

    public String getName() {
        return def.getName();
    }

    public float getMyGravity() {
        return def.getMyGravity();
    }

    public int getMoveState() {
        return def.getMoveState();
    }

    public MoveData getMoveData() {
        return def.getMoveData();
    }

    public float getMinWaterDepth() {
        return def.getMinWaterDepth();
    }

    public int getMinTransportSize() {
        return def.getMinTransportSize();
    }

    public float getMinTransportMass() {
        return def.getMinTransportMass();
    }

    public float getMinCollisionSpeed() {
        return def.getMinCollisionSpeed();
    }

    public float getMinAirBasePower() {
        return def.getMinAirBasePower();
    }

    public float getMaxWeaponRange() {
        return def.getMaxWeaponRange();
    }

    public float getMaxWaterDepth() {
        return def.getMaxWaterDepth();
    }

    public int getMaxThisUnit() {
        return def.getMaxThisUnit();
    }

    public float getMaxSlope() {
        return def.getMaxSlope();
    }

    public float getMaxRudder() {
        return def.getMaxRudder();
    }

    public float getMaxRepairSpeed() {
        return def.getMaxRepairSpeed();
    }

    public float getMaxPitch() {
        return def.getMaxPitch();
    }

    public float getMaxHeightDif() {
        return def.getMaxHeightDif();
    }

    public float getMaxFuel() {
        return def.getMaxFuel();
    }

    public float getMaxElevator() {
        return def.getMaxElevator();
    }

    public float getMaxDeceleration() {
        return def.getMaxDeceleration();
    }

    public float getMaxBank() {
        return def.getMaxBank();
    }

    public float getMaxAileron() {
        return def.getMaxAileron();
    }

    public float getMaxAcceleration() {
        return def.getMaxAcceleration();
    }

    public float getMass() {
        return def.getMass();
    }

    public float getMakesResource(Resource rsrc) {
        return def.getMakesResource(rsrc);
    }

    public float getLosRadius() {
        return def.getLosRadius();
    }

    public float getLosHeight() {
        return def.getLosHeight();
    }

    public float getLoadingRadius() {
        return def.getLoadingRadius();
    }

    public float getKamikazeDist() {
        return def.getKamikazeDist();
    }

    public int getJammerRadius() {
        return def.getJammerRadius();
    }

    public int getIdleTime() {
        return def.getIdleTime();
    }

    public float getIdleAutoHeal() {
        return def.getIdleAutoHeal();
    }

    public String getHumanName() {
        return def.getHumanName();
    }

    public int getHighTrajectoryType() {
        return def.getHighTrajectoryType();
    }

    public float getHeight() {
        return def.getHeight();
    }

    public float getHealth() {
        return def.getHealth();
    }

    public String getGaia() {
        return def.getGaia();
    }

    public float getFrontToSpeed() {
        return def.getFrontToSpeed();
    }

    public int getFlareTime() {
        return def.getFlareTime();
    }

    public int getFlareSalvoSize() {
        return def.getFlareSalvoSize();
    }

    public int getFlareSalvoDelay() {
        return def.getFlareSalvoDelay();
    }

    public float getFlareReloadTime() {
        return def.getFlareReloadTime();
    }

    public float getFlareEfficiency() {
        return def.getFlareEfficiency();
    }

    public AIFloat3 getFlareDropVector() {
        return def.getFlareDropVector();
    }

    public float getFlareDelay() {
        return def.getFlareDelay();
    }

    public FlankingBonus getFlankingBonus() {
        return def.getFlankingBonus();
    }

    public int getFireState() {
        return def.getFireState();
    }

    public String getFileName() {
        return def.getFileName();
    }

    public float getFallSpeed() {
        return def.getFallSpeed();
    }

    public float getExtractsResource(Resource rsrc) {
        return def.getExtractsResource(rsrc);
    }

    public float getDrag() {
        return def.getDrag();
    }

    public float getDlHoverFactor() {
        return def.getDlHoverFactor();
    }

    public UnitDef getDecoyDef() {
        return def.getDecoyDef();
    }

    public float getDecloakDistance() {
        return def.getDecloakDistance();
    }

    public String getDeathExplosion() {
        return def.getDeathExplosion();
    }

    public Map<String, String> getCustomParams() {
        return def.getCustomParams();
    }

    public float getCost(Resource rsrc) {
        return def.getCost(rsrc);
    }

    public int getCobId() {
        return def.getCobId();
    }

    public float getCloakCostMoving() {
        return def.getCloakCostMoving();
    }

    public float getCloakCost() {
        return def.getCloakCost();
    }

    public String getCategoryString() {
        return def.getCategoryString();
    }

    public int getCategory() {
        return def.getCategory();
    }

    public float getCaptureSpeed() {
        return def.getCaptureSpeed();
    }

    public int getBuildingDecalType() {
        return def.getBuildingDecalType();
    }

    public int getBuildingDecalSizeY() {
        return def.getBuildingDecalSizeY();
    }

    public int getBuildingDecalSizeX() {
        return def.getBuildingDecalSizeX();
    }

    public float getBuildingDecalDecaySpeed() {
        return def.getBuildingDecalDecaySpeed();
    }

    public float getBuildTime() {
        return def.getBuildTime();
    }

    public float getBuildSpeed() {
        return def.getBuildSpeed();
    }

    public List<UnitDef> getBuildOptions() {
        return def.getBuildOptions();
    }

    public float getBuildDistance() {
        return def.getBuildDistance();
    }

    public int getBuildAngle() {
        return def.getBuildAngle();
    }

    public float getAutoHeal() {
        return def.getAutoHeal();
    }

    public float getArmoredMultiple() {
        return def.getArmoredMultiple();
    }

    public int getArmorType() {
        return def.getArmorType();
    }

    public float getAirLosRadius() {
        return def.getAirLosRadius();
    }

    public int getAiHint() {
        return def.getAiHint();
    }

    public boolean canManualFire() {
        return def.canManualFire();
    }
}
