package com.alexua.messages.utils;

import com.alexua.messages.ui.fragments.BaseFragment;

/**
 * @author Mirash
 */
public class ScreenSwitchUtils {
    public static String getTag(BaseFragment fragment) {
        return fragment.getClass().getSimpleName();
    }
}
