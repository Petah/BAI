/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.petah.spring.bai.util;

import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.common.util.profiler.Profile;
import org.petah.common.util.profiler.ProfileFormatter;
import org.petah.common.util.profiler.ProfileHandler;
import org.petah.spring.bai.gui.GUIManager;

/**
 *
 * @author Petah
 */
public class CustomProfileHandler implements ProfileHandler {

    private static Option<Long> minNanos = OptionsManager.getOption(
            new Option<Long>("CustomProfileHandler.minNanos", 15000000l));
    private static ProfileFormatter formatter = new CustomProfileFormatter();

    public void publish(Profile profile) {
        if (profile.getRunTime() > minNanos.getValue()) {
            GUIManager.profile(formatter.format(profile));
        }
    }
}
