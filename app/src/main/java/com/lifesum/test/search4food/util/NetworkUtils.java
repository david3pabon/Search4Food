package com.lifesum.test.search4food.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by David on 7/5/14.
 */
public class NetworkUtils {

    public static boolean verifyNetworkState(Activity activity) {
        final ConnectivityManager conMgr = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();

        if (activeNetwork == null) return false;
        if (!activeNetwork.isConnected()) {
            return false;
        }
        return true;
    }
}
