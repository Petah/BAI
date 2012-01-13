/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.listener;

import com.springrts.ai.oo.clb.Command;
import com.springrts.ai.oo.clb.Unit;
import java.util.List;

/**
 *
 * @author davnei06
 */
public interface CommandEventListener {

    public int commandFinished(Unit unit, int commandId, int commandTopicId);

    public int playerCommand(List<Unit> units, Command command, int playerId);
}
