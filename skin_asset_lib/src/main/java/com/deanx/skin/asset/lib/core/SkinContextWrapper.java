package com.deanx.skin.asset.lib.core;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.view.ContextThemeWrapper;

public class SkinContextWrapper extends ContextThemeWrapper {
    private AssetManager mAssetManager;
    private Resources mResources;
    private ClassLoader mClassLoader;

    public SkinContextWrapper(Context context) {
        super();
    }

    public void updateSkinResource(AssetManager am, Resources resources, ClassLoader classLoader) {
        if (mAssetManager != null) {
            mAssetManager.close();
        }
        mAssetManager = am;
        mResources = resources;
        mClassLoader = classLoader;
    }
}
