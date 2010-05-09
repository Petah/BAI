package org.petah.spring.bai.cache;

import com.springrts.ai.oo.UnitDef;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import org.petah.spring.bai.ResourceManager;
import org.petah.spring.bai.delegate.GlobalDelegate;

public class CachedUnitDef implements Serializable {

    private transient UnitDef def;
//    private transient List<WeaponMount> weaponMounts;
//    private transient CollisionVolume collisionVolume;
//    private transient WeaponDef shieldDef;
//    private transient WeaponDef stockpileDef;
//    private transient FlankingBonus flankingBonus;
//    private transient AIFloat3 flareDropVector;
    private CachedMoveData moveData;
    private int aiHint;
    private float airLosRadius;
    private int armorType;
    private float armoredMultiple;
    private float autoHeal;
    private int buildAngle;
    private float buildDistance;
    private List<Integer> buildOptionIDs;
    private float buildSpeed;
    private float buildTime;
    private float buildingDecalDecaySpeed;
    private int buildingDecalSizeX;
    private int buildingDecalSizeY;
    private int buildingDecalType;
    private float captureSpeed;
    private int category;
    private String categoryString;
    private float cloakCost;
    private float cloakCostMoving;
    private int cobId;
    private float controlRadius;
    private float metalCost;
    private float energyCost;
    private Map<String, String> customParams;
    private String deathExplosion;
    private float decloakDistance;
    private float dlHoverFactor;
    private float drag;
    private float extractsMetal;
    private float fallSpeed;
    private String fileName;
    private int fireState;
    private float flareDelay;
    private float flareEfficiency;
    private float flareReloadTime;
    private int flareSalvoDelay;
    private int flareSalvoSize;
    private int flareTime;
    private float frontToSpeed;
    private String gaia;
    private float health;
    private float height;
    private int highTrajectoryType;
    private String humanName;
    private float idleAutoHeal;
    private int idleTime;
    private int jammerRadius;
    private float kamikazeDist;
    private float loadingRadius;
    private float losHeight;
    private float losRadius;
    private float makesMetal;
    private float makesEnergy;
    private float mass;
    private float maxAcceleration;
    private float maxAileron;
    private float maxBank;
    private float maxDeceleration;
    private float maxElevator;
    private float maxFuel;
    private float maxHeightDif;
    private float maxPitch;
    private float maxRepairSpeed;
    private float maxRudder;
    private float maxSlope;
    private int maxThisUnit;
    private float maxWaterDepth;
    private float maxWeaponRange;
    private float minAirBasePower;
    private float minCollisionSpeed;
    private float minTransportMass;
    private int minTransportSize;
    private float minWaterDepth;
    private int moveState;
    private float myGravity;
    private String name;
    private int noChaseCategory;
    private float power;
    private int radarRadius;
    private float radius;
    private float reclaimSpeed;
    private float refuelTime;
    private float repairSpeed;
    private float metalExtractorRange;
    private float metalMake;
    private float energyMake;
    private float resurrectSpeed;
    private int seismicRadius;
    private float seismicSignature;
    private int selfDCountdown;
    private String selfDExplosion;
    private float slideTolerance;
    private int sonarJamRadius;
    private int sonarRadius;
    private float speed;
    private float speedToFront;
    private float metalStorage;
    private float energyStorage;
    private int techLevel;
    private float terraformSpeed;
    private float tidalResourceGenerator;
    private String tooltip;
    private float trackOffset;
    private float trackStrength;
    private float trackStretch;
    private int trackType;
    private float trackWidth;
    private int transportCapacity;
    private float transportMass;
    private int transportSize;
    private int transportUnloadMethod;
    private float turnInPlaceDistance;
    private float turnInPlaceSpeedLimit;
    private float turnRadius;
    private float turnRate;
    private String type;
    private int unitDefId;
    private float unitFallSpeed;
    private float unloadSpread;
    private float energyUpkeep;
    private float metalUpkeep;
    private float verticalSpeed;
    private float wantedHeight;
    private float waterline;
    private float windResourceGenerator;
    private float wingAngle;
    private float wingDrag;
    private String wreckName;
    private int xSize;
    private int zSize;
    private int hashCode;
    private boolean ableToAssist;
    private boolean ableToAttack;
    private boolean ableToCapture;
    private boolean ableToCloak;
    private boolean ableToCrash;
    private boolean ableToDGun;
    private boolean ableToDropFlare;
    private boolean ableToFight;
    private boolean ableToFireControl;
    private boolean ableToFly;
    private boolean ableToGuard;
    private boolean ableToHover;
    private boolean ableToKamikaze;
    private boolean ableToLoopbackAttack;
    private boolean ableToMove;
    private boolean ableToPatrol;
    private boolean ableToReclaim;
    private boolean ableToRepair;
    private boolean ableToRepeat;
    private boolean ableToRestore;
    private boolean ableToResurrect;
    private boolean ableToSelfD;
    private boolean ableToSelfRepair;
    private boolean ableToSubmerge;
    private boolean activateWhenBuilt;
    private boolean airBase;
    private boolean airStrafe;
    private boolean assistable;
    private boolean buildRange3D;
    private boolean builder;
    private boolean capturable;
    private boolean collide;
    private boolean commander;
    private boolean decloakOnFire;
    private boolean decloakSpherical;
    private boolean dontLand;
    private boolean factoryHeadingTakeoff;
    private boolean feature;
    private boolean firePlatform;
    private boolean floater;
    private boolean fullHealthFactory;
    private boolean hideDamage;
    private boolean holdSteady;
    private boolean hoverAttack;
    private boolean leaveTracks;
    private boolean levelGround;
    private boolean needGeo;
    private boolean notTransportable;
    private boolean onOffable;
    private boolean pushResistant;
    private boolean reclaimable;
    private boolean releaseHeld;
    private boolean showPlayerName;
    private boolean sonarStealth;
    private boolean squareMetalExtractor;
    private boolean startCloaked;
    private boolean stealth;
    private boolean strafeToAttack;
    private boolean targetingFacility;
    private boolean transportByEnemy;
    private boolean turnInPlace;
    private boolean upright;
    private boolean useBuildingGroundDecal;
    private boolean valid;
//    private int decoyDefID;
//    private int moveType;

