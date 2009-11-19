/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.util;

import java.io.File;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;

/**
 *
 * @author Petah
 */
public class CustomOptionIO {

    public static void save(File file) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file);
            writer.println("<options>");
            for (String name : OptionsManager.getOptions().keySet()) {
                Option option = OptionsManager.getOption(name);
                if (option.getMetaOptionValue("transient") == null || !(Boolean) option.getMetaOptionValue("transient")) {
                    writer.println("  <option name=\"" + name + "\" type=\"" + option.getValue().getClass().getName() + "\">");
                    writer.println("    <value>" + option.getValue() + "</value>");
                    for (Object metaName : option.getMetaOptions().keySet()) {
                        Option metaOption = option.getMetaOption((String) metaName);
                        if (metaName.equals("description")) {
                            writer.println("    <!-- " + metaOption.getValue() + " -->");
                        } else if (metaName.equals("values")) {
                            writer.println("    <!-- Values: " + metaOption.getValue() + " -->");
                        } else {
                            writer.println("    <metaoption name=\"" + metaName + "\">");
                            writer.println("      <value type=\"" + metaOption.getValue().getClass().getName() + "\">" + metaOption.getValue() + "</value>");
                            writer.println("    </metaoption>");
                        }
                    }
                    writer.println("  </option>");
                }
            }
            writer.println("</options>");
        } catch (Exception ex) {
            Logger.getLogger(CustomOptionIO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writer.close();
        }
    }

    public static void load(File file) {
    }
}
