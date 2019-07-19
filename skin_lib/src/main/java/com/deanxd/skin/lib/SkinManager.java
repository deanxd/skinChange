package com.deanxd.skin.lib;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.LayoutInflaterCompat;

import com.deanxd.skin.lib.core.SkinFactory;
import com.deanxd.skin.lib.core.SkinResource;
import com.deanxd.skin.lib.listener.IResourceParser;
import com.deanxd.skin.lib.listener.ISkinnableView;
import com.deanxd.skin.lib.utils.ActionBarUtils;
import com.deanxd.skin.lib.utils.NavigationUtils;
import com.deanxd.skin.lib.utils.StatusBarUtils;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;

public class SkinManager implements IResourceParser {
    private final static String TAG = "skin manager >>>";

    private boolean mIsInit;
    private SkinResource mSkinResource;
    private HashSet<WeakReference<Activity>> mHashSet;

    private int mThemeColorId = -1;

    private static SkinManager mSkinManager;

    private SkinManager() {
    }

    public static SkinManager getInstance() {
        if (mSkinManager == null) {
            synchronized (SkinManager.class) {
                if (mSkinManager == null) {
                    mSkinManager = new SkinManager();
                }
            }
        }
        return mSkinManager;
    }

    public void setThemeColorId(int themeColorId) {
        mThemeColorId = themeColorId;
    }

    public void init(Context context) {
        mIsInit = true;
        mHashSet = new HashSet<>();
        mSkinResource = new SkinResource(context);
    }

    /**
     * 注册当前Activity, 皮肤改变时， 通知activity更新显示
     * <p>
     * 在Activity的onCreate(@Nullable Bundle savedInstanceState)方法中super.onCreate(savedInstanceState)语句之前调用
     * 以便拦截注册InflatLayout布局解析监听
     * <p>
     * 例如：
     * ---@Override
     * ---protected void onCreate(@Nullable Bundle savedInstanceState) {
     * ------SkinManager.register(this);
     * ------super.onCreate(savedInstanceState);
     * ------......
     * ---}
     */
    public void register(Activity activity) {
        checkIsInit();

        //为当前Activity设置 布局解析监听
        LayoutInflater inflater = LayoutInflater.from(activity);
        LayoutInflaterCompat.setFactory2(inflater, new SkinFactory(activity));

        //缓存注册过的activity, 在改变参数时，全局界面改变
        WeakReference<Activity> reference = new WeakReference<>(activity);
        mHashSet.add(reference);
    }

    /**
     * Activity销毁时， 从集合中移除activity， 避免内存泄漏
     */
    public void unRegister(Activity activity) {
        checkIsInit();

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

    /**
     * 安装新皮肤包,并显示
     */
    public void installSkin(String skinFilePath) {
        checkIsInit();

        boolean isSuccess = mSkinResource.loadSkin(skinFilePath);
        if (isSuccess) {
            notifySkinChanged();
        } else {
            Log.e(TAG, "loadSkin error");
        }
    }

    /**
     * 显示默认皮肤
     */
    public void showDefaultSkin() {
        checkIsInit();

        boolean isSuccess = mSkinResource.showDefaultSkin();
        if (isSuccess) {
            notifySkinChanged();
        }
    }

    /**
     * 更新主题
     */
    public void applySkinConfig(Activity activity) {
        checkIsInit();

        long currentTimeMillis = System.currentTimeMillis();

        applySkinTheme(activity);
        applyActivitySkin(activity);

        Log.e(TAG, "applySkinConfig cost:" + (System.currentTimeMillis() - currentTimeMillis));
    }

    /**
     * 设置日间模式、夜间模式
     */
    public void setDayNightMode(@AppCompatDelegate.NightMode int nightMode) {
        checkIsInit();
        notifyDayNightModeChanged(nightMode);
    }


    private void notifySkinChanged() {
        long currentTimeMillis = System.currentTimeMillis();

        for (WeakReference<Activity> reference : mHashSet) {
            Activity activity = reference.get();
            if (activity == null) {
                continue;
            }
            applySkinTheme(activity);
            applyActivitySkin(activity);
        }

        Log.e(TAG, "notifySkinChanged cost:" + (System.currentTimeMillis() - currentTimeMillis));
    }

    /**
     * 遍历该Activity 布局View, 应用当前皮肤配置
     */
    private void applyActivitySkin(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        updateViewsSkin(decorView);
    }

    /**
     * 根据当前皮肤配置，更新Activity主题
     */
    private void applySkinTheme(Activity activity) {
        if (Build.VERSION.SDK_INT < 21 || mThemeColorId <= 0) {
            return;
        }

        int themeColor = mSkinResource.getColor(mThemeColorId);
        StatusBarUtils.forStatusBar(activity, themeColor);
        NavigationUtils.forNavigation(activity, themeColor);

        if (activity instanceof AppCompatActivity) {
            ActionBarUtils.forActionBar((AppCompatActivity) activity, themeColor);
        }
    }

    /**
     * 通知View 更新 日间模式、夜间模式
     */
    private void notifyDayNightModeChanged(@AppCompatDelegate.NightMode int nightMode) {
        final boolean isPost21 = Build.VERSION.SDK_INT >= 21;

        for (WeakReference<Activity> reference : mHashSet) {
            Activity activity = reference.get();
            if (activity == null) {
                continue;
            }

            if (isPost21) {
                StatusBarUtils.forStatusBar(activity);
                NavigationUtils.forNavigation(activity);

                if (activity instanceof AppCompatActivity) {
                    ((AppCompatActivity) activity).getDelegate().setLocalNightMode(nightMode);
                    ActionBarUtils.forActionBar((AppCompatActivity) activity);
                }
            }

            View decorView = activity.getWindow().getDecorView();
            updateViewsSkin(decorView);
        }
    }

    /**
     * 根据当前皮肤设置，更新界面元素 显示
     */
    private void updateViewsSkin(View view) {
        if (view instanceof ISkinnableView) {
            ISkinnableView viewsMatch = (ISkinnableView) view;
            viewsMatch.updateSkin();
        }

        if (view instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) view;
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                updateViewsSkin(parent.getChildAt(i));
            }
        }
    }

    private void checkIsInit() {
        if (!mIsInit) {
            throw new RuntimeException("please call init() first");
        }
    }

    @Override
    public int getColor(int colorId) {
        checkIsInit();

        return mSkinResource.getColor(colorId);
    }

    @Override
    public Drawable getDrawableOrMipMap(int drawableId) {
        checkIsInit();

        return mSkinResource.getDrawableOrMipMap(drawableId);
    }

    @Override
    public String getString(int stringId) {
        checkIsInit();

        return mSkinResource.getString(stringId);
    }

    @Override
    public ColorStateList getColorStateList(int ids) {
        checkIsInit();

        return mSkinResource.getColorStateList(ids);
    }

    @Override
    public Object getBackgroundOrSrc(int resourceId) {
        checkIsInit();

        return mSkinResource.getBackgroundOrSrc(resourceId);
    }

    @Override
    public Typeface getTypeface(int resourceId) {
        checkIsInit();

        return mSkinResource.getTypeface(resourceId);
    }
}
