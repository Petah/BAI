/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.petah.common.util.profiler.Profile;
import org.petah.common.util.profiler.ProfileFormatter;

/**
 *
 * @author Petah
 */
public class CustomProfileFormatter implements ProfileFormatter {

    private static final String SPACE = " ";

    public String format(Profile profile) {
        SimpleDateFormat formatter = new SimpleDateFormat("H:mm:ss");
        return FormatUtil.addWhiteSpace(profile.getThread().getName(), 20) + SPACE +
                FormatUtil.addWhiteSpace(profile.getProfileClass().getName().replaceFirst("org.petah.spring.bai.", ""), 30) + SPACE +
                FormatUtil.addWhiteSpace(profile.getName(), 50) + SPACE +
                FormatUtil.addWhiteSpace(FormatUtil.formatNanoTime(profile.getRunTime()), 20) + SPACE +
                FormatUtil.addWhiteSpace(formatter.format(new Date(profile.getStartTime() / 1000000)), 10) + SPACE +
                (profile.getDescription() == null ? "" : profile.getDescription());
    }

}