    public CachedUnitDef(UnitDef def) {
        this.def = def;
        // Parse build options
        buildOptionIDs = new CopyOnWriteArrayList<Integer>();
        for (UnitDef unitDef : def.getBuildOptions()) {
            buildOptionIDs.add(unitDef.getUnitDefId());
        }
        moveData = GlobalDelegate.getCachedMoveData(def.getMoveData());
// FIXME: decoy def crashes JVM
        // Parse decoy unit def
//        decoyDefID = def.getDecoyDef().getUnitDefId();
        // Unused properties
//        collisionVolume = def.getCollisionVolume();
//        flankingBonus = def.getFlankingBonus();
//        flareDropVector = def.getFlareDropVector();
//        shieldDef = def.getShieldDef();
//        stockpileDef = def.getStockpileDef();
//        weaponMounts = def.getWeaponMounts();
        // Standard properties
        aiHint = def.getAiHint();
        airLosRadius = def.getAirLosRadius();
        armorType = def.getArmorType();
        armoredMultiple = def.getArmoredMultiple();
        autoHeal = def.getAutoHeal();
        buildAngle = def.getBuildAngle();
        buildDistance = def.getBuildDistance();
        buildSpeed = def.getBuildSpeed();
        buildTime = def.getBuildTime();
        buildingDecalDecaySpeed = def.getBuildingDecalDecaySpeed();
        buildingDecalSizeX = def.getBuildingDecalSizeX();
        buildingDecalSizeY = def.getBuildingDecalSizeY();
        buildingDecalType = def.getBuildingDecalType();
        captureSpeed = def.getCaptureSpeed();
        category = def.getCategory();
        categoryString = def.getCategoryString();
        cloakCost = def.getCloakCost();
        cloakCostMoving = def.getCloakCostMoving();
        cobId = def.getCobId();
        controlRadius = def.getControlRadius();
        metalCost = def.getCost(ResourceManager.getMetal());
        energyCost = def.getCost(ResourceManager.getEnergy());
        customParams = def.getCustomParams();
        deathExplosion = def.getDeathExplosion();
        decloakDistance = def.getDecloakDistance();
        dlHoverFactor = def.getDlHoverFactor();
        drag = def.getDrag();
        extractsMetal = def.getExtractsResource(ResourceManager.getMetal());
        fallSpeed = def.getFallSpeed();
        fileName = def.getFileName();
        fireState = def.getFireState();
        flareDelay = def.getFlareDelay();
        flareEfficiency = def.getFlareEfficiency();
        flareReloadTime = def.getFlareReloadTime();
        flareSalvoDelay = def.getFlareSalvoSize();
        flareSalvoSize = def.getFlareSalvoSize();
        flareTime = def.getFlareTime();
        frontToSpeed = def.getFrontToSpeed();
        gaia = def.getGaia();
        health = def.getHealth();
        height = def.getHeight();
        highTrajectoryType = def.getHighTrajectoryType();
        humanName = def.getHumanName();
        idleAutoHeal = def.getIdleAutoHeal();
        idleTime = def.getIdleTime();
        jammerRadius = def.getJammerRadius();
        kamikazeDist = def.getKamikazeDist();
        loadingRadius = def.getLoadingRadius();
        losHeight = def.getLosHeight();
        losRadius = def.getLosRadius();
        makesMetal = def.getMakesResource(ResourceManager.getMetal());
        makesEnergy = def.getMakesResource(ResourceManager.getEnergy());
        mass = def.getMass();
        maxAcceleration = def.getMaxAcceleration();
        maxAileron = def.getMaxAileron();
        maxBank = def.getMaxAileron();
        maxDeceleration = def.getMaxDeceleration();
        maxElevator = def.getMaxElevator();
        maxFuel = def.getMaxFuel();
        maxHeightDif = def.getMaxHeightDif();
        maxPitch = def.getMaxPitch();
        maxRepairSpeed = def.getMaxRepairSpeed();
        maxRudder = def.getMaxRudder();
        maxSlope = def.getMaxSlope();
        maxThisUnit = def.getMaxThisUnit();
        maxWaterDepth = def.getMaxWaterDepth();
        maxWeaponRange = def.getMaxWeaponRange();
        minAirBasePower = def.getMinAirBasePower();
        minCollisionSpeed = def.getMinCollisionSpeed();
        minTransportMass = def.getMinTransportMass();
        minTransportSize = def.getMinTransportSize();
        minWaterDepth = def.getMinWaterDepth();
        moveState = def.getMoveState();
        // FIXME: moveType causes null pointer exception
//        moveType = def.getMoveType();
        myGravity = def.getMyGravity();
        name = def.getName();
        noChaseCategory = def.getNoChaseCategory();
        power = def.getPower();
        radarRadius = def.getRadarRadius();
        radius = def.getRadius();
        reclaimSpeed = def.getReclaimSpeed();
        refuelTime = def.getRefuelTime();
        repairSpeed = def.getRepairSpeed();
        metalExtractorRange = def.getResourceExtractorRange(ResourceManager.getMetal());
        metalMake = def.getResourceMake(ResourceManager.getMetal());
        energyMake = def.getResourceMake(ResourceManager.getEnergy());
        resurrectSpeed = def.getResurrectSpeed();
        seismicRadius = def.getSeismicRadius();
        seismicSignature = def.getSeismicSignature();
        selfDCountdown = def.getSelfDCountdown();
        selfDExplosion = def.getSelfDExplosion();
        slideTolerance = def.getSlideTolerance();
        sonarJamRadius = def.getSonarJamRadius();
        sonarRadius = def.getSonarRadius();
        speed = def.getSpeed();
        speedToFront = def.getSpeedToFront();
        metalStorage = def.getStorage(ResourceManager.getMetal());
        energyStorage = def.getStorage(ResourceManager.getEnergy());
        techLevel = def.getTechLevel();
        terraformSpeed = def.getTerraformSpeed();
        tidalResourceGenerator = def.getTidalResourceGenerator(ResourceManager.getEnergy());
        tooltip = def.getTooltip();
        trackOffset = def.getTrackOffset();
        trackStrength = def.getTrackStrength();
        trackStretch = def.getTrackStretch();
        trackType = def.getTrackType();
        trackWidth = def.getTrackWidth();
        transportCapacity = def.getTransportCapacity();
        transportMass = def.getTransportMass();
        transportSize = def.getTransportSize();
        transportUnloadMethod = def.getTransportUnloadMethod();
        turnInPlaceDistance = def.getTurnInPlaceDistance();
        turnInPlaceSpeedLimit = def.getTurnInPlaceSpeedLimit();
        turnRadius = def.getTurnRadius();
        turnRate = def.getTurnRate();
        type = def.getType();
        unitDefId = def.getUnitDefId();
        unitFallSpeed = def.getFallSpeed();
        unloadSpread = def.getUnloadSpread();
        metalUpkeep = def.getUpkeep(ResourceManager.getMetal());
        energyUpkeep = def.getUpkeep(ResourceManager.getEnergy());
        verticalSpeed = def.getVerticalSpeed();
        wantedHeight = def.getWantedHeight();
        waterline = def.getWaterline();
        windResourceGenerator = def.getWindResourceGenerator(ResourceManager.getEnergy());
        wingAngle = def.getWingAngle();
        wingDrag = def.getWingDrag();
        wreckName = def.getWreckName();
        xSize = def.getXSize();
        zSize = def.getZSize();
        ableToAssist = def.isAbleToAssist();
        ableToAttack = def.isAbleToAttack();
        ableToCapture = def.isAbleToCapture();
        ableToCloak = def.isAbleToCloak();
        ableToCrash = def.isAbleToCrash();
        ableToDGun = def.isAbleToDGun();
        ableToDropFlare = def.isAbleToDropFlare();
        ableToFight = def.isAbleToFight();
        ableToFireControl = def.isAbleToFireControl();
        ableToFly = def.isAbleToFly();
        ableToGuard = def.isAbleToGuard();
        ableToHover = def.isAbleToHover();
        ableToKamikaze = def.isAbleToKamikaze();
        ableToLoopbackAttack = def.isAbleToLoopbackAttack();
        ableToMove = def.isAbleToMove();
        ableToPatrol = def.isAbleToPatrol();
        ableToReclaim = def.isAbleToReclaim();
        ableToRepair = def.isAbleToRepair();
        ableToRepeat = def.isAbleToRepeat();
        ableToRestore = def.isAbleToRestore();
        ableToResurrect = def.isAbleToResurrect();
        ableToSelfD = def.isAbleToSelfD();
        ableToSelfRepair = def.isAbleToSelfRepair();
        ableToSubmerge = def.isAbleToSubmerge();
        activateWhenBuilt = def.isActivateWhenBuilt();
        airBase = def.isAirBase();
        airStrafe = def.isAirStrafe();
        assistable = def.isAssistable();
        buildRange3D = def.isBuildRange3D();
        builder = def.isBuilder();
        capturable = def.isCapturable();
        collide = def.isCollide();
        commander = def.isCommander();
        decloakOnFire = def.isDecloakOnFire();
        decloakSpherical = def.isDecloakSpherical();
        dontLand = def.isDontLand();
        factoryHeadingTakeoff = def.isFactoryHeadingTakeoff();
        feature = def.isFeature();
        firePlatform = def.isFirePlatform();
        floater = def.isFloater();
        fullHealthFactory = def.isFullHealthFactory();
        hideDamage = def.isHideDamage();
        holdSteady = def.isHoldSteady();
        hoverAttack = def.isHoverAttack();
        leaveTracks = def.isLeaveTracks();
        levelGround = def.isLevelGround();
        needGeo = def.isNeedGeo();
        notTransportable = def.isNotTransportable();
        onOffable = def.isOnOffable();
        pushResistant = def.isPushResistant();
        reclaimable = def.isReclaimable();
        releaseHeld = def.isReleaseHeld();
        showPlayerName = def.isShowPlayerName();
        sonarStealth = def.isSonarStealth();
        squareMetalExtractor = def.isSquareResourceExtractor(ResourceManager.getMetal());
        startCloaked = def.isStartCloaked();
        stealth = def.isStealth();
        strafeToAttack = def.isStrafeToAttack();
        targetingFacility = def.isTargetingFacility();
        transportByEnemy = def.isTransportByEnemy();
        turnInPlace = def.isTurnInPlace();
        upright = def.isUpright();
        useBuildingGroundDecal = def.isUseBuildingGroundDecal();
        valid = def.isValid();
    }

