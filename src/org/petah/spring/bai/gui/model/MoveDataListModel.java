/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.gui.model;

import javax.swing.AbstractListModel;
import org.petah.spring.bai.cache.CachedMoveData;
import org.petah.spring.bai.delegate.GlobalDelegate;

/**
 *
 * @author Petah
 */
public class MoveDataListModel extends AbstractListModel {

    public Object getElementAt(int index) {
        CachedMoveData moveData = getCachedMoveData(index);
        if (moveData != null) {
            return moveData.getName();
        }
        return "Unknown move data.";
    }

    public CachedMoveData getCachedMoveData(int index) {
        int count = 0;
        for (CachedMoveData moveData : GlobalDelegate.getCachedMoveData().values()) {
            if (count == index) {
                return moveData;
            }
            count++;
        }
        return null;
    }

    public int getSize() {
        return GlobalDelegate.getCachedMoveData().size();
    }
}
