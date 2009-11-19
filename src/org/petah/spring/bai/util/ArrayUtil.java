/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.util;

/**
 *
 * @author Petah
 */
public class ArrayUtil {

    /**
     * Gets an array index for 2D data stored in a 1D array
     * @param x
     * @param y
     * @param width
     * @return
     */
    public static int get1DIndex(int x, int y, int width) {
        return y * width + x;
    }
}