    public void setDef(UnitDef def) {
        this.def = def;
    }

    public UnitDef getUnitDef() {
        return def;
    }

    public boolean isAbleToAssist() {
        return ableToAssist;
    }

    public boolean isAbleToAttack() {
        return ableToAttack;
    }

    public boolean isAbleToCapture() {
        return ableToCapture;
    }

    public boolean isAbleToCloak() {
        return ableToCloak;
    }

    public boolean isAbleToCrash() {
        return ableToCrash;
    }

    public boolean isAbleToDGun() {
        return ableToDGun;
    }

    public boolean isAbleToDropFlare() {
        return ableToDropFlare;
    }

    public boolean isAbleToFight() {
        return ableToFight;
    }

    public boolean isAbleToFireControl() {
        return ableToFireControl;
    }

    public boolean isAbleToFly() {
        return ableToFly;
    }

    public boolean isAbleToGuard() {
        return ableToGuard;
    }

    public boolean isAbleToHover() {
        return ableToHover;
    }

    public boolean isAbleToKamikaze() {
        return ableToKamikaze;
    }

    public boolean isAbleToLoopbackAttack() {
        return ableToLoopbackAttack;
    }

    public boolean isAbleToMove() {
        return ableToMove;
    }

    public boolean isAbleToPatrol() {
        return ableToPatrol;
    }

