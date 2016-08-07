package com.patrick.customviewdemo.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @ 创建者     administrator
 * @ 创建时间   2016/7/17 22:49
 * @ 描述      ${TODO}
 * @ 更新者    $Author$
 * @ 更新时间  2016/7/17
 * @ 更新描述  ${TODO}
 */
public class UIUtils {

    public static DisplayMetrics getScreenInfo(Context ctx){
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }
}
