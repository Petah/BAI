/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.listener;

import com.springrts.ai.oo.clb.Unit;

/**
 *
 * @author davnei06
 */
public interface EnemyEventListener {

    public int enemyEnterLOS(Unit enemy);

    public int enemyEnterRadar(Unit enemy);

    public int enemyLeaveLOS(Unit enemy);

    public int enemyLeaveRadar(Unit enemy);
}
