/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.delegate;

import com.springrts.ai.AICommand;
import com.springrts.ai.AIFloat3;
import com.springrts.ai.oo.OOAI;
import com.springrts.ai.oo.OOAICallback;
import com.springrts.ai.oo.Unit;
import com.springrts.ai.oo.WeaponDef;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.petah.common.util.profiler.Profiler;
import org.petah.spring.bai.AIReturnCode;
import org.petah.spring.bai.listener.AIEventListener;
import org.petah.spring.bai.listener.CommandEventListener;
import org.petah.spring.bai.listener.DamageEventListener;
import org.petah.spring.bai.listener.EnemyEventListener;
import org.petah.spring.bai.listener.IOEventListener;
import org.petah.spring.bai.listener.MessageEventListener;
import org.petah.spring.bai.listener.MoveEventListener;
import org.petah.spring.bai.listener.SeismicPingEventListener;
import org.petah.spring.bai.listener.UnitEventListener;
import org.petah.spring.bai.listener.UpdateEventListener;
import org.petah.spring.bai.listener.WeaponEventListener;

/**
 *
 * @author davnei06
 */
public class AIEventHandler implements OOAI {

    private AIDelegate aiDelegate;
    private Collection<AIEventListener> aiEventListeners = new CopyOnWriteArrayList<AIEventListener>();
    private Collection<CommandEventListener> commandEventListeners = new CopyOnWriteArrayList<CommandEventListener>();
    private Collection<DamageEventListener> damageEventListeners = new CopyOnWriteArrayList<DamageEventListener>();
    private Collection<EnemyEventListener> enemyEventListeners = new CopyOnWriteArrayList<EnemyEventListener>();
    private Collection<IOEventListener> ioEventListeners = new CopyOnWriteArrayList<IOEventListener>();
    private Collection<MessageEventListener> messageEventListeners = new CopyOnWriteArrayList<MessageEventListener>();
    private Collection<SeismicPingEventListener> seismicPingEventListeners = new CopyOnWriteArrayList<SeismicPingEventListener>();
    private Collection<UnitEventListener> unitEventListeners = new CopyOnWriteArrayList<UnitEventListener>();
    private Collection<UpdateEventListener> updateEventListeners = new CopyOnWriteArrayList<UpdateEventListener>();
    private Collection<WeaponEventListener> weaponEventListeners = new CopyOnWriteArrayList<WeaponEventListener>();
    private Collection<MoveEventListener> moveEventListeners = new CopyOnWriteArrayList<MoveEventListener>();

    // Private methods
    private void handleException(Exception ex) {
        ex.printStackTrace();
    }

    // Public methods
    public void addAIEventListener(AIEventListener listener) {
        aiEventListeners.add(listener);
    }

    public void removeAIEventListener(AIEventListener listener) {
        aiEventListeners.remove(listener);
    }

    public void addCommandEventListener(CommandEventListener listener) {
        commandEventListeners.add(listener);
    }

    public void removeCommandEventListener(CommandEventListener listener) {
        commandEventListeners.remove(listener);
    }

    public void addDamageEventListener(DamageEventListener listener) {
        damageEventListeners.add(listener);
    }

    public void removeDamageEventListener(DamageEventListener listener) {
        damageEventListeners.remove(listener);
    }

    public void addEnemyEventListener(EnemyEventListener listener) {
        enemyEventListeners.add(listener);
    }

    public void removeEnemyEventListener(EnemyEventListener listener) {
        enemyEventListeners.remove(listener);
    }

    public void addIOEventListener(IOEventListener listener) {
        ioEventListeners.add(listener);
    }

    public void removeIOEventListener(IOEventListener listener) {
        ioEventListeners.remove(listener);
    }

    public void addMessageEventListener(MessageEventListener listener) {
        messageEventListeners.add(listener);
    }

    public void removeMessageEventListener(MessageEventListener listener) {
        messageEventListeners.remove(listener);
    }

    public void addSeismicPingEventListener(SeismicPingEventListener listener) {
        seismicPingEventListeners.add(listener);
    }

    public void removeSeismicPingEventListener(SeismicPingEventListener listener) {
        seismicPingEventListeners.remove(listener);
    }

    public void addUnitEventListener(UnitEventListener listener) {
        unitEventListeners.add(listener);
    }

    public void removeUnitEventListener(UnitEventListener listener) {
        unitEventListeners.remove(listener);
    }

