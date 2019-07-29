package com.deanx.skin_resource_lib;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.deanx.skin_resource_lib.core.MResource;
import com.deanx.skin_resource_lib.core.SkinResource;
import com.deanx.skin_resource_lib.utils.ActionBarUtils;
import com.deanx.skin_resource_lib.utils.NavigationUtils;
import com.deanx.skin_resource_lib.utils.StatusBarUtils;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Iterator;

/**
 * 对外接口，加载皮肤管理类
 */
public class SkinRManager {

    private final static String CONTEXT_IMPL_CLASS_NAME = "android.app.ContextImpl";
    private final static String CONTEXT_IMPL_FIELD_NAME = "mResources";

    private final static String TAG = "SkinRManager >>>";

    private static SkinRManager mInstance;

    private boolean mIsInit;
    private SkinResource mSkinResource;
    private HashSet<WeakReference<Activity>> mActivityRefs;

    private int mThemeColorId = -1;
    private MResource mResource;

    public static SkinRManager getInstance() {
        if (mInstance == null) {
            synchronized (SkinRManager.class) {
                if (mInstance == null) {
                    mInstance = new SkinRManager();
                }
            }
        }
        return mInstance;
    }

    private SkinRManager() {
        mActivityRefs = new HashSet<>();
    }

    public void init(Application application) {
        mIsInit = true;
        mSkinResource = new SkinResource(application.getApplicationContext());
        application.registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
    }

    public void installSkin(String skinFilePath) {
        checkIsInit();

        boolean isSuccess = mSkinResource.loadSkin(skinFilePath);
        if (isSuccess) {
            notifySkinChanged();
        } else {
            Log.e(TAG, "loadSkin error");
        }

    }

    private void notifySkinChanged() {
        long currentTimeMillis = System.currentTimeMillis();

        for (WeakReference<Activity> reference : mActivityRefs) {
            Activity activity = reference.get();
            if (activity == null) {
                continue;
            }
            applySkinTheme(activity);

            Window window = activity.getWindow();
            if (window != null) {
                View decorView = window.getDecorView();
                decorView.requestLayout();
                decorView.invalidate();
            }
        }

        Log.e(TAG, "notifySkinChanged cost:" + (System.currentTimeMillis() - currentTimeMillis));
    }

    public void showDefaultSkin() {
        checkIsInit();
        boolean isSuccess = mSkinResource.showDefaultSkin();
        if (isSuccess) {
            notifySkinChanged();
        }
    }

    public void setThemeColorId(int themeColorId) {
        mThemeColorId = themeColorId;
    }

    private void checkIsInit() {
        if (!mIsInit) {
            throw new RuntimeException("please call init() first");
        }
    }

    /**
     * @param contextImp 替换ContextImp对象中的Resource对象
     */
    public void createActivityResourceProxy(Context contextImp) {
        long currentTimeMillis = System.currentTimeMillis();

        try {
            @SuppressLint("PrivateApi")
            Class<?> clazz = Class.forName(CONTEXT_IMPL_CLASS_NAME);
            Field field = clazz.getDeclaredField(CONTEXT_IMPL_FIELD_NAME);
            field.setAccessible(true);

            if (mResource == null) {
                mResource = new MResource(mSkinResource);
            }
            field.set(contextImp, mResource);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e(TAG, "createActivityResourceProxy cost:" + (System.currentTimeMillis() - currentTimeMillis));
    }

    /**
     * 根据当前皮肤配置，更新Activity主题
     */
    private void applySkinTheme(Activity activity) {
        checkIsInit();

        if (Build.VERSION.SDK_INT < 21 || mThemeColorId <= 0) {
            return;
        }

        Resources resource = mSkinResource.getRealResource(mThemeColorId);
        int realUsedResId = mSkinResource.getRealUsedResId(mThemeColorId);

        int themeColor = resource.getColor(realUsedResId);

        StatusBarUtils.forStatusBar(activity, themeColor);
        NavigationUtils.forNavigation(activity, themeColor);

        if (activity instanceof AppCompatActivity) {
            ActionBarUtils.forActionBar((AppCompatActivity) activity, themeColor);
        }
    }

    /**
     * @param activity Activity 销毁时，移除
     */
    private void removeActivity(Activity activity) {
        Iterator<WeakReference<Activity>> iterator = mActivityRefs.iterator();
        while (iterator.hasNext()) {
            WeakReference<Activity> weakRef = iterator.next();
            Activity activityRef = weakRef.get();
            if (activityRef == null) {
                iterator.remove();
            }
            if (activity == activityRef) {
                iterator.remove();
                break;
            }
        }
    }

    private Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks =
            new Application.ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    mActivityRefs.add(new WeakReference<>(activity));
                    applySkinTheme(activity);
                }

                @Override
                public void onActivityStarted(Activity activity) {

                }

                @Override
                public void onActivityResumed(Activity activity) {

                }

                @Override
                public void onActivityPaused(Activity activity) {

                }

                @Override
                public void onActivityStopped(Activity activity) {

                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    removeActivity(activity);
                }
            };


}
