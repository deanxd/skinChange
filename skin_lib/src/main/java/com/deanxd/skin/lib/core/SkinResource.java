package com.deanxd.skin.lib.core;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;

import com.deanxd.skin.lib.utils.FileUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;

public class SkinResource {

    private final static String TAG = "skin load >>>";
    private final static String SKIN_DIR = "skin";
    private final static String SKIN_FILE_SUFFIX = ".skin";
    private final static String ADD_ASSET_PATH = "addAssetPath";

    private Context mContext;
    private Resources mDefaultResource;
    private HashMap<String, Resources> mSkinCache;

    private String mCurrentSKinPackageName;

    public SkinResource(Context context) {
        mContext = context.getApplicationContext();
        mDefaultResource = mContext.getResources();
        mCurrentSKinPackageName = context.getPackageName();

        mSkinCache = new HashMap<>();
        mSkinCache.put(mCurrentSKinPackageName, mDefaultResource);
    }

    /**
     * TODO 加载皮肤应当异步操作，以后优化
     * <p>
     * 1，將当前皮肤拷贝到私有文件夹
     * 2，加载该皮肤资源
     *
     * @return true:加载皮肤成功，需要刷新界面显示， false:加载皮肤失败
     * <p>
     */
    public boolean loadSkin(String skinFilePath) {
        // 將当前皮肤拷贝到私有文件夹
        File targetFile = copySKinFile(skinFilePath);
        if (targetFile == null) {
            Log.e(TAG, "copySKinFile error");
            return false;
        }
        String packageName = loadInnerSkin(targetFile.getAbsolutePath());
        if (TextUtils.isEmpty(packageName)) {
            Log.e(TAG, "packageName is empty");
            return false;
        }

        mCurrentSKinPackageName = packageName;
        return true;
    }

    /**
     * 设置显示默认皮肤， 即使用app内置资源
     *
     * @return true:需要刷新界面显示， false:不需要
     */
    public boolean showDefaultSkin() {
        if (mCurrentSKinPackageName.equals(mContext.getPackageName())) {
            //当前已显示 默认皮肤，不用切换
            return false;
        }
        mCurrentSKinPackageName = mContext.getPackageName();
        return true;
    }

    public int getColor(int colorId) {
        int resourceId = getRealResourceId(colorId);
        Resources cacheResource = getCacheResource();
        try {
            return cacheResource.getColor(resourceId);
        } catch (Exception e) {
        }
        return mDefaultResource.getColor(resourceId);
    }

    /**
     * TODO
     * mipmap和drawable统一用法（待测）
     */
    public Drawable getDrawableOrMipMap(int drawableId) {
        int resourceId = getRealResourceId(drawableId);
        Resources cacheResource = getCacheResource();
        try {
            return cacheResource.getDrawable(resourceId);
        } catch (Exception e) {
        }
        return mDefaultResource.getDrawable(resourceId);
    }

    public String getString(int stringId) {
        int resourceId = getRealResourceId(stringId);
        Resources cacheResource = getCacheResource();
        try {
            return cacheResource.getString(resourceId);
        } catch (Exception e) {
        }
        return mDefaultResource.getString(resourceId);
    }

    public ColorStateList getColorStateList(int resourceId) {
        int ids = getRealResourceId(resourceId);
        Resources cacheResource = getCacheResource();
        try {
            return cacheResource.getColorStateList(resourceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDefaultResource.getColorStateList(resourceId);
    }

    /**
     * @return 返回值特殊情况, 可能是color / drawable / mipmap
     */
    public Object getBackgroundOrSrc(int resourceId) {
        // 根据当前属性的类型名ResourceTypeName判断
        String resourceTypeName = mDefaultResource.getResourceTypeName(resourceId);

        switch (resourceTypeName) {
            case "color":
                return getColor(resourceId);
            case "mipmap":
            case "drawable":
                return getDrawableOrMipMap(resourceId);
            default:
                break;
        }
        return null;
    }

    /**
     * 获得字体
     */
    public Typeface getTypeface(int resourceId) {
        // 通过资源ID获取资源path，参考：resources.arsc资源映射表
        String skinTypefacePath = getString(resourceId);
        // 路径为空，使用系统默认字体
        if (TextUtils.isEmpty(skinTypefacePath))
            return Typeface.DEFAULT;
        Resources cacheResource = getCacheResource();
        return Typeface.createFromAsset(cacheResource.getAssets(), skinTypefacePath);
    }

    /**
     * 从私有目录加载 皮肤文件, 并缓存解析出来的Resource文件
     *
     * @return 加载成功，返回皮肤包名； 加载失败，返回null
     */
    private String loadInnerSkin(String skinFile) {
        try {
            //加载该皮肤资源
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPathMethod = AssetManager.class.getMethod(ADD_ASSET_PATH, String.class);

            addAssetPathMethod.setAccessible(true);
            addAssetPathMethod.invoke(assetManager, skinFile);

            Resources resources = new Resources(assetManager,
                    mDefaultResource.getDisplayMetrics(), mDefaultResource.getConfiguration());

            // 根据apk文件路径（皮肤包也是apk文件），获取该应用的包名。兼容5.0 - 9.0（亲测）
            String skinPackageName = mContext.getPackageManager()
                    .getPackageArchiveInfo(skinFile, PackageManager.GET_ACTIVITIES).packageName;

            if (!TextUtils.isEmpty(skinPackageName)) {
                mSkinCache.put(skinPackageName, resources);
                return skinPackageName;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将app内置的资源Id，转换成 皮肤包内 的资源id
     * <p>
     * 参考：resources.arsc资源映射表
     * 通过ID值获取资源 Name 和 Type
     *
     * @param resId 资源ID值
     * @return 如果没有皮肤包则加载app内置资源ID，反之加载皮肤包指定资源ID
     */
    private int getRealResourceId(int resId) {
        Resources resources = getCacheResource();
        if (resources == mDefaultResource) {
            return resId;
        }

        //资源名称  sample.jpg
        String resourceName = mDefaultResource.getResourceEntryName(resId);
        //资源类型： drawable
        String resourceType = mDefaultResource.getResourceTypeName(resId);

        int identifier = resources.getIdentifier(resourceName, resourceType, mCurrentSKinPackageName);

        if (identifier == 0) {
            //皮肤包内 未找到当前资源
            return resId;
        }
        return identifier;
    }

    private Resources getCacheResource() {
        Resources resources = mSkinCache.get(mCurrentSKinPackageName);
        if (resources == null) {
            resources = mDefaultResource;
        }
        return resources;
    }


    private File copySKinFile(String skinFilePath) {
        if (TextUtils.isEmpty(skinFilePath) || !skinFilePath.endsWith(SKIN_FILE_SUFFIX)) {
            return null;
        }

        File file = new File(skinFilePath);
        if (!file.exists()) {
            Log.e(TAG, "source skin file not exist");
            return null;
        }

        File targetFile = new File(mContext.getDir(SKIN_DIR, Context.MODE_PRIVATE).getAbsolutePath()
                + File.separator + file.getName());
        if (targetFile.exists()) {
            if (!targetFile.delete()) {
                Log.e(TAG, "targetFile.delete error");
                return null;
            }
        }
        if (!FileUtils.copyFile(file, targetFile)) {
            Log.e(TAG, "copyFile error");
            return null;
        }
        return targetFile;
    }

}
