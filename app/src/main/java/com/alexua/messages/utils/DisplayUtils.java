package com.alexua.messages.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author Mirash
 */
public class DisplayUtils {
    public static int getDisplayHeight(Context context) {
        return getDisplayDimensions(context).y;
    }

    public static int getDisplayWidth(Context context) {
        return getDisplayDimensions(context).x;
    }

    public static Point getDisplayDimensions(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        return size;
    }

    public static float dp2px(Context context, float valueInDp) {
        return valueInDp * (context.getResources().getDisplayMetrics().densityDpi / 160f);
    }

    public static void hideActionBarTitle(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public static void hideStatusBar(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static void showStatusBar(Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