    public boolean isAbleToReclaim() {
        return ableToReclaim;
    }

    public boolean isAbleToRepair() {
        return ableToRepair;
    }

    public boolean isAbleToRepeat() {
        return ableToRepeat;
    }

    public boolean isAbleToRestore() {
        return ableToRestore;
    }

    public boolean isAbleToResurrect() {
        return ableToResurrect;
    }

    public boolean isAbleToSelfD() {
        return ableToSelfD;
    }

    public boolean isAbleToSelfRepair() {
        return ableToSelfRepair;
    }

    public boolean isAbleToSubmerge() {
        return ableToSubmerge;
    }

    public boolean isActivateWhenBuilt() {
        return activateWhenBuilt;
    }

    public int getAiHint() {
        return aiHint;
    }

    public boolean isAirBase() {
        return airBase;
    }

    public float getAirLosRadius() {
        return airLosRadius;
    }

    public boolean isAirStrafe() {
        return airStrafe;
    }

    public int getArmorType() {
        return armorType;
    }

    public float getArmoredMultiple() {
        return armoredMultiple;
    }

    public boolean isAssistable() {
        return assistable;
    }

    public float getAutoHeal() {
        return autoHeal;
    }

    public int getBuildAngle() {
        return buildAngle;
    }

    public float getBuildDistance() {
        return buildDistance;
    }

