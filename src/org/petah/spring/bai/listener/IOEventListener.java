/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.listener;

/**
 *
 * @author davnei06
 */
public interface IOEventListener {

    public int load(String file);
    public int save(String file);
}
