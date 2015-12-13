package com.alexua.messages.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;

import com.alexua.messages.utils.LogUtils;
import com.alexua.messages.utils.ScreenSwitchUtils;

/**
 * @author Mirash
 */
public abstract class BaseFragment extends Fragment {
    private final String tag = ScreenSwitchUtils.getTag(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        LogUtils.log("onCreate:" + getScreenTag());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        LogUtils.log("onDestroy:" + getScreenTag());
        super.onDestroy();
    }

    @Override
    public void onResume() {
        LogUtils.log("onResume:" + getScreenTag());
        super.onResume();
    }

    @Override
    public void onPause() {
        LogUtils.log("onPause:" + getScreenTag());
        super.onPause();
    }

    public String getScreenTag() {
        return tag;
    }
}
