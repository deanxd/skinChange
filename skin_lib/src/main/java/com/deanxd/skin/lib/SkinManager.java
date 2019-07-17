package com.deanxd.skin.lib;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.LayoutInflaterCompat;

import com.deanxd.skin.lib.core.SkinFactory;
import com.deanxd.skin.lib.listener.ISkinnableView;
import com.deanxd.skin.lib.utils.ActionBarUtils;
import com.deanxd.skin.lib.utils.NavigationUtils;
import com.deanxd.skin.lib.utils.StatusBarUtils;
import com.deanxd.skin.lib.view.SkinnableButton;
import com.deanxd.skin.lib.view.SkinnableImageView;
import com.deanxd.skin.lib.view.SkinnableLinearLayout;
import com.deanxd.skin.lib.view.SkinnableRelativeLayout;
import com.deanxd.skin.lib.view.SkinnableTextView;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;

public class SkinManager {

    private final static String TAG = "skin manager >>>";

    private static HashSet<WeakReference<Activity>> mHashSet = new HashSet<>();

    public static void register(Activity activity) {
        //为当前Activity设置 布局解析监听
        LayoutInflater inflater = LayoutInflater.from(activity);
        LayoutInflaterCompat.setFactory2(inflater, new SkinFactory(activity));

        //缓存注册过的activity, 在改变参数时，全局界面改变
        WeakReference<Activity> reference = new WeakReference<>(activity);
        mHashSet.add(reference);
    }

    public static void unRegister(Activity activity) {
        Iterator<WeakReference<Activity>> iterator = mHashSet.iterator();
        while (iterator.hasNext()) {
            WeakReference<Activity> reference = iterator.next();

            Activity cacheActivity = reference.get();
            if (cacheActivity != null) {
                if (cacheActivity == activity) {
                    iterator.remove();
                }
            } else {
                iterator.remove();
            }
        }
    }

    public static void installSkin(String skinFilePath) {

    }

    public static void setDayNightMode(@AppCompatDelegate.NightMode int nightMode) {
        notifySkinChanged(nightMode);
    }


    /**
     * 通知 皮肤改变
     */
    private static void notifySkinChanged(@AppCompatDelegate.NightMode int nightMode) {
        final boolean isPost21 = Build.VERSION.SDK_INT >= 21;
        for (WeakReference<Activity> reference : mHashSet) {
            Activity activity = reference.get();
            if (activity != null) {
                if (activity instanceof AppCompatActivity) {
                    if (isPost21) {
                        ((AppCompatActivity) activity).getDelegate().setLocalNightMode(nightMode);
                        ActionBarUtils.forActionBar((AppCompatActivity) activity);
                    }
                }
                if (isPost21) {
                    StatusBarUtils.forStatusBar(activity);
                    NavigationUtils.forNavigation(activity);
                }

                View decorView = activity.getWindow().getDecorView();
                applyDayNightForView(decorView);
            }
        }
    }

    /**
     * 循环view集合，根据当前配置 设置样式
     */
    private static void applyDayNightForView(View view) {
        if (view instanceof ISkinnableView) {
            ISkinnableView viewsMatch = (ISkinnableView) view;
            viewsMatch.updateSkin();
        }

        if (view instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) view;
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                applyDayNightForView(parent.getChildAt(i));
            }
        }
    }
}
