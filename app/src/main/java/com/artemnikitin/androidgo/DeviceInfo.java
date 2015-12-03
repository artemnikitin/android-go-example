package com.artemnikitin.androidgo;

import android.content.Context;
import android.util.DisplayMetrics;

public class DeviceInfo {

    private final DisplayMetrics displayInfo;

    public DeviceInfo(Context context) {
        displayInfo = context.getResources().getDisplayMetrics();
    }

    public int getDpi() {
        return displayInfo.densityDpi;
    }

    public int getHeight() {
        return displayInfo.heightPixels;
    }

    public int getWidth() {
        return displayInfo.widthPixels;
    }

}
