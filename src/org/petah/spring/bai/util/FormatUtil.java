/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.util;

import com.springrts.ai.oo.AIFloat3;

/**
 *
 * @author Petah
 */
public class FormatUtil extends org.petah.common.util.FormatUtil {

    public static String formatAIFloat3(AIFloat3 pos) {
        return pos.x + ", " + pos.y + ", " + pos.z;
    }
}
