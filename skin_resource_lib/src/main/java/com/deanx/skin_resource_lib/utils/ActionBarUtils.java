package com.deanx.skin_resource_lib.utils;

import android.annotation.TargetApi;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ActionBarUtils {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void forActionBar(AppCompatActivity activity) {
        TypedArray a = activity.getTheme().obtainStyledAttributes(0, new int[] {
                android.R.attr.colorPrimary
        });
        int color = a.getColor(0, 0);
        a.recycle();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(color));
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void forActionBar(AppCompatActivity activity, int skinColor) {
        try {
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setBackgroundDrawable(new ColorDrawable(skinColor));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