    public void addUpdateEventListener(UpdateEventListener listener) {
        updateEventListeners.add(listener);
    }

    public void removeUpdateEventListener(UpdateEventListener listener) {
        updateEventListeners.remove(listener);
    }

    public void addWeaponEventListener(WeaponEventListener listener) {
        weaponEventListeners.add(listener);
    }

    public void removeWeaponEventListener(WeaponEventListener listener) {
        weaponEventListeners.remove(listener);
    }

    public void addMoveEventListener(MoveEventListener listener) {
        moveEventListeners.add(listener);
    }

    public void removeMoveEventListener(MoveEventListener listener) {
        moveEventListeners.remove(listener);
    }

    // Events
    public int commandFinished(Unit unit, int commandId, int commandTopicId) {
        try {
            for (CommandEventListener commandEventListener : commandEventListeners) {
                Profiler.start(AIEventHandler.class, aiDelegate.getPrefix() + commandEventListener.getClass().getSimpleName() + ".commandFinished()");
                commandEventListener.commandFinished(unit, commandId, commandTopicId);
                Profiler.stop(AIEventHandler.class, aiDelegate.getPrefix() + commandEventListener.getClass().getSimpleName() + ".commandFinished()");
            }
            return AIReturnCode.NORMAL;
        } catch (Exception ex) {
            handleException(ex);
            return AIReturnCode.UNHANDLED_EXCEPTION;
        }
    }

    public int enemyDamaged(Unit enemy, Unit attacker, float damage, AIFloat3 dir, WeaponDef weaponDef, boolean paralyzer) {
        try {
            for (DamageEventListener damageEventListener : damageEventListeners) {
                Profiler.start(AIEventHandler.class, aiDelegate.getPrefix() + damageEventListener.getClass().getSimpleName() + ".enemyDamaged()");
                damageEventListener.enemyDamaged(enemy, attacker, damage, dir, weaponDef, paralyzer);
                Profiler.stop(AIEventHandler.class, aiDelegate.getPrefix() + damageEventListener.getClass().getSimpleName() + ".enemyDamaged()");
            }
            return AIReturnCode.NORMAL;
        } catch (Exception ex) {
            handleException(ex);
            return AIReturnCode.UNHANDLED_EXCEPTION;
        }
    }

    public int enemyDestroyed(Unit enemy, Unit attacker) {
        try {
            for (DamageEventListener damageEventListener : damageEventListeners) {
                Profiler.start(AIEventHandler.class, aiDelegate.getPrefix() + damageEventListener.getClass().getSimpleName() + ".enemyDestroyed()");
                damageEventListener.enemyDestroyed(enemy, attacker);
                Profiler.stop(AIEventHandler.class, aiDelegate.getPrefix() + damageEventListener.getClass().getSimpleName() + ".enemyDestroyed()");
            }
            return AIReturnCode.NORMAL;
        } catch (Exception ex) {
            handleException(ex);
            return AIReturnCode.UNHANDLED_EXCEPTION;
        }
    }

    public int enemyEnterLOS(Unit enemy) {
        try {
            for (EnemyEventListener enemyEventListener : enemyEventListeners) {
                Profiler.start(AIEventHandler.class, aiDelegate.getPrefix() + enemyEventListener.getClass().getSimpleName() + ".enemyEnterLOS()");
                enemyEventListener.enemyEnterLOS(enemy);
                Profiler.stop(AIEventHandler.class, aiDelegate.getPrefix() + enemyEventListener.getClass().getSimpleName() + ".enemyEnterLOS()");
            }
            return AIReturnCode.NORMAL;
        } catch (Exception ex) {
            handleException(ex);
            return AIReturnCode.UNHANDLED_EXCEPTION;
        }
    }

    public int enemyEnterRadar(Unit enemy) {
        try {
            for (EnemyEventListener enemyEventListener : enemyEventListeners) {
                Profiler.start(AIEventHandler.class, aiDelegate.getPrefix() + enemyEventListener.getClass().getSimpleName() + ".enemyEnterRadar()");
                enemyEventListener.enemyEnterRadar(enemy);
                Profiler.stop(AIEventHandler.class, aiDelegate.getPrefix() + enemyEventListener.getClass().getSimpleName() + ".enemyEnterRadar()");
            }
            return AIReturnCode.NORMAL;
        } catch (Exception ex) {
            handleException(ex);
            return AIReturnCode.UNHANDLED_EXCEPTION;
        }

    }

