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
        LayoutInflater inflater = LayoutInflater.from(activity);
        LayoutInflaterCompat.setFactory2(inflater, mSkinFactory);

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

    public static void setDayNightMode(@AppCompatDelegate.NightMode int nightMode) {
        notifySkinChanged(nightMode);
    }

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

    private static LayoutInflater.Factory2 mSkinFactory = new LayoutInflater.Factory2() {
        @Override
        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
            View view = null;
            switch (name) {
                case "LinearLayout":
                    view = new SkinnableLinearLayout(context, attrs);
                    verifyNotNull(view, name);
                    break;
                case "RelativeLayout":
                    view = new SkinnableRelativeLayout(context, attrs);
                    verifyNotNull(view, name);
                    break;
                case "TextView":
                    view = new SkinnableTextView(context, attrs);
                    verifyNotNull(view, name);
                    break;
                case "ImageView":
                    view = new SkinnableImageView(context, attrs);
                    verifyNotNull(view, name);
                    break;
                case "Button":
                    view = new SkinnableButton(context, attrs);
                    verifyNotNull(view, name);
                    break;
            }
            return view;
        }

        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            return null;
        }
    };

    private static void verifyNotNull(View view, String name) {
        if (view == null) {
            throw new IllegalStateException(SkinManager.class.getName() + " asked to inflate view for <" + name + ">, but returned null");
        }
    }
}
