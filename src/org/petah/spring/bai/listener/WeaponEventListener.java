/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.listener;

import com.springrts.ai.oo.Unit;
import com.springrts.ai.oo.WeaponDef;

/**
 *
 * @author davnei06
 */
public interface WeaponEventListener {

    public int weaponFired(Unit unit, WeaponDef weaponDef);
}