    public List<Integer> getBuildOptionIDs() {
        return buildOptionIDs;
    }

    public boolean isBuildRange3D() {
        return buildRange3D;
    }

    public float getBuildSpeed() {
        return buildSpeed;
    }

    public float getBuildTime() {
        return buildTime;
    }

    public boolean isBuilder() {
        return builder;
    }

    public float getBuildingDecalDecaySpeed() {
        return buildingDecalDecaySpeed;
    }

    public int getBuildingDecalSizeX() {
        return buildingDecalSizeX;
    }

    public int getBuildingDecalSizeY() {
        return buildingDecalSizeY;
    }

    public int getBuildingDecalType() {
        return buildingDecalType;
    }

    public boolean isCapturable() {
        return capturable;
    }

    public float getCaptureSpeed() {
        return captureSpeed;
    }

    public int getCategory() {
        return category;
    }

    public String getCategoryString() {
        return categoryString;
    }

    public float getCloakCost() {
        return cloakCost;
    }

    public float getCloakCostMoving() {
        return cloakCostMoving;
    }

    public int getCobId() {
        return cobId;
    }

    public boolean isCollide() {
        return collide;
    }

    public boolean isCommander() {
        return commander;
    }

    public float getControlRadius() {
        return controlRadius;
    }

    public Map<String, String> getCustomParams() {
        return customParams;
    }

    public String getDeathExplosion() {
        return deathExplosion;
    }

    public float getDecloakDistance() {
        return decloakDistance;
    }

    public boolean isDecloakOnFire() {
        return decloakOnFire;
    }

    public boolean isDecloakSpherical() {
        return decloakSpherical;
    }
//    public int getDecoyDefID() {
//        return decoyDefID;
//    }

    public float getDlHoverFactor() {
        return dlHoverFactor;
    }

    public boolean isDontLand() {
        return dontLand;
    }

    public float getDrag() {
        return drag;
    }

    public float getEnergyCost() {
        return energyCost;
    }

    public float getEnergyMake() {
        return energyMake;
    }

    public float getEnergyStorage() {
        return energyStorage;
    }

