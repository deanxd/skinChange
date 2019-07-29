package com.deanxd.skin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import androidx.annotation.Nullable;

import com.deanxd.skin.base.BaseActivity;
import com.deanxd.skin.lib.SkinManager;

import java.io.File;

public class DynamicMainActivity extends BaseActivity {

    private String skinFilePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 运行时权限申请（6.0+）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (checkSelfPermission(perms[0]) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(perms, 200);
            }
        }
        skinFilePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "red_skin.skin";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dynamic_main;
    }

    public void skinDynamic(View view) {
        SkinManager skinManager = SkinManager.getInstance();
        skinManager.setThemeColorId(R.color.skin_item_color);
        skinManager.installSkin(skinFilePath);

//        SkinRManager skinManager = SkinRManager.getInstance();
//        skinManager.setThemeColorId(R.color.skin_item_color);
//        skinManager.installSkin(skinFilePath);
    }

    public void skinDefault(View view) {
        SkinManager skinManager = SkinManager.getInstance();

        skinManager.setThemeColorId(R.color.colorPrimary);
        skinManager.showDefaultSkin();

//        SkinRManager skinManager = SkinRManager.getInstance();
//        skinManager.setThemeColorId(R.color.colorPrimary);
//        skinManager.showDefaultSkin();
    }

    public void jumpSelf(View view) {
        startActivity(new Intent(this, DynamicMainActivity.class));
    }
}