    public int enemyLeaveLOS(Unit enemy) {
        try {
            for (EnemyEventListener enemyEventListener : enemyEventListeners) {
                Profiler.start(AIEventHandler.class, aiDelegate.getPrefix() + enemyEventListener.getClass().getSimpleName() + ".enemyLeaveLOS()");
                enemyEventListener.enemyLeaveLOS(enemy);
                Profiler.stop(AIEventHandler.class, aiDelegate.getPrefix() + enemyEventListener.getClass().getSimpleName() + ".enemyLeaveLOS()");
            }
            return AIReturnCode.NORMAL;
        } catch (Exception ex) {
            handleException(ex);
            return AIReturnCode.UNHANDLED_EXCEPTION;
        }
    }

    public int enemyLeaveRadar(Unit enemy) {
        try {
            for (EnemyEventListener enemyEventListener : enemyEventListeners) {
                Profiler.start(AIEventHandler.class, aiDelegate.getPrefix() + enemyEventListener.getClass().getSimpleName() + ".enemyLeaveRadar()");
                enemyEventListener.enemyLeaveRadar(enemy);
                Profiler.stop(AIEventHandler.class, aiDelegate.getPrefix() + enemyEventListener.getClass().getSimpleName() + ".enemyLeaveRadar()");
            }
            return AIReturnCode.NORMAL;
        } catch (Exception ex) {
            handleException(ex);
            return AIReturnCode.UNHANDLED_EXCEPTION;
        }
    }

    public int init(int teamId, OOAICallback callback) {
        try {
            for (AIEventListener aiEventListener : aiEventListeners) {
                Profiler.start(AIEventHandler.class, aiDelegate.getPrefix() + aiEventListener.getClass().getSimpleName() + ".init()");
                aiEventListener.init(teamId, callback);
                Profiler.stop(AIEventHandler.class, aiDelegate.getPrefix() + aiEventListener.getClass().getSimpleName() + ".init()");
            }
            return AIReturnCode.NORMAL;
        } catch (Exception ex) {
            handleException(ex);
            return AIReturnCode.UNHANDLED_EXCEPTION;
        }
    }

    public int load(String file) {
        try {
            for (IOEventListener ioEventListener : ioEventListeners) {
                Profiler.start(AIEventHandler.class, aiDelegate.getPrefix() + ioEventListener.getClass().getSimpleName() + ".load()");
                ioEventListener.load(file);
                Profiler.stop(AIEventHandler.class, aiDelegate.getPrefix() + ioEventListener.getClass().getSimpleName() + ".load()");
            }
            return AIReturnCode.NORMAL;
        } catch (Exception ex) {
            handleException(ex);
            return AIReturnCode.UNHANDLED_EXCEPTION;
        }
    }

    public int message(int player, String message) {
        try {
            for (MessageEventListener messageEventListener : messageEventListeners) {
                Profiler.start(AIEventHandler.class, aiDelegate.getPrefix() + messageEventListener.getClass().getSimpleName() + ".message()");
                messageEventListener.message(player, message);
                Profiler.stop(AIEventHandler.class, aiDelegate.getPrefix() + messageEventListener.getClass().getSimpleName() + ".message()");
            }
            return AIReturnCode.NORMAL;
        } catch (Exception ex) {
            handleException(ex);
            return AIReturnCode.UNHANDLED_EXCEPTION;
        }
    }

    public int playerCommand(List<Unit> units, AICommand command, int playerId) {
        try {
            for (CommandEventListener commandEventListener : commandEventListeners) {
                Profiler.start(AIEventHandler.class, aiDelegate.getPrefix() + commandEventListener.getClass().getSimpleName() + ".playerCommand()");
                commandEventListener.playerCommand(units, command, playerId);
                Profiler.stop(AIEventHandler.class, aiDelegate.getPrefix() + commandEventListener.getClass().getSimpleName() + ".playerCommand()");
            }
            return AIReturnCode.NORMAL;
        } catch (Exception ex) {
            handleException(ex);
            return AIReturnCode.UNHANDLED_EXCEPTION;
        }
    }

    public int release(int reason) {
        try {
            for (AIEventListener aiEventListener : aiEventListeners) {
                Profiler.start(AIEventHandler.class, aiDelegate.getPrefix() + aiEventListener.getClass().getSimpleName() + ".release()");
                aiEventListener.release(reason);
                Profiler.stop(AIEventHandler.class, aiDelegate.getPrefix() + aiEventListener.getClass().getSimpleName() + ".release()");
            }
            return AIReturnCode.NORMAL;
        } catch (Exception ex) {
            handleException(ex);
            return AIReturnCode.UNHANDLED_EXCEPTION;
        }
    }

