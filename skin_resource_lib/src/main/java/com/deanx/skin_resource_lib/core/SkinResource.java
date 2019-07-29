package com.deanx.skin_resource_lib.core;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import com.deanx.skin_resource_lib.utils.FileUtils;

import java.io.File;
import java.lang.reflect.Method;

/**
 * 封装皮肤包加载 以及 皮肤资源解析的功能
 */
public class SkinResource {

    private final static int FLAG_RESOURCE_NOT_FOUND = -1;

    private final static String TAG = "skin load >>>";
    private final static String SKIN_DIR = "skin";
    private final static String SKIN_FILE_SUFFIX = ".skin";
    private final static String ADD_ASSET_PATH = "addAssetPath";

    private Context mContext;
    private Resources mSkinResource;
    private String mSkinPackageName;

    public SkinResource(Context context) {
        mContext = context.getApplicationContext();
        mSkinResource = null;
    }

    /**
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
        mSkinPackageName = packageName;
        return true;
    }

    /**
     * 设置显示默认皮肤， 即使用app内置资源
     *
     * @return true:需要刷新界面显示， false:不需要
     */
    public boolean showDefaultSkin() {
        if (mSkinResource == null) {
            return false;
        }
        mSkinResource = null;
        return true;
    }

    public Resources getDefaultResource() {
        return mContext.getResources();
    }

    public Resources getRealResource(int resId) {
        if (mSkinResource != null) {
            int realResourceId = findSkinResId(resId);
            if (realResourceId > 0) {
                return mSkinResource;
            }
        }
        return mContext.getResources();
    }

    public int getRealUsedResId(int resId) {
        int skinResId = findSkinResId(resId);
        if (skinResId > 0) {
            return skinResId;
        }
        return resId;
    }

    /**
     * 动态资源映射 将app内置的资源Id，转换成 皮肤包内 的资源id
     *
     * @param resId 资源ID值
     * @return 没有皮肤包，或皮肤包未找到资源 返回-1， 否则返回皮肤包内 资源Id
     */
    private int findSkinResId(int resId) {
        if (mSkinResource == null) {
            //没有皮肤包
            return FLAG_RESOURCE_NOT_FOUND;
        }

        //通过资源的 Name和Type，动态映射，找出皮肤包内 对应资源Id

        Resources sysResource = mContext.getResources();
        //资源名称  sample.jpg
        String resourceName = sysResource.getResourceEntryName(resId);
        //资源类型： drawable
        String resourceType = sysResource.getResourceTypeName(resId);

        int skinResId = mSkinResource.getIdentifier(resourceName, resourceType, mSkinPackageName);

        if (skinResId > 0) {
            //皮肤包内找到 对应资源
            return skinResId;
        }
        return FLAG_RESOURCE_NOT_FOUND;
    }

    /**
     * 从私有目录加载 皮肤文件, 并缓存解析出来的Resource文件
     *
     * @return 加载成功，返回皮肤包名； 加载失败，返回null
     */
    private String loadInnerSkin(String skinFile) {
        try {
            //加载该皮肤资源
            Resources sysResource = mContext.getResources();

            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPathMethod = AssetManager.class.getMethod(ADD_ASSET_PATH, String.class);

            addAssetPathMethod.setAccessible(true);
            addAssetPathMethod.invoke(assetManager, skinFile);

            Resources resources = new Resources(assetManager,
                    sysResource.getDisplayMetrics(), sysResource.getConfiguration());

            // 根据apk文件路径（皮肤包也是apk文件），获取该应用的包名。兼容5.0 - 9.0（亲测）
            String skinPackageName = mContext.getPackageManager()
                    .getPackageArchiveInfo(skinFile, PackageManager.GET_ACTIVITIES).packageName;

            if (!TextUtils.isEmpty(skinPackageName)) {
                //皮肤包加载成功
                mSkinResource = resources;
                return skinPackageName;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
