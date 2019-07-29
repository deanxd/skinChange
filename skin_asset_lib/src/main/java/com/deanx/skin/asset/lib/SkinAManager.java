package com.deanx.skin.asset.lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.util.Log;

import com.deanx.skin.asset.lib.core.SkinContextWrapper;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SkinAManager {


    public void addSkinPath(Context context, String skinPkgPath) throws Exception {
        PackageManager packageManager = context.getPackageManager();
        packageManager.getPackageArchiveInfo(skinPkgPath,
                PackageManager.GET_SIGNATURES | PackageManager.GET_META_DATA);

        AssetManager assetManager = AssetManager.class.newInstance();
        Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            addAssetPath.invoke(assetManager, context.getApplicationInfo().publicSourceDir);
            addAssetPath.invoke(assetManager, skinPkgPath);

        } else {
            //5.0以上，需要將assets 资源文件单独添加
            File assetsFile = new File(skinPkgPath);
//            File assetsFile = Utils.generateIndependentAsssetsForl(new File((skinPkgPath)));
            addAssetPath.invoke(assetManager, skinPkgPath);
            addAssetPath.invoke(assetManager, context.getApplicationInfo().publicSourceDir);
            addAssetPath.invoke(assetManager, assetsFile.getAbsolutePath());
        }
    }

    public Context wrapperContext(Context context) {
        return new SkinContextWrapper(context);
    }

    public Context unWrapperContext(Context context) {
        if (context instanceof ContextWrapper) {
            return ((ContextWrapper) context).getBaseContext();
        }
        return context;
    }


}