    public int save(String file) {
        try {
            for (IOEventListener ioEventListener : ioEventListeners) {
                Profiler.start(AIEventHandler.class, aiDelegate.getPrefix() + ioEventListener.getClass().getSimpleName() + ".save()");
                ioEventListener.save(file);
                Profiler.stop(AIEventHandler.class, aiDelegate.getPrefix() + ioEventListener.getClass().getSimpleName() + ".save()");
            }
            return AIReturnCode.NORMAL;
        } catch (Exception ex) {
            handleException(ex);
            return AIReturnCode.UNHANDLED_EXCEPTION;
        }
    }

    public int seismicPing(AIFloat3 pos, float strength) {
        try {
            for (SeismicPingEventListener seismicPingEventListener : seismicPingEventListeners) {
                Profiler.start(AIEventHandler.class, aiDelegate.getPrefix() + seismicPingEventListener.getClass().getSimpleName() + ".seismicPing()");
                seismicPingEventListener.seismicPing(pos, strength);
                Profiler.stop(AIEventHandler.class, aiDelegate.getPrefix() + seismicPingEventListener.getClass().getSimpleName() + ".seismicPing()");
            }
            return AIReturnCode.NORMAL;
        } catch (Exception ex) {
            handleException(ex);
            return AIReturnCode.UNHANDLED_EXCEPTION;
        }
    }

    public int unitCaptured(Unit unit, int oldTeamId, int newTeamId) {
        try {
            for (UnitEventListener unitEventListener : unitEventListeners) {
                Profiler.start(AIEventHandler.class, aiDelegate.getPrefix() + unitEventListener.getClass().getSimpleName() + ".unitCaptured()");
                unitEventListener.unitCaptured(unit, oldTeamId, newTeamId);
                Profiler.stop(AIEventHandler.class, aiDelegate.getPrefix() + unitEventListener.getClass().getSimpleName() + ".unitCaptured()");
            }
            return AIReturnCode.NORMAL;
        } catch (Exception ex) {
            handleException(ex);
            return AIReturnCode.UNHANDLED_EXCEPTION;
        }
    }

    public int unitCreated(Unit unit, Unit builder) {
        try {
            for (UnitEventListener unitEventListener : unitEventListeners) {
                Profiler.start(AIEventHandler.class, aiDelegate.getPrefix() + unitEventListener.getClass().getSimpleName() + ".unitCreated()");
                unitEventListener.unitCreated(unit, builder);
                Profiler.stop(AIEventHandler.class, aiDelegate.getPrefix() + unitEventListener.getClass().getSimpleName() + ".unitCreated()");
            }
            return AIReturnCode.NORMAL;
        } catch (Exception ex) {
            handleException(ex);
            return AIReturnCode.UNHANDLED_EXCEPTION;
        }
    }

    public int unitDamaged(Unit unit, Unit attacker, float damage, AIFloat3 dir, WeaponDef weaponDef, boolean paralyzer) {
        try {
            for (DamageEventListener damageEventListener : damageEventListeners) {
                Profiler.start(AIEventHandler.class, aiDelegate.getPrefix() + damageEventListener.getClass().getSimpleName() + ".unitDamaged()");
                damageEventListener.unitDamaged(unit, attacker, damage, dir, weaponDef, paralyzer);
                Profiler.stop(AIEventHandler.class, aiDelegate.getPrefix() + damageEventListener.getClass().getSimpleName() + ".unitDamaged()");
            }
            return AIReturnCode.NORMAL;
        } catch (Exception ex) {
            handleException(ex);
            return AIReturnCode.UNHANDLED_EXCEPTION;
        }
    }

    public int unitDestroyed(Unit unit, Unit attacker) {
        try {
            for (DamageEventListener damageEventListener : damageEventListeners) {
                Profiler.start(AIEventHandler.class, aiDelegate.getPrefix() + damageEventListener.getClass().getSimpleName() + ".unitDestroyed()");
                damageEventListener.unitDestroyed(unit, attacker);
                Profiler.stop(AIEventHandler.class, aiDelegate.getPrefix() + damageEventListener.getClass().getSimpleName() + ".unitDestroyed()");
            }
            return AIReturnCode.NORMAL;
        } catch (Exception ex) {
            handleException(ex);
            return AIReturnCode.UNHANDLED_EXCEPTION;
        }
    }

