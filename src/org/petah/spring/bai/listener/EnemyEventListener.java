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

    public void enemyEnterLOS(Unit enemy);

    public void enemyEnterRadar(Unit enemy);

    public void enemyLeaveLOS(Unit enemy);

    public void enemyLeaveRadar(Unit enemy);
}
