package com.douncoding.timelineview;

import android.util.Log;

/**
 *
 */
public class Constants {
    public static final String TAG = "TimeLineView";
    public static boolean LOGGING = true;

    public static void logging(String message) {
        if (LOGGING) {
            Log.d(TAG, message);
        }
    }
}
