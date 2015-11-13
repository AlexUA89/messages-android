package com.alexua.messages.base;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by olkh on 11/13/2015.
 */
public class BaseFragment extends Fragment {

    private FragmentActivity myContext;

    @Override
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }


    public FragmentActivity getMyContext() {
        return myContext;
    }
}