    public float getEnergyUpkeep() {
        return energyUpkeep;
    }

    public float getExtractsMetal() {
        return extractsMetal;
    }

    public boolean isFactoryHeadingTakeoff() {
        return factoryHeadingTakeoff;
    }

    public float getFallSpeed() {
        return fallSpeed;
    }

    public boolean isFeature() {
        return feature;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isFirePlatform() {
        return firePlatform;
    }

    public int getFireState() {
        return fireState;
    }

    public float getFlareDelay() {
        return flareDelay;
    }

    public float getFlareEfficiency() {
        return flareEfficiency;
    }

    public float getFlareReloadTime() {
        return flareReloadTime;
    }

    public int getFlareSalvoDelay() {
        return flareSalvoDelay;
    }

    public int getFlareSalvoSize() {
        return flareSalvoSize;
    }

    public int getFlareTime() {
        return flareTime;
    }

    public boolean isFloater() {
        return floater;
    }

    public float getFrontToSpeed() {
        return frontToSpeed;
    }

    public boolean isFullHealthFactory() {
        return fullHealthFactory;
    }

    public String getGaia() {
        return gaia;
    }

    public int getHashCode() {
        return hashCode;
    }

    public float getHealth() {
        return health;
    }

    public float getHeight() {
        return height;
    }

    public boolean isHideDamage() {
        return hideDamage;
    }

    public int getHighTrajectoryType() {
        return highTrajectoryType;
    }

    public boolean isHoldSteady() {
        return holdSteady;
    }

    public boolean isHoverAttack() {
        return hoverAttack;
    }

    public String getHumanName() {
        return humanName;
    }

    public float getIdleAutoHeal() {
        return idleAutoHeal;
    }

    public int getIdleTime() {
        return idleTime;
    }

    public int getJammerRadius() {
        return jammerRadius;
    }

    public float getKamikazeDist() {
        return kamikazeDist;
    }

    public boolean isLeaveTracks() {
        return leaveTracks;
    }

    public boolean isLevelGround() {
        return levelGround;
    }

    public float getLoadingRadius() {
        return loadingRadius;
    }

    public float getLosHeight() {
        return losHeight;
    }

    public float getLosRadius() {
        return losRadius;
    }

    public float getMakesEnergy() {
        return makesEnergy;
    }

    public float getMakesMetal() {
        return makesMetal;
    }

    public float getMass() {
        return mass;
    }

    public float getMaxAcceleration() {
        return maxAcceleration;
    }

    public float getMaxAileron() {
        return maxAileron;
    }

    public float getMaxBank() {
        return maxBank;
    }

    public float getMaxDeceleration() {
        return maxDeceleration;
    }

    public float getMaxElevator() {
        return maxElevator;
    }

    public float getMaxFuel() {
        return maxFuel;
    }

    public float getMaxHeightDif() {
        return maxHeightDif;
    }

    public float getMaxPitch() {
        return maxPitch;
    }

    public float getMaxRepairSpeed() {
        return maxRepairSpeed;
    }

    public float getMaxRudder() {
        return maxRudder;
    }

    public float getMaxSlope() {
        return maxSlope;
    }

    public int getMaxThisUnit() {
        return maxThisUnit;
    }

    public float getMaxWaterDepth() {
        return maxWaterDepth;
    }

    public float getMaxWeaponRange() {
        return maxWeaponRange;
    }

    public float getMetalCost() {
        return metalCost;
    }

    public float getMetalExtractorRange() {
        return metalExtractorRange;
    }

    public float getMetalMake() {
        return metalMake;
    }

    public float getMetalStorage() {
        return metalStorage;
    }

    public float getMetalUpkeep() {
        return metalUpkeep;
    }

    public float getMinAirBasePower() {
        return minAirBasePower;
    }

    public float getMinCollisionSpeed() {
        return minCollisionSpeed;
    }

    public float getMinTransportMass() {
        return minTransportMass;
    }

    public int getMinTransportSize() {
        return minTransportSize;
    }

    public float getMinWaterDepth() {
        return minWaterDepth;
    }

    public CachedMoveData getMoveData() {
        return moveData;
    }

    public int getMoveState() {
        return moveState;
    }

//    public int getMoveType() {
//        return moveType;
//    }
    public float getMyGravity() {
        return myGravity;
    }

    public String getName() {
        return name;
    }

    public boolean isNeedGeo() {
        return needGeo;
    }

    public int getNoChaseCategory() {
        return noChaseCategory;
    }

    public boolean isNotTransportable() {
        return notTransportable;
    }

    public boolean isOnOffable() {
        return onOffable;
    }

    public float getPower() {
        return power;
    }

    public boolean isPushResistant() {
        return pushResistant;
    }

    public int getRadarRadius() {
        return radarRadius;
    }

    public float getRadius() {
        return radius;
    }

    public float getReclaimSpeed() {
        return reclaimSpeed;
    }

    public boolean isReclaimable() {
        return reclaimable;
    }

    public float getRefuelTime() {
        return refuelTime;
    }

    public boolean isReleaseHeld() {
        return releaseHeld;
    }

    public float getRepairSpeed() {
        return repairSpeed;
    }

    public float getResurrectSpeed() {
        return resurrectSpeed;
    }

    public int getSeismicRadius() {
        return seismicRadius;
    }

    public float getSeismicSignature() {
        return seismicSignature;
    }

    public int getSelfDCountdown() {
        return selfDCountdown;
    }

    public String getSelfDExplosion() {
        return selfDExplosion;
    }

    public boolean isShowPlayerName() {
        return showPlayerName;
    }

    public float getSlideTolerance() {
        return slideTolerance;
    }

    public int getSonarJamRadius() {
        return sonarJamRadius;
    }

    public int getSonarRadius() {
        return sonarRadius;
    }

    public boolean isSonarStealth() {
        return sonarStealth;
    }

    public float getSpeed() {
        return speed;
    }

    public float getSpeedToFront() {
        return speedToFront;
    }

    public boolean isSquareMetalExtractor() {
        return squareMetalExtractor;
    }

    public boolean isStartCloaked() {
        return startCloaked;
    }

    public boolean isStealth() {
        return stealth;
    }

    public boolean isStrafeToAttack() {
        return strafeToAttack;
    }

    public boolean isTargetingFacility() {
        return targetingFacility;
    }

    public int getTechLevel() {
        return techLevel;
    }

    public float getTerraformSpeed() {
        return terraformSpeed;
    }

    public float getTidalResourceGenerator() {
        return tidalResourceGenerator;
    }

    public String getTooltip() {
        return tooltip;
    }

    public float getTrackOffset() {
        return trackOffset;
    }

    public float getTrackStrength() {
        return trackStrength;
    }

    public float getTrackStretch() {
        return trackStretch;
    }

    public int getTrackType() {
        return trackType;
    }

    public float getTrackWidth() {
        return trackWidth;
    }

    public boolean isTransportByEnemy() {
        return transportByEnemy;
    }

    public int getTransportCapacity() {
        return transportCapacity;
    }

    public float getTransportMass() {
        return transportMass;
    }

    public int getTransportSize() {
        return transportSize;
    }

    public int getTransportUnloadMethod() {
        return transportUnloadMethod;
    }

    public boolean isTurnInPlace() {
        return turnInPlace;
    }

    public float getTurnInPlaceDistance() {
        return turnInPlaceDistance;
    }

    public float getTurnInPlaceSpeedLimit() {
        return turnInPlaceSpeedLimit;
    }

    public float getTurnRadius() {
        return turnRadius;
    }

    public float getTurnRate() {
        return turnRate;
    }

    public String getType() {
        return type;
    }

    public int getUnitDefId() {
        return unitDefId;
    }

    public float getUnitFallSpeed() {
        return unitFallSpeed;
    }

    public float getUnloadSpread() {
        return unloadSpread;
    }

    public boolean isUpright() {
        return upright;
    }

    public boolean isUseBuildingGroundDecal() {
        return useBuildingGroundDecal;
    }

    public boolean isValid() {
        return valid;
    }

    public float getVerticalSpeed() {
        return verticalSpeed;
    }

    public float getWantedHeight() {
        return wantedHeight;
    }

    public float getWaterline() {
        return waterline;
    }

    public float getWindResourceGenerator() {
        return windResourceGenerator;
    }

    public float getWingAngle() {
        return wingAngle;
    }

    public float getWingDrag() {
        return wingDrag;
    }

    public String getWreckName() {
        return wreckName;
    }

    public int getXSize() {
        return xSize;
    }

    public int getZSize() {
        return zSize;
    }
}