    public int unitFinished(Unit unit) {
        try {
            for (UnitEventListener unitEventListener : unitEventListeners) {
                Profiler.start(AIEventHandler.class, aiDelegate.getPrefix() + unitEventListener.getClass().getSimpleName() + ".unitFinished()");
                unitEventListener.unitFinished(unit);
                Profiler.stop(AIEventHandler.class, aiDelegate.getPrefix() + unitEventListener.getClass().getSimpleName() + ".unitFinished()");
            }
            return AIReturnCode.NORMAL;
        } catch (Exception ex) {
            handleException(ex);
            return AIReturnCode.UNHANDLED_EXCEPTION;
        }
    }

    public int unitGiven(Unit unit, int oldTeamId, int newTeamId) {
        try {
            for (UnitEventListener unitEventListener : unitEventListeners) {
                Profiler.start(AIEventHandler.class, aiDelegate.getPrefix() + unitEventListener.getClass().getSimpleName() + ".unitGiven()");
                unitEventListener.unitGiven(unit, oldTeamId, newTeamId);
                Profiler.stop(AIEventHandler.class, aiDelegate.getPrefix() + unitEventListener.getClass().getSimpleName() + ".unitGiven()");
            }
            return AIReturnCode.NORMAL;
        } catch (Exception ex) {
            handleException(ex);
            return AIReturnCode.UNHANDLED_EXCEPTION;
        }
    }

    public int unitIdle(Unit unit) {
        try {
            for (MoveEventListener moveEventListener : moveEventListeners) {
                Profiler.start(AIEventHandler.class, aiDelegate.getPrefix() + moveEventListener.getClass().getSimpleName() + ".unitIdle()");
                moveEventListener.unitIdle(unit);
                Profiler.stop(AIEventHandler.class, aiDelegate.getPrefix() + moveEventListener.getClass().getSimpleName() + ".unitIdle()");
            }
            return AIReturnCode.NORMAL;
        } catch (Exception ex) {
            handleException(ex);
            return AIReturnCode.UNHANDLED_EXCEPTION;
        }
    }

    public int unitMoveFailed(Unit unit) {
        try {
            for (MoveEventListener moveEventListener : moveEventListeners) {
                Profiler.start(AIEventHandler.class, aiDelegate.getPrefix() + moveEventListener.getClass().getSimpleName() + ".unitMoveFailed()");
                moveEventListener.unitMoveFailed(unit);
                Profiler.stop(AIEventHandler.class, aiDelegate.getPrefix() + moveEventListener.getClass().getSimpleName() + ".unitMoveFailed()");
            }
            return AIReturnCode.NORMAL;
        } catch (Exception ex) {
            handleException(ex);
            return AIReturnCode.UNHANDLED_EXCEPTION;
        }
    }

    public int update(int frame) {
        try {
            for (UpdateEventListener updateEventListener : updateEventListeners) {
                Profiler.start(AIEventHandler.class, aiDelegate.getPrefix() + updateEventListener.getClass().getSimpleName() + ".update()");
                updateEventListener.update(frame);
                Profiler.stop(AIEventHandler.class, aiDelegate.getPrefix() + updateEventListener.getClass().getSimpleName() + ".update()");
            }
            return AIReturnCode.NORMAL;
        } catch (Exception ex) {
            handleException(ex);
            return AIReturnCode.UNHANDLED_EXCEPTION;
        }
    }

    public int weaponFired(Unit unit, WeaponDef weaponDef) {
        try {
            for (WeaponEventListener weaponEventListener : weaponEventListeners) {
                Profiler.start(AIEventHandler.class, aiDelegate.getPrefix() + weaponEventListener.getClass().getSimpleName() + ".weaponFired()");
                weaponEventListener.weaponFired(unit, weaponDef);
                Profiler.stop(AIEventHandler.class, aiDelegate.getPrefix() + weaponEventListener.getClass().getSimpleName() + ".weaponFired()");
            }
            return AIReturnCode.NORMAL;
        } catch (Exception ex) {
            handleException(ex);
            return AIReturnCode.UNHANDLED_EXCEPTION;
        }
    }

    public void setAiDelegate(AIDelegate aiDelegate) {
        this.aiDelegate = aiDelegate;
    }
}
