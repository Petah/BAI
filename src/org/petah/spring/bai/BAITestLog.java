package org.petah.spring.bai;

import com.springrts.ai.oo.AIFloat3;
import com.springrts.ai.oo.OOAI;
import com.springrts.ai.oo.clb.OOAICallback;
import com.springrts.ai.oo.clb.Unit;
import com.springrts.ai.oo.clb.WeaponDef;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BAITestLog extends OOAI {

    public static PrintStream file;
    public static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss:SSS dd.MM.yyyy");

    static {
        try {
            file = new PrintStream(new File("c:\\work\\bai.log"));
        } catch (FileNotFoundException ex) {
        }
    }

    public int log(String str) {
        file.println(dateFormat.format(new Date()) + " " + str);
        return 0;
    }

    public int commandFinished(Unit unit, int i, int i1) {
        return log("commandFinished");
    }

    public int enemyCreated(Unit unit) {
        return log("enemyCreated");
    }

    public int enemyDamaged(Unit unit, Unit unit1, float f, AIFloat3 aif, WeaponDef wd, boolean bln) {
        return log("enemyDamaged");
    }

    public int enemyDestroyed(Unit unit, Unit unit1) {
        return log("enemyDestroyed");
    }

    public int enemyEnterLOS(Unit unit) {
        return log("enemyEnterLOS");
    }

    public int enemyEnterRadar(Unit unit) {
        return log("enemyEnterRadar");
    }

    public int enemyFinished(Unit unit) {
        return log("enemyFinished");
    }

    public int enemyLeaveLOS(Unit unit) {
        return log("enemyLeaveLOS");
    }

    public int enemyLeaveRadar(Unit unit) {
        return log("enemyLeaveRadar");
    }

    public int init(int i, OOAICallback ooaic) {
        return log("init");
    }

    public int load(String string) {
        return log("load");
    }

    public int luaMessage(String string, String[] strings) {
        return log("luaMessage");
    }

    public int message(int i, String string) {
        return log("message");
    }

    public int playerCommand(List<Unit> list, int i, int i1) {
        return log("playerCommand");
    }

    public int release(int i) {
        return log("release");
    }

    public int save(String string) {
        return log("save");
    }

    public int seismicPing(AIFloat3 aif, float f) {
        return log("seismicPing");
    }

    public int unitCaptured(Unit unit, int i, int i1) {
        return log("unitCaptured");
    }

    public int unitCreated(Unit unit, Unit unit1) {
        return log("unitCreated");
    }

    public int unitDamaged(Unit unit, Unit unit1, float f, AIFloat3 aif, WeaponDef wd, boolean bln) {
        return log("unitDamaged");
    }

    public int unitDestroyed(Unit unit, Unit unit1) {
        return log("unitDestroyed");
    }

    public int unitFinished(Unit unit) {
        return log("unitFinished");
    }

    public int unitGiven(Unit unit, int i, int i1) {
        return log("unitGiven");
    }

    public int unitIdle(Unit unit) {
        return log("unitIdle");
    }

    public int unitMoveFailed(Unit unit) {
        return log("unitMoveFailed");
    }

    public int update(int i) {
        return log("update");
    }

    public int weaponFired(Unit unit, WeaponDef wd) {
        return log("weaponFired");
    }
}
