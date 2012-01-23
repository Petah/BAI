package org.petah.spring.bai.listener;

/**
 *
 * @author David Neilsen
 */
public interface MessageEventListener extends EventListener {

    public void message(Integer player, String message);
}
