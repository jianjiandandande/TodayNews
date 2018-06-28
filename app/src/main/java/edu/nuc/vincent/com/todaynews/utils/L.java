package edu.nuc.vincent.com.todaynews.utils;

import android.util.Log;

/**
 * Created by Vincent on 2018/6/27.
 */

public class L {

    //开关
    public static final boolean DEBUG =true;
    //TAG
    public static final String TAG= "Androidkeshe";

    //五个等级 DIWEF

    public static void d(String text){
        if (DEBUG){
            Log.d(TAG, text);
        }
    }

    public static void i(String text){
        if (DEBUG){
            Log.i(TAG, text);
        }
    }

    public static void w(String text){
        if (DEBUG){
            Log.w(TAG, text);
        }
    }

    public static void e(String text){
        if (DEBUG){
            Log.e(TAG, text);
        }
    }

}
