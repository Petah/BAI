/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.petah.common.option.Option;

/**
 *
 * @author Petah
 */
public class IOUtil {

    /**
     * Saves an object to the specified file if the options is tree.
     * @param option the option to check to see if caching is enabled
     * @param file the file to save the object to
     * @param object the object to save
     */
    public static void saveCacheFile(Option<Boolean> option, File file, Object object) {
        ObjectOutputStream outputStream = null;
        if (option.getValue()) {
            try {
                outputStream = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)));
                outputStream.writeObject(object);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    outputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Loads an object from a cached file if it exists, is valid and the option's
     * value is true.
     * @param option the option to check to see if caching is enabled
     * @param file the file to load the object from
     * @return the object loaded from the file or null
     */
    public static Object loadCacheFile(Option<Boolean> option, File file) {
        boolean loadedSuccessfully = false;
        Object object = null;
        if (option.getValue() && file.exists()) {
            ObjectInputStream inputStream = null;
            try {
                inputStream = new ObjectInputStream(new GZIPInputStream(new FileInputStream(file)));
                object = inputStream.readObject();
                loadedSuccessfully = true;
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        if (loadedSuccessfully) {
            return object;
        }
        return null;
    }
}
